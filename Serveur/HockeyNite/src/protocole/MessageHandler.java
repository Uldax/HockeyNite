package protocole;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import server.UDPServer;

public class MessageHandler implements Runnable {

	private Message message;
	private UDPServer myServer;
	private static final Logger logger = Logger.getLogger("Runable");

	public MessageHandler(Message message, UDPServer udpServer) {
		super();
		this.message = message;
		this.myServer = udpServer;
		logger.info("new runnable");
	}
	
	@Override
	public void run() {
		Message reply = buildResponse(message);
		respond(reply);
		logger.info("reply done");
	}
	
	//Answer to the request
		private Message buildResponse(Message request) {
			Message reply = new Message();
			reply.setType(Message.REPLY);
			reply.setDestinationPort(request.getSenderPort());
			reply.setDestination(request.getSender());
			//Access to dao and set resultat
			
			return reply;
		}

	protected void respond(Message message) {
		DatagramSocket aSocket = null;
		try {
			aSocket = new DatagramSocket(message.getDestinationPort()); // port convenu avec les clients
			while (true) {
				DatagramPacket datagram = new DatagramPacket(message.getData(),
						message.getLength(), 
						message.getDestination(),
						message.getDestinationPort());
				aSocket.send(datagram); // émission non-bloquante
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public UDPServer getMyServer() {
		return myServer;
	}

	public void setMyServer(UDPServer myServer) {
		this.myServer = myServer;
	}

}
