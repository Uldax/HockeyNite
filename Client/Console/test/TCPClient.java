package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.apache.log4j.Logger;

import dataObject.Bet;


//UDP client sends a message to the server and gets a reply

public class TCPClient{
    
	private static final Logger logger = Logger.getLogger(TCPClient.class);
	
	/**
	 * Menu principal
	 * @param args
	 * @author CharlyBong & Uldax
	 */
    public static void main(String args[]){    	
    	int serveurPort = 1248;
        
        //Construction de quelques objet de bet
        DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        
        Bet b1 = new Bet(dateFormat.format(date) + "-BET1",1, "A", 25);
        Bet b2 = new Bet(dateFormat.format(date) + "-BET2",1, "A", 50);
        Bet b3 = new Bet(dateFormat.format(date) + "-BET3",1, "A", 100);
        Bet b4 = new Bet(dateFormat.format(date) + "-BET4",1, "B", 500);
        Bet b5 = new Bet(dateFormat.format(date) + "-BET5",1, "B", 250);
        
        Bet[] betArray = {b1,b2,b3,b4,b5};
        
        for(Bet b: betArray)
        {
         
            try {
                Socket sClient = new Socket("localhost", serveurPort);
                
                InputStream is = sClient.getInputStream();               
                
                OutputStream os = sClient.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                
                oos.writeObject(b);
                
                oos.flush();
                
                int result = is.read();
                
                if(result == 1)
                {
                 System.out.println("Succès pour l'objet b courant");
                }
                else if(result == 0)
                {
                 System.out.println("l'ajout à echoué, car la période est plus grande que 2");
                }
                else
                {
                    System.out.println("l'ajout à echoué, error de stream");
                }
                
                sClient.close();
                
                
                
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
   
    }
}