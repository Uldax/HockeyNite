import java.net.*; import java.io.*; 
//UDP client sends a message to the server and gets a reply


public class UDPClient{

    public static void main(String args[]){ 
     // args give message contents and server hostname
     byte[] msg = args[0].getBytes(); 
     String hostname = args[1];

     DatagramSocket aSocket = null;

     try {
		// socket + port quelconque pour envoyer et recevoir la réponse
         aSocket = new DatagramSocket();
         InetAddress aHost = InetAddress.getByName(hostname);
         int serverPort = 6789;  // port convenu avec le serveur
	     //émission non-bloquante 	
         DatagramPacket request = new DatagramPacket(msg, msg.length, aHost, serverPort);
         aSocket.send(request);
         
	     //réception bloquante 		                        
         byte[] buffer = new byte[1000];
         DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
         aSocket.receive(reply);
         
         MyObject MO = null;         
         try { // désériallisation de l'objet
			ByteArrayInputStream bis = new ByteArrayInputStream(reply.getData());
			ObjectInput in = null;
			in = new ObjectInputStream(bis);
			MO = (MyObject) in.readObject(); 
         } catch (IOException | ClassNotFoundException e1) {e1.printStackTrace();} 
         
         System.out.println("Reply: " + MO.toString());
     } 
     catch (SocketException e){System.out.println("Socket: " + e.getMessage());} 
     catch (IOException e){System.out.println("IO: " + e.getMessage());}
	 finally {if(aSocket != null) aSocket.close();}
    }
}
