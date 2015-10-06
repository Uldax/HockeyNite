package test;
import java.net.*;

import utils.Marshallizer;
import protocole.Message;
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
    	 
    	 // socket + port quelconque pour envoyer et recevoir la réponse
    	 Message test = Protocole.craftMessage(); 
    	 aSocket = new DatagramSocket(6779); 
    	 //send
    	 Protocole.respond(test,aSocket);
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
    
    public static void mainv2(){
    	int choix = -1;
    	Object[] ListMatch = null;
    	do{
    		DatagramSocket aSocket = null;
    		try {
    			// Demande de la liste des matchs
        	  	Protocole.askListMatch(aSocket);
        		
            	// Récupération de la liste des matchs
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
    		//  récupération des détails du match et affichage
    		
    	}while(choix != 0);
    	// choix == 0 -> exit
    	// other choix -> refresh list 	
    }
    
    public static void detailMatch(int id){
    	// Demande d'information sur le match
    	
    	// Récupération des informations
    	
    	// Affichage des infomations
    	
    	// Réponse utilisateur
    }
    
 
   
}
