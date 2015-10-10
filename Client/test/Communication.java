package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import affichage.Menu;
import dataObject.Match;
import protocole.Message;
import protocole.Reply;
import protocole.Request;
import utils.Marshallizer;

public class Communication {
	private static Communication INSTANCE = new Communication();
	public static Communication getInstance(){return INSTANCE;}
	
	private DatagramSocket aSocket = null;
	private InetAddress adress;
	private int serveurPort;
	private int clientPort;
	private Thread WaitingMessage = null;
	
	private Reply reponse = null;
	
	private Object MutexLock = new Object();

	private Communication(){
	}

	public void setServeur(InetAddress adress, int serveurPort, int clientPort){
		this.adress = adress;
		this.serveurPort = serveurPort;
		this.clientPort = clientPort;
	}
	
	public Match[] getListMatch(){
		try {
			aSocket = new DatagramSocket(this.clientPort);
		
			Message ask = Request.craftGetMatchList(this.adress,this.serveurPort);		
			Protocole.send(ask,aSocket);
			
			System.out.println("message send");
			
			WaitingMessage = new Thread(new Menu.WaitMessage(1000));
			WaitingMessage.start();
			
			synchronized (MutexLock) {
				new Thread(new WaitReponse()).start();
				MutexLock.wait();
			}			
		}
		catch (SocketException e){System.out.println("Socket: " + e.getMessage());} 
		catch (InterruptedException e) {e.printStackTrace();} 
		finally {aSocket.close(); aSocket = null; WaitingMessage.interrupt();}		
		
		return (Match[]) this.reponse.getValue();
	}
	
	public Match GetMatchDetail(int idMatch){
		try {
			aSocket = new DatagramSocket(this.clientPort);
		
			Message ask = Request.craftGetMatchDetail(this.adress,this.serveurPort, idMatch);		
			Protocole.send(ask,aSocket);
			
			System.out.println("message send");
			
			WaitingMessage = new Thread(new Menu.WaitMessage(1000));
			WaitingMessage.start();
			
			synchronized (MutexLock) {
				new Thread(new WaitReponse()).start();
				MutexLock.wait();
			}			
		}
		catch (SocketException e){System.out.println("Socket: " + e.getMessage());} 
		catch (InterruptedException e) {e.printStackTrace();} 
		finally {aSocket.close(); aSocket = null; WaitingMessage.interrupt();}		
		
		return (Match) this.reponse.getValue();
	}
	
	
	
	
	private class WaitReponse implements Runnable {
		private int timer = 0;
		
		public WaitReponse() {}
		
		@Override
		public void run() {
			synchronized (MutexLock) {
				try { 		                        
					byte[] buffer = new byte[1000];
					DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
					aSocket.receive(reply);
					reponse = (Reply) Marshallizer.unmarshall(reply);
			
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					MutexLock.notify();
				}
			}
				         
		}

	}
	
	
}
