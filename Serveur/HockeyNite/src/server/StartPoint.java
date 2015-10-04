package server;

import org.apache.log4j.Logger;

public class StartPoint {
	private static final Logger logger = Logger.getLogger(StartPoint.class);
	
	public static void main(String[] args) {
		UDPServer Server = new UDPServer();
		Server.start();

	}

}
