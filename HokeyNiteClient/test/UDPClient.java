package test;
import java.net.*;



import utils.Marshallizer;
import protocole.Message;
import protocole.Request;
import affichage.Menu;

import java.io.*; 
//UDP client sends a message to the server and gets a reply


public class UDPClient{

    public static void main(String args[]){ 
     // args give message contents and server hostname
     //byte[] msg = args[0].getBytes(); 
     //String hostname = args[1];

     DatagramSocket aSocket = null;

     try {
		// socket + port quelconque pour envoyer et recevoir la r�ponse
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
         
         //r�ception bloquante 		                        
         byte[] buffer = new byte[1000];
         DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
         aSocket.receive(reply);
         System.out.println("reply re�u"); 
         
         //Unmarshal the receive object
         Message message = (Message) Marshallizer.unmarshall(reply);
         System.out.println("Reply: " + message.toString()); 
 
     } 
     catch (SocketException e){System.out.println("Socket: " + e.getMessage());} 
     catch (IOException e){System.out.println("IO: " + e.getMessage());}
	 finally {if(aSocket != null) aSocket.close();}
    }
    
    public static void mainv2(){
    	int choix = -1;
    	Object[] ListMatch = null;
    	do{
    		DatagramSocket aSocket = null;
    		try {
    			// Demande de la liste des matchs
        	  	Protocole.askListMatch(aSocket);
        		
            	// R�cup�ration de la liste des matchs
        	  	ListMatch = Protocole.getListMatch(aSocket);
    	    } 
    	    catch (SocketException e){System.out.println("Socket: " + e.getMessage());} 
    	    catch (IOException e){System.out.println("IO: " + e.getMessage());} 
    		finally {if(aSocket != null) aSocket.close();}
    		
    		
    		if(ListMatch == null) return;
    		
    		
       	 	// Affichage de la liste des matchs
    		Menu.affListMatch(ListMatch);
    		
    		
        	// Choix dans le menu
    		System.out.println("Faites votre choix ?");
    		do{
    			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	    	try{
    	            choix = Integer.parseInt(br.readLine());
    	        }catch(NumberFormatException nfe){
    	            System.err.println("Invalid Format!");
    	            choix = -1;
    	        } catch (IOException e) {
    				e.printStackTrace();
    				choix = -1;
    			}        
    		}while(choix == -1);
    		if((choix > 0)&&(choix <= ListMatch.length)) detailMatch(choix-1);
    		//  r�cup�ration des d�tails du match et affichage
    		
    	}while(choix != 0);
    	// choix == 0 -> exit
    	// other choix -> refresh list 	
    }
    
    public static void detailMatch(int idMatch){
    	DatagramSocket aSocket = null;
    	Object Match = null;
		try {
			// Demande d'information sur le match
    	  	Protocole.askInfoMatch(aSocket, idMatch);
    		
    	  	// R�cup�ration des informations
    	  	Match = Protocole.getInfoMatch(aSocket);
	    } 
	    catch (SocketException e){System.out.println("Socket: " + e.getMessage());} 
	    catch (IOException e){System.out.println("IO: " + e.getMessage());} 
		finally {if(aSocket != null) aSocket.close();}
		
    	// Affichage des infomations
		Match.toString();
    	
    	// R�ponse utilisateur
    }
    
 
   
}
