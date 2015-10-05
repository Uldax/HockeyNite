package server;

import org.apache.log4j.Logger;

public class StartPoint {
	private static final Logger logger = Logger.getLogger(StartPoint.class);
	
	public static void main(String[] args) {
		int port = 6780;
		int threadPoolSize = 10;
		UDPServer Server = new UDPServer(port,threadPoolSize);
		Server.start();

	}

}
