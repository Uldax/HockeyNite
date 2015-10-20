

import org.apache.log4j.Logger;

import dataManagement.ListeDesMatchs;
import server.UDPServer;
import server.TCPServer;

public class StartPoint {
	private static final Logger logger = Logger.getLogger(StartPoint.class);
	
	public static void main(String[] args) {
		//Contain all data
		ListeDesMatchs matchList = ListeDesMatchs.getInstance();
		//Need to wait for instance of matchList before started the 2 thread that update value
		//matchList.setMultiplicateur(true);
		matchList.startThreadUpdate();
		
		//Create match service
		int port = 6780;
		int threadPoolSize = 4;	
		Thread matchServer = new Thread( new  UDPServer(port,threadPoolSize));
		matchServer.start();
	
		//Create paris service
                int portTCP = 1248;
                int threadPoolSizeTCP = 10;
                Thread betServer = new Thread(new TCPServer(portTCP,threadPoolSizeTCP));
                betServer.start();
	}

}
