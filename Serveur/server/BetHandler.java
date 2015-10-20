package server;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

//import org.apache.log4j.Logger;

import dataManagement.ListeDesMatchs;
import dataObject.Bet;
import dataObject.Match;


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
                BufferedInputStream bis = new BufferedInputStream(is);
                ObjectInputStream ois = new ObjectInputStream(bis);
                
                //We send a confirmation to the client
                OutputStream os = getConnectionSocket().getOutputStream();
                
                //On récupère l'objet Bet
                Bet currentBet = (Bet) ois.readObject(); //We wait for the object
                
                
               logger.info("We got currentBet: " + currentBet.getBetID());
               
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
                
            os.flush();
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
            List<Bet> betList = new ArrayList<>();
            String filename = "betsForMatch#" + String.valueOf( currentBet.getMatchID() );
            File f = new File(filename);
            FileOutputStream fos = new FileOutputStream(filename);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            if(f.exists()){
                
                FileInputStream fis = new FileInputStream(f);
                BufferedInputStream bis = new BufferedInputStream(fis);
                try (ObjectInputStream ois = new ObjectInputStream(bis)) {
                    betList = (List<Bet>) ois.readObject();
                    ois.close();
                }
            }
            betList.add(currentBet); 
            try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(betList);
                oos.flush();
                oos.close();
            }
            
            logger.info("saveBetOnDisk: currentBet: " + currentBet.getBetID());
                       
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
               
                List<Bet> betList = (List<Bet>) ois.readObject();
                ois.close();
                for(int i = 0; i< betList.size(); i++){
                    Bet currentBet = betList.get(i);
                    logger.info("getWinnerMap: betID:" + currentBet.getBetID()+ " for the matchID: #" + String.valueOf(matchID));
                    if( currentBet.getTeamName().equals(winnerTeamName) ){
                        logger.info("getWinnerMap: betID:" + currentBet.getBetID()+ " was added to the winnerTable" );
                        winnerTable.put(currentBet.getBetID(),currentBet);
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
