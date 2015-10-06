package test;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import utils.Marshallizer;
import protocole.Message;

public class Protocole {
	public static Message craftMessage(){
		Message reply = new Message();
		reply.setType(Message.REQUEST);
		reply.setDestinationPort(6780);
		InetAddress aHost = null;
		try {
			aHost = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		reply.setDestination(aHost);
		reply.setSender(aHost);
		reply.setSenderPort(6779);
		return reply;
	}

	public static void respond(Message message,DatagramSocket aSocket ) {
		try {
			
				//Message.getData()
				System.out.println(message.toString());
				byte[] stream = Marshallizer.marshallize(message);
				System.out.println("Stream length " + stream.length);
				DatagramPacket datagram = new DatagramPacket(stream,
						message.getLength(), 
						message.getDestination(),
						message.getDestinationPort());
				aSocket.send(datagram); // émission non-bloquante
			
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
	
	public static void askListMatch(DatagramSocket aSocket) throws SocketException,IOException{
		Message send = Protocole.craftMessage(); 
		aSocket = new DatagramSocket(6779); 

		Protocole.respond(send,aSocket);
		System.out.println("Ask server for Matchs"); 
	}
	
	public static Object[] getListMatch(DatagramSocket aSocket) throws IOException{
		//réception bloquante 		                        
		byte[] buffer = new byte[1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
		aSocket.receive(reply);
		System.out.println("reply received");
		
		//Unmarshal the receive object
		Message message = (Message) Marshallizer.unmarshall(reply);
		System.out.println("Reply: " + message.toString()); 
		
		//Object[] ListMatch = (Object[]) Marshallizer.unmarshall(message.getData());
		
		return null;	
	}
}
