package protocole;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import dataObject.Match;
import server.DAO;
import server.UDPServer;
import utils.Marshallizer;

public class MessageHandler implements Runnable {

	private Message message;
	private UDPServer myServer;
	private DAO data = null;
	private static final Logger logger = Logger.getLogger(MessageHandler.class);

	public MessageHandler(Message message, UDPServer udpServer) {
		super();
		this.message = message;
		this.myServer = udpServer;
		data = myServer.getData();
		logger.info("new runnable");
	}
	
	@Override
	public void run() {
		//build the response of the request
		Message reply = buildResponse(message);
		respond(reply);
		logger.info("reply done");
	}
	
	//Answer to the request
		private <T> Message buildResponse(Message request) {
			Message reply = new Message();
			reply.setType(Message.REPLY);
			reply.setDestinationPort(request.getSenderPort());
			reply.setDestination(request.getSender());
			//Access to dao and set resultat
			if(request.isRequestDetail()){
				@SuppressWarnings("unchecked")
				MessageParam<T> paramObject = (MessageParam<T>)request.getValue();
				Match matchDetail = data.getMatch((int)paramObject.getParam());
				reply.setValue(matchDetail);	
			} else {
				Match[] listMatch = data.getAllMatch();
				reply.setValue(listMatch);	
			}								
			logger.info("Message reply crafted");		
			return reply;
		}

	protected void respond(Message message) {
		DatagramSocket aSocket = null;
		try {
			aSocket = myServer.getMySocket();
			//Message.getData()
			logger.debug(message.toString());
			byte[] reply = Marshallizer.marshallize(message);
			DatagramPacket datagram = new DatagramPacket(reply,
					reply.length, 
					message.getDestination(),
					message.getDestinationPort());
			aSocket.send(datagram); // émission non-bloquante
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
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
