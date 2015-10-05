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
import protocole.MessageHandler;
import org.apache.log4j.Logger;
import utils.Marshallizer;

public class UDPServer {
	
	
	private DatagramSocket mySocket = null;

	//request thread pool
	private ExecutorService pool;
	// update play time every 30sec
	private ScheduledExecutorService scheduler =  null;
	
	private static final int INTERVAL_TIME = 10;
	private String serverIP = null;
	private int serverPort = 0;
	private DAO data = null;
	private static final Logger logger = Logger.getLogger(UDPServer.class);

	public UDPServer(int port,int poolSize){ 
		serverPort = port;
		serverIP = "127.0.0.1";
		data = new DAO();
		pool = Executors.newFixedThreadPool(poolSize);
		scheduler = Executors.newScheduledThreadPool(1);
	}	
	
	
	public void start() {
		mySocket = null;
		logger.info("server start on port " + String.valueOf(serverPort));
		startTimer();
		logger.info("timer scheduler started");
		try {
			mySocket = new DatagramSocket(serverPort); // port convenu avec les clients
			byte[] buffer = new byte[1000];
			while (true) {
				DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
				mySocket.receive(datagram); // réception bloquante
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

	public void stop() {
		// Stop the timmer
		//timerHandle.cancel(true);
		
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
	
	private void startTimer() {
		//Waring : writter
	   final Runnable timer = new TimeManager(data,INTERVAL_TIME);
	   // start the timer task
	   final ScheduledFuture<?> timerHandle = scheduler.scheduleAtFixedRate(timer, INTERVAL_TIME, INTERVAL_TIME, TimeUnit.SECONDS);
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
