package server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import protocole.Message;
import protocole.MessageHandler;
import org.apache.log4j.Logger;
import utils.Marshallizer;

public class UDPServer {
	
	
	private DatagramSocket mySocket;
	private String serverIP;
	private int serverPort;
	private static final Logger logger = Logger.getLogger(UDPServer.class);


	//UDP server repeatedly receives a request and sends it back to the client 
	public UDPServer(){ 
		serverPort = 6780;
		serverIP = "127.0.0.1";
	}	
	
	public void start() {
		mySocket = null;
		logger.info("server start on port " + String.valueOf(serverPort));
		try {
			mySocket = new DatagramSocket(serverPort); // port convenu avec les clients
			byte[] buffer = new byte[1000];
			while (true) {
				DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
				mySocket.receive(datagram); // réception bloquante
				logger.info("data receive");
				Message message = (Message) Marshallizer.unmarshall(datagram);
				//IF client ask something
				if (message.isRequest()) {
					logger.info("start thread for answer");
					new Thread(new MessageHandler(message,this)).start();					
				} else {
					//new Thread(new ReplyHandler((Reply) message, this)).start();
				}
				logger.info("end of reception");
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
