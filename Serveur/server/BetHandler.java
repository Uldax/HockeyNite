package server;


import java.io.IOException;


import java.net.SocketException;

//import org.apache.log4j.Logger;

import dataManagement.ListeDesMatchs;
import dataObject.ListMatchName;
import dataObject.Match;
import dataObject.Bet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
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
                //We retreive the currentBet 
                InputStream is = getConnectionSocket().getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                Bet currentBet = (Bet) ois.readObject(); //We wait for the object
                
               logger.info("We got currentBet: " + currentBet.toString());
               
                //We save the bet on the disk
                saveOnDisk(currentBet);

                logger.info("The bet is saved on the disk");
                
                //We send a confirmation to the client
                OutputStream os = getConnectionSocket().getOutputStream();  
                os.write(1); // The bet was save
                
                logger.info("The client should receive an ACK");

            } catch(Exception e) {
                    e.printStackTrace();
                    try{
                        //Something went wrong we tell the client
                        OutputStream os = getConnectionSocket().getOutputStream();  
                        os.write(0); // Something went wrong

                    }catch(Exception e2) {
                         e2.printStackTrace();
                    }
            } 
         }
	
    private void saveOnDisk(Bet currentBet) throws IOException{ 
        try{	
            //New file named match#TheID and we set append @ true
            FileOutputStream fout = new FileOutputStream("match#" + String.valueOf( currentBet.getMatchID() ),true);
            ObjectOutputStream oos = new ObjectOutputStream(fout);   
            oos.writeObject(currentBet);
            oos.close();
		   
	}catch(Exception ex){
		   ex.printStackTrace();
	}
       
    };
    public Socket getConnectionSocket() {
        return connectionSocket;
    }

    public void setConnectionSocket(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

}
