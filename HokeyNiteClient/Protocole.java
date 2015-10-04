import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import utils.Marshallizer;
import utils.Message;

public class Protocole {
	public static Message craftMessage(){
		Message reply = new Message();
		reply.setType(Message.REPLY);
		reply.setDestinationPort(6780);
		InetAddress aHost = null;
		try {
			aHost = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reply.setDestination(aHost);
		//reply.setValue(new MyObject(1, "coucou"));
		return reply;
	}

	public static void respond(Message message) {
		DatagramSocket aSocket = null;
		try {
			aSocket = new DatagramSocket(); // port convenu avec les clients
				//Message.getData()
				System.out.println("destination is "+ message.getDestination() + "porc " + message.getDestinationPort());
				DatagramPacket datagram = new DatagramPacket(Marshallizer.marshallize(message),
						message.getLength(), 
						message.getDestination(),
						message.getDestinationPort());
				aSocket.send(datagram); // émission non-bloquante
			
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}
	}
}
