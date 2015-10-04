package server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import protocole.Message;
import protocole.MessageHandler;

import utils.Marshallizer;

public class UDPServer {
	
	
	private DatagramSocket mySocket;
	private String serverIP;
	private int serverPort;

	//UDP server repeatedly receives a request and sends it back to the client 
	public UDPServer(){ 
		serverPort = 6789;
		serverIP = "192.169.0.1";
	}	
	
	public void startServer() {
		mySocket = null;
		try {
			mySocket = new DatagramSocket(serverPort); // port convenu avec les clients
			byte[] buffer = new byte[1000];
			while (true) {
				DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
				mySocket.receive(datagram); // réception bloquante

				Message message = (Message) Marshallizer.unmarshall(datagram);
				//IF client ask something
				if (message.isRequest()) {
					new Thread(new MessageHandler(message,this)).start();					
				} else {
					//new Thread(new ReplyHandler((Reply) message, this)).start();
				}
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}

		finally {
			if (mySocket != null)
				mySocket.close();
		}

	}

	public void stopServer() {
		// TODO : faire quelque chose de plus ŽlŽgant
		if (mySocket != null)
			mySocket.close();
	}
	//curent id
	public void manageDuplicate(){
		
	}
	//Get all the information from the Datagram
	public void unmarshallData(){
		
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
}
