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
    	int choix = -1;
    	Object[] ListMatch = null;
    	InetAddress aHost = null;
    	int serveurPort = 6780;
    	int clientPort = 6779;
 		try {
 			aHost = InetAddress.getByName("localhost");
 		} catch (UnknownHostException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    	Communication.getInstance().setServeur(aHost, serveurPort, clientPort);
    	do{
    		
			System.out.println("R�cup�ration de la liste des matchs, veuillez patienter");
			ListMatch = Communication.getInstance().getListMatch();    			 
    		
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
    	Object Match = null;
			Match = Communication.getInstance().GetMatchDetail(idMatch);
		
    	// Affichage des infomations
		Match.toString();
    	
    	// R�ponse utilisateur
    }
    
 
   
}
