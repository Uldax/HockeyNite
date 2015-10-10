package test;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import utils.Marshallizer;
import protocole.Message;

public class Protocole {


	public static void send(Message message,DatagramSocket aSocket ) {
		try {
				//Message.getData()
				System.out.println(message.toString());
				byte[] stream = Marshallizer.marshallize(message);
				System.out.println("Stream length " + stream.length);
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
