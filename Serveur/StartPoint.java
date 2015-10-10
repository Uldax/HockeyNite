

import org.apache.log4j.Logger;

import dataManagement.ListeDesMatchs;
import server.UDPServer;

public class StartPoint {
	private static final Logger logger = Logger.getLogger(StartPoint.class);
	
	public static void main(String[] args) {
		//Contain all data
		//Warning must be thread safe
		ListeDesMatchs matchList = new ListeDesMatchs();
		
		//Create match service
		int port = 6780;
		int threadPoolSize = 4;	
		Thread matchServer = new Thread( new  UDPServer(port,threadPoolSize,matchList));
		matchServer.start();
	
		//Create paris service

	}

}
