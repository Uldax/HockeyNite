package server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import protocole.Message;
import protocole.MessageHandler;
import org.apache.log4j.Logger;
import utils.Marshallizer;

public class UDPServer {
	
	
	private DatagramSocket mySocket = null;

	//thread pool
	private ExecutorService pool;
	private String serverIP = null;
	private int serverPort = 0;
	private DAO data = null;
	private static final Logger logger = Logger.getLogger(UDPServer.class);


	//UDP server repeatedly receives a request and sends it back to the client 
	public UDPServer(int port,int poolSize){ 
		serverPort = port;
		serverIP = "127.0.0.1";
		data = new DAO();
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
				logger.info("data receive");
				Message message = (Message) Marshallizer.unmarshall(datagram);
				logger.info("message receive " + String.valueOf(message.getType()));
				//IF client ask something
				if (message.isRequest()) {
					logger.info("start thread for answer");
					//What append if pool full ?
		            pool.execute(new MessageHandler(message,this));
					//new Thread(new MessageHandler(message,this)).start();					
				} else {
					//new Thread(new ReplyHandler((Reply) message, this)).start();
				}
				logger.info("end of reception");
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}

		finally {
			if (mySocket != null)
				mySocket.close();
		}

	}

	public void stopServer() {
	   pool.shutdown(); // Disable new tasks from being submitted
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
	//curent id
	public void manageDuplicate(){
		
	}


	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
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
	public DAO getData() {
		return data;
	}

	public void setData(DAO data) {
		this.data = data;
	}
}