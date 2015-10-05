package protocole;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import dataObject.Match;
import utils.Marshallizer;

public class Message implements Serializable {

		/**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		
		final static public int REQUEST_MATCH_LIST = 0;
		final static public int REQUEST_MATCH_DETAIL = 1;
		final static public int REPLY = 2;
		
		private int type;						// type de message request or reply
		private int numero; 					// numéro de la requéte originelle  

		private InetAddress  sender; 			// emetteur du message
		private int senderPort;					// port de l'emetteur du message
		
		//Just for logical 
		private InetAddress destination; 		// destinataire du message
		private int destinationPort;			// port du destinataire

		private Serializable value;				// dans le cas d'une requéte, les paramètres
												// dans le cas d'une réponse, les donnée associé
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
		public void setValue(Serializable value) {
			if(value != null){
				this.value = value;
			} else {
				this.value = new MessageError(1);
			}		
		}
		
		public byte[] getData() {		
			return Marshallizer.marshallize(this);
		}
		public int getLength() {
			return Marshallizer.marshallize(this).length;
		}
		public boolean isRequest() {
			return (type == REQUEST_MATCH_LIST) || isRequestDetail();
		}
		
		public boolean isRequestDetail() {
			return (type == REQUEST_MATCH_DETAIL) && (value != null);
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
		@Override
		public String toString() {
			String output = "Message [type=" + type + ", numero=" + numero + ", sender=" + sender + ", senderPort=" + senderPort
					+ ", destination=" + destination + ", destinationPort=" + destinationPort; 
			if (value != null) {
				if (value instanceof Match[]) {			
					output += 	", value=" + Arrays.toString( (Match[])value) + "]";
				} else {
					output += 	", value=" + value.toString() + "]";
				}
				
			}
			return output;	
		}
}
