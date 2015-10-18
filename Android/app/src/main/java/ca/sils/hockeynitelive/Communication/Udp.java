// Identification
package ca.sils.hockeynitelive.Communication;


// Importations
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import dataObject.ListMatchName;
import dataObject.Match;
import protocole.Message;
import protocole.Reply;
import protocole.Request;
import utils.Marshallizer;


/**
 * Created by Luc Saucier on 15-10-18.
 */
public class Udp
{
    // Constantes
    private final int TIMEOUT = 5000;
    private final int MAX_TENTATIVE = 5;


    // Variables
    private InetAddress adresse;
    private int portServeur;
    private int portClient;
    private Boolean error;
    private int tentative;
    private Thread tSendMessage = null;
    private Thread WaitingMessage = null;
    private Reply reponse = null;
    private Object MutexLock = new Object();
    private DatagramSocket dsClient = null;


    // Méthodes
    public void setServeur(InetAddress p_adresse, int p_portServeur, int p_portClient)
    {
        adresse = p_adresse;
        portClient = p_portClient;
        portServeur = p_portServeur;
    }

    public ListMatchName getListMatchName()
    {
        // Initialisation
        tentative = 0;

        // Boucle d'essai
        do
        {
            // Initialisation
            error = false;

            // Essai
            try
            {
                dsClient = new DatagramSocket(portClient);

                // Doit être implanté avec SendMessageMatch
                // Message ask = Request.craftGetMatchList(adresse, portServeur);
                // send(ask, dsClient);
                tSendMessage = new Thread(new SendMessageMatch(dsClient, adresse, portServeur));
                tSendMessage.start();

                WaitingMessage = new Thread(new WaitMessage(1000));
                WaitingMessage.start();

                synchronized (MutexLock)
                {
                    new Thread(new WaitReponse()).start();
                    MutexLock.wait();
                }
            }
            catch (SocketException e)
            {
                System.out.println("Socket: " + e.getMessage());
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                System.out.println("Exception: " + e.getMessage());
            }
            finally
            {
                dsClient.close();
                dsClient = null;
                WaitingMessage.interrupt();
                tentative++;
            }
        }
        while (error && tentative < MAX_TENTATIVE);

        if (error)
        {
            System.out.println("--- Erreur Serveur Timeout ---");
            return null;
        }

        return (ListMatchName) reponse.getValue();
    }

    public Match getMatchDetail(int p_matchId)
    {
        // Initialisation
        tentative = 0;

        // Boucle d'essais
        do
        {
            error = false;

            try
            {
                dsClient = new DatagramSocket(portClient);

                // Doit être implanté avec SendMessageDetails
                Message ask = Request.craftGetMatchDetail(adresse, portServeur, p_matchId);
                send(ask, dsClient);

                System.out.println("Message envoyé");

                WaitingMessage = new Thread(new WaitMessage(1000));
                WaitingMessage.start();

                synchronized (MutexLock)
                {
                    new Thread(new WaitReponse()).start();
                    MutexLock.wait();
                }
            }
            catch (SocketException e)
            {
                System.out.println("Socket: " + e.getMessage());
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                dsClient.close();
                dsClient = null;
                WaitingMessage.interrupt();
                tentative++;
            }
        }
        while ((error) && (tentative < MAX_TENTATIVE));

        if (error)
        {
            System.out.println("--- Erreur Serveur TimeOut ---");
            return null;
        }

        return (Match) reponse.getValue();
    }

    // Envoi
    public static void send(Message p_message, DatagramSocket p_udpSocket)
    {
        try
        {
            byte[] stream = Marshallizer.marshallize(p_message);
            DatagramPacket datagram = new DatagramPacket(stream, stream.length, p_message.getDestination(), p_message.getDestinationPort());
            p_udpSocket.send(datagram);
        }
        catch (SocketException e)
        {
            System.out.println("Socket: " + e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println("IO: " + e.getMessage());
        }
    }

    // Rouler l'envoi de messages dans un thread
    public static class SendMessageMatch implements Runnable
    {
        private DatagramSocket datasocket;
        private InetAddress adresse;
        private int port;

        public SendMessageMatch(DatagramSocket p_socket, InetAddress p_adresse, int p_port)
        {
            datasocket = p_socket;
            adresse = p_adresse;
            port = p_port;
        }

        @Override
        public void run()
        {
            Message ask = Request.craftGetMatchList(adresse, port);
            send(ask, datasocket);
        }
    }

    // Rouler l'envoi de messages dans un thread
    public static class SendMessageDetails implements Runnable
    {
        private DatagramSocket datasocket;
        private InetAddress adresse;
        private int port;
        private int matchId;

        public SendMessageDetails(DatagramSocket p_socket, InetAddress p_adresse, int p_port, int p_matchId)
        {
            datasocket = p_socket;
            adresse = p_adresse;
            port = p_port;
            matchId = p_matchId;
        }

        @Override
        public void run()
        {
            Message ask = Request.craftGetMatchDetail(adresse, port, matchId);
            send(ask, datasocket);
        }
    }

    /**
     * Thread pour l'affichage de point d'indiquant à l'utilisateur d'attendre
     */
    public static class WaitMessage implements Runnable
    {
        private int timer = 0;

        public WaitMessage(int p_time)
        {
            timer = p_time;
        }

        @Override
        public void run()
        {
            while(true)
            {
                System.out.print(".");
                try
                {
                    Thread.sleep(timer);
                }
                catch (InterruptedException e)
                {
                    System.out.println("");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    private class WaitReponse implements Runnable
    {
        public WaitReponse() {}

        @Override
        public void run()
        {
            synchronized (MutexLock)
            {
                try
                {
                    byte[] buffer = new byte[10000];
                    DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                    dsClient.setSoTimeout(TIMEOUT);
                    dsClient.receive(reply);
                    reponse = (Reply) Marshallizer.unmarshall(reply);
                }
                catch (SocketTimeoutException e)
                {
                    error = true;
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    error = true;
                }
                finally
                {
                    MutexLock.notify();
                }
            }

        }

    }
}
