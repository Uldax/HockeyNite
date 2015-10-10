package server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


import protocole.Message;

import org.apache.log4j.Logger;

import dataManagement.ListeDesMatchs;
import utils.Marshallizer;

public class UDPServer implements Runnable{

	private DatagramSocket mySocket = null;

	//request thread pool
	private ExecutorService pool;

	private int serverPort = 0;
	private ListeDesMatchs listMatch = null;
	private static final Logger logger = Logger.getLogger(UDPServer.class);

	public UDPServer(int port,int poolSize,ListeDesMatchs list){ 
		serverPort = port;
		listMatch = list;
		pool = Executors.newFixedThreadPool(poolSize);
	}	
	
	
	public void start() {
		mySocket = null;
		logger.info("server start on port " + String.valueOf(serverPort));
		try {
			mySocket = new DatagramSocket(serverPort); // port convenu avec les clients
			byte[] buffer = new byte[1000];
			while (true) {
				DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
				mySocket.receive(datagram); // r�ception bloquante
				logger.info("datafram receive");						
				//What append if pool full ?
	            pool.execute(new MessageHandler(datagram,this));					
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
		finally {
			logger.info("end of reception");
			stop();
		}
	}
	//Stop the server in the clean way
	public void stop() {
		// Disable new tasks from being submitted
		pool.shutdown(); 
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
					logger.debug("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
		if (mySocket != null) {
			mySocket.close();
		}
	}
	
	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public DatagramSocket getMySocket() {
		return mySocket;
	}

	public void setMySocket(DatagramSocket mySocket) {
		this.mySocket = mySocket;
	}
	public ListeDesMatchs getListMatch() {
		return listMatch;
	}

	public void setData(ListeDesMatchs listMatch) {
		this.listMatch = listMatch;
	}


	@Override
	public void run() {
		start();	
	}
}
