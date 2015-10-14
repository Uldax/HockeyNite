package test;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import utils.Marshallizer;
import protocole.Message;

public class Protocole {
	
	private static final Logger logger = Logger.getLogger(Protocole.class);
	
	/**
	 * Protocole de communication avec le serveur
	 * @param message
	 * @param aSocket
	 * @author Uldax
	 */
	public static void send(Message message, DatagramSocket aSocket) {
		try {
				//Message.getData()
				logger.info(message.toString());
				byte[] stream = Marshallizer.marshallize(message);
				logger.info("Stream length " + stream.length);
				DatagramPacket datagram = new DatagramPacket(stream,
						stream.length, 
						message.getDestination(),
						message.getDestinationPort());
				aSocket.send(datagram); // émission non-bloquante
			
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
}
