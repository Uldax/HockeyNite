package protocole;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Arrays;

import dataObject.Match;

public class Reply extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2622663736791338175L;
	private Serializable value;				// dans le cas d'une requete, les parametres
	// dans le cas d'une reponse, les donnee associe
	
	public Serializable getValue() {
		return value;
	}
	public void setValue(Serializable value) {
		if(value != null){
			this.value = value;
		} else {
			this.value = new MessageError(1);
		}		
	}
	
	public Reply(InetAddress adress,int port,int messageID){
		type = Message.REQUEST;
		destinationPort = port;
		destination = adress;
		this.messageID = messageID;
	}
	
	
	@Override
	public String toString() {
		String output = super.toString();
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
