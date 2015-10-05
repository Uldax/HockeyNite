package test;
import java.net.*;

import utils.Marshallizer;
import protocole.Message;

import java.io.*; 
//UDP client sends a message to the server and gets a reply


public class UDPClient{

    public static void main(String args[]){ 
     // args give message contents and server hostname
     //byte[] msg = args[0].getBytes(); 
     //String hostname = args[1];

     DatagramSocket aSocket = null;

     try {
		// socket + port quelconque pour envoyer et recevoir la réponse
         //Envoi non blocant
    	 Message test = Protocole.craftMessage(); 
    	 aSocket = new DatagramSocket(); // port convenu avec les clients
    	 Protocole.respond(test,aSocket);
    	 
         System.out.println("message send"); 
         //réception bloquante 		                        
         byte[] buffer = new byte[1000];
         DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
         aSocket.receive(reply);
         System.out.println("reply reçu"); 
		Message message = (Message) Marshallizer.unmarshall(reply);
		System.out.println("Reply: " + message.toString()); 
       /*  MyObject MO = null;         
         try { // désériallisation de l'objet
			ByteArrayInputStream bis = new ByteArrayInputStream(reply.getData());
			ObjectInput in = null;
			in = new ObjectInputStream(bis);
			MO = (MyObject) in.readObject(); 
         } catch (IOException | ClassNotFoundException e1) {e1.printStackTrace();} 
         
         System.out.println("Reply: " + MO.toString()); */
     } 
     catch (SocketException e){System.out.println("Socket: " + e.getMessage());} 
     catch (IOException e){System.out.println("IO: " + e.getMessage());}
	 finally {if(aSocket != null) aSocket.close();}
    }
    
 
   
}
