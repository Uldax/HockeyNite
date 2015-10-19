package server;


import java.io.IOException;


import java.net.SocketException;

//import org.apache.log4j.Logger;

import dataManagement.ListeDesMatchs;
import dataObject.ListMatchName;
import dataObject.Match;
import dataObject.Bet;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import protocole.Message;
import protocole.MessageError;
import protocole.Reply;
import protocole.Request;


//Extact data from datagram
//send respons
public class BetHandler implements Runnable {
        
       private Socket connectionSocket = null;
       private ListeDesMatchs data = null;
       private static final Logger logger = Logger.getLogger(BetHandler.class);
       

	public BetHandler(Socket connectionSocket) {
		super();
		this.connectionSocket = connectionSocket;		
		logger.info("new runnable");
	}
	
	@Override
	public void run() {
            try{
                //extract data from packet
		data = ListeDesMatchs.getInstance();
                
                //Déclaration des streams que nous allons utiliser
                //We retreive the currentBet 
                InputStream is = getConnectionSocket().getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                
                //We send a confirmation to the client
                OutputStream os = getConnectionSocket().getOutputStream();
                
                //On récupère l'objet Bet
                Bet currentBet = (Bet) ois.readObject(); //We wait for the object
                
               logger.info("We got currentBet: " + currentBet.toString());
               
               //On s'assure que la période est est >= 2
               Match matchDetail = data.getMatch(currentBet.getMatchID());
               
               if(matchDetail.getPeriode() <= 2){
                    //We save the bet on the disk
                    saveBetOnDisk(currentBet);

                    //We update match betting amount
                    updateTotalBettingAmount(currentBet);                

                    logger.info("The bet is saved on the disk");
                    
                    os.write(1); // The bet was save
                    
                    logger.info("The client should receive an ACK");
               }
               else
               {
                   os.write(0); // The bet wasnt save and we send an error to the client
                   
                   logger.info("The client should receive an error code 0");
                }
                

            } catch(Exception e) {
                    e.printStackTrace();
                    try{
                        //Something went wrong we tell the client
                        OutputStream os = getConnectionSocket().getOutputStream();  
                        os.write(-1); // Something went wrong

                    }catch(Exception e2) {
                         e2.printStackTrace();
                    }
            } 
         }
	
    private synchronized void saveBetOnDisk(Bet currentBet) throws IOException{ 
        try{	
            //New file named match#TheID and we set append @ true
            FileOutputStream fout = new FileOutputStream("betsForMatch#" + String.valueOf( currentBet.getMatchID() ),true);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            logger.info("saveBetOnDisk: currentBet: " + currentBet.getBetID());
            oos.writeObject(currentBet);
            oos.close();           
            logger.info("saveBetOnDisk: Object was saved on the disk");
	}catch(Exception ex){
		   ex.printStackTrace();
	}
       
    };
    private synchronized void updateTotalBettingAmount(Bet currentBet) throws IOException{ 
        try{
        
            String filename = "totalBettingAmountForMatch#" + String.valueOf( currentBet.getMatchID() );
            float totalBetting = 0;
            //On valide que le fichier existe déja
            File f = new File(filename);
            if(f.exists() && !f.isDirectory()) { 
                //Le fichier existe déja alors on va chercher la valeur
                FileInputStream fis = new FileInputStream(f);
                DataInputStream dis = new DataInputStream(fis);            
                totalBetting = dis.readFloat();            
                dis.close();
            }        

           FileOutputStream fos = new FileOutputStream(filename);
           DataOutputStream dos = new DataOutputStream(fos);     
           totalBetting = totalBetting + currentBet.getBetAmount();      
           dos.writeFloat(totalBetting);
           dos.close();
           logger.info("updateTotalBettingAmount: The total betting amount was updated it is now: " + String.valueOf(totalBetting) + "$");
	}catch(Exception ex){
		   ex.printStackTrace();
	}       
    };
    
    static public synchronized float getTotalBettingAmount(int matchID) throws IOException{ 
       float totalBetting = 0;
       String filename = "totalBettingAmountForMatch#" + String.valueOf( matchID );
        try{  
            //On valide que le fichier existe déja
            File f = new File(filename);
            if(f.exists() && !f.isDirectory()) { 
                //Le fichier existe déja alors on va chercher la valeur
                FileInputStream fis = new FileInputStream(f);
                DataInputStream dis = new DataInputStream(fis);            
                totalBetting = dis.readFloat();            
                dis.close();
            }            
           
	}catch(Exception ex){
		   ex.printStackTrace();
	}  
        return totalBetting;
    };
    
    static public synchronized Hashtable<String, Bet> getWinnerTable(Match matchDetail){
        Hashtable<String, Bet> winnerTable = new Hashtable<String, Bet>();
        int matchID = matchDetail.getId();
        boolean loopCondition = true;
        String winnerTeamName = matchDetail.getWinner();        
        try{  
            //On valide que le fichier existe déja
            File f = new File("betsForMatch#" + String.valueOf( matchID ));
            if(f.exists() && !f.isDirectory()) { 
                //Le fichier existe déja alors on va chercher la valeur
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);            
                while (loopCondition) {
                    try {
                        // Read the next object from the stream. If there is none, the
                        // EOFException will be thrown.
                        // This call might also throw a ClassNotFoundException, which can be passed
                        // up or handled here.
                        Bet currentBet = (Bet) ois.readObject();
                        if( currentBet.getTeamName() == winnerTeamName ){
                            winnerTable.put(currentBet.getBetID(),currentBet);
                        }            
                    } catch (EOFException e) {
                        // If there are no more objects to read, we break the while with what we have.
                        loopCondition = false;
                    } finally {
                        // Close the stream.
                        ois.close();
                    }
                }    
            }      
        }catch(Exception ex){
            ex.printStackTrace();
	}
        logger.info("getWinnerMap: a Winner map was returned for the matchID: #" + String.valueOf(matchID));
        return winnerTable;
    };
    
    public Socket getConnectionSocket() {
        return connectionSocket;
    }

    public void setConnectionSocket(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

}
