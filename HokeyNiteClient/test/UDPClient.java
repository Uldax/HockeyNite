package test;
import java.net.*;



import utils.Marshallizer;
import protocole.Message;
import protocole.Request;

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
    	 	InetAddress aHost = null;
	 		try {
	 			aHost = InetAddress.getByName("localhost");
	 		} catch (UnknownHostException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
    	  Message test = Request.craftGetMatchList(aHost,6780); 
    	 aSocket = new DatagramSocket(6779); 
    	 //send
    	 Protocole.send(test,aSocket);
         System.out.println("message send"); 
         
         //réception bloquante 		                        
         byte[] buffer = new byte[1000];
         DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
         aSocket.receive(reply);
         System.out.println("reply reçu"); 
         
         //Unmarshal the receive object
         Message message = (Message) Marshallizer.unmarshall(reply);
         System.out.println("Reply: " + message.toString()); 
 
     } 
     catch (SocketException e){System.out.println("Socket: " + e.getMessage());} 
     catch (IOException e){System.out.println("IO: " + e.getMessage());}
	 finally {if(aSocket != null) aSocket.close();}
    }
    
 
   
}
