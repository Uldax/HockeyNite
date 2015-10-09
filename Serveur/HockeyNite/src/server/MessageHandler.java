package server;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import dataObject.Match;
import protocole.Message;
import protocole.MessageError;
import protocole.Reply;
import protocole.Request;
import utils.Marshallizer;

//Extact data from datagram
//send respons
public class MessageHandler implements Runnable {

	private UDPServer myServer;
	private DAO data = null;
	private static final Logger logger = Logger.getLogger(MessageHandler.class);
	private DatagramPacket packetReceive;

	public MessageHandler(DatagramPacket packetReceive, UDPServer udpServer) {
		super();
		this.packetReceive = packetReceive;
		this.myServer = udpServer;
		logger.info("new runnable");
	}
	
	@Override
	public void run() {
		//extract data from packet
		data = myServer.getData();
		Message message = (Message) Marshallizer.unmarshall(packetReceive);
		logger.info("message receive " + String.valueOf(message.getType()));	
		if (message.isRequest()) {
			//build the response of the request
			logger.info("build answer");
			Reply reply = buildResponse((Request)message);
			respond(reply);
		}
		logger.info("reply done");
	}
	
		//Answer to the request
		private Reply buildResponse(Request request) {
			Reply reply = new Reply(packetReceive.getAddress(),packetReceive.getPort(),request.getNumero());
			switch(request.getMethode()) {
			case list :
				Match[] listMatch = data.getAllMatch();
				reply.setValue(listMatch);	
				break;
				
			case detail:
				Object[] arguments = request.getArgument();
				int matchID = (int) arguments[0];
				Match matchDetail = data.getMatch(matchID);
				reply.setValue(matchDetail);			
				break;
				
			default:
				reply.setValue(new MessageError(MessageError.METHODEERROR)); 
				break;
			}								
			logger.info("Message reply crafted");		
			return reply;
		}

	protected void respond(Reply message) {
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


}
