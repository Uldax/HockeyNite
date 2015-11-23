package test;
import dataObject.Bet;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;



import utils.Marshallizer;
import protocole.Message;

public class Protocole {
	
	
	
	/**
	 * Protocole de communication avec le serveur
	 * @param message
	 * @param aSocket
	 * @author Uldax
	 */
	public static void send(Message message, DatagramSocket aSocket) {
		try {
				//Message.getData()
				
				byte[] stream = Marshallizer.marshallize(message);
				
				DatagramPacket datagram = new DatagramPacket(stream,
						stream.length, 
						message.getDestination(),
						message.getDestinationPort());
				aSocket.send(datagram); // emission non-bloquante
			
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
        
        public static int sendTCP(Bet b, int betServerPort){
           int result = 0; // Par default
           Socket sClient = null;
            try {
                sClient = new Socket("localhost", betServerPort); // Connexion au serveur TCP

                // Récupération des Streams  
                InputStream is = sClient.getInputStream();  
                OutputStream os = sClient.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);

                //Envoi de l'objet
                oos.writeObject(b);
                
                oos.flush();     
                

                result = is.read(); //reception bloquante 
                
                
                
                sClient.close();   
                           
                
            } catch (IOException ex) {
                
            } 
        return result; // Retour du code recu 
        }
}
