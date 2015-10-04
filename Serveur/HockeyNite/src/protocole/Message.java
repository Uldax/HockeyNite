package protocole;

import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import utils.Marshallizer;

public class Message implements Serializable {

		/**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		public static Message unmarshall(DatagramPacket datagram) {
			 
			return null;
		}
		
		final static public int REQUEST = 0;
		final static public int REPLY = 1;
		
		private int type;					// type de message request or reply
		private int numero; 				// numéro de la requéte originelle  

		private InetAddress  sender; 			// emetteur du message
		private int senderPort;				// port de l'emetteur du message
		
		//Just for logical 
		private InetAddress destination; 		// destinataire du message
		private int destinationPort;					// port du destinataire

		private Object value;				// dans le cas d'une requéte, le nom symbolique dont on recherche l'adresse IP
											// dans le cas d'une rŽponse, l'adresse IP correspondant au nom symbolique
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public int getNumero() {
			return numero;
		}
		public void setNumero(int numero) {
			this.numero = numero;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		
		public byte[] getData() {		
			return Marshallizer.marshallize(this);
		}
		public int getLength() {
			return Marshallizer.marshallize(this).length;
		}
		public boolean isRequest() {
			return type == REQUEST;
		}
		public InetAddress getDestinationAsInet() {
			// TODO Auto-generated method stub
			return null;
		}
		public InetAddress getSender() {
			return sender;
		}
		public void setSender(InetAddress sender) {
			this.sender = sender;
		}
		public void setSender(String sender) {
			try {
				this.sender = InetAddress.getByName(sender);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public int getSenderPort() {
			return senderPort;
		}
		public void setSenderPort(int senderPort) {
			this.senderPort = senderPort;
		}
		public InetAddress getDestination() {
			return destination;
		}
		public int getDestinationPort() {
			return destinationPort;
		}
		public void setDestinationPort(int destinationPort) {
			this.destinationPort = destinationPort;
		}
		public void setDestination(InetAddress destination) {
			this.destination = destination;
		}
}
