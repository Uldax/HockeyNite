package test;
import java.net.*;

import org.apache.log4j.Logger;

import utils.Marshallizer;
import protocole.Message;
import protocole.Request;
import affichage.Menu;

import dataObject.ListMatchName;

import java.io.*; 
//UDP client sends a message to the server and gets a reply


public class UDPClient{
    
	private static final Logger logger = Logger.getLogger(UDPClient.class);
	
    public static void main(String args[]){
    	int choix = -1;
    	Object[] ListMatch = null;
    	ListMatchName matchList = null;
    	InetAddress aHost = null;
    	int serveurPort = 6780;
    	int clientPort = 6779;
    	Communication commObject = Communication.getInstance();
    	
 		try {
 			aHost = InetAddress.getByName("localhost");
 		} catch (UnknownHostException e) {
 			e.printStackTrace();
 		}
 		
 		//Set server port and host
    	commObject.setServeur(aHost, serveurPort, clientPort);
    	do{
    		
			System.out.println("Récupération de la liste des matchs, veuillez patienter");
			//ListMatch = commObject.getListMatch(); 
			// Affichage de la liste des matchs
    		//Menu.affListMatch(ListMatch);
			
			//Send datagrame in new thread and wait for answer
			matchList = commObject.getListMatchName();   
    		if(matchList == null) return;
    		//Display the available match
    		Menu.affListMatchName(matchList);
       	 		
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
    		//if((choix > 0)&&(choix <= ListMatch.length)) {
    		logger.info("Choix = " + choix + " and contain = " + matchList.getMatchName().containsKey( (choix-1 ) ));
    		
    		if ((choix > 0)&& (matchList.getMatchName().containsKey(choix-1))){
    	   		logger.info("call to detailMatch");
    			detailMatch(choix-1);
    		}
    		//  récupération des détails du match et affichage
    		
    	}while(choix != 0);
    	// choix == 0 -> exit
    	// other choix -> refresh list
    	
    	System.out.println("Bye :3");
    }
    
    public static void detailMatch(int idMatch){
    	Object Match = null;
    	int choix = 0;
    	do{
    		Match = Communication.getInstance().getMatchDetail(idMatch);
			if(Match == null) return;

        	// Affichage des infomations
    		Menu.affDetailsMatch(Match);
    		
    		// Réponse utilisateur
    		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    	try{
	            choix = Integer.parseInt(br.readLine());
	        }
	    	catch(NumberFormatException nfe){} 
	    	catch (IOException e) {}        
    		
    	}while(choix != 0);
    	// choix = 0 -> back
    	// else refresh
    }
    
 
   
}
