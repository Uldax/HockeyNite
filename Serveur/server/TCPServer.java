package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;


/**
 *
 * @author JUASP-G73-Android
 */
public class TCPServer implements Runnable {
    
    //Private variables
    private ServerSocket mySocket = null;
    private Socket connectionSocket = null;


    private ExecutorService pool;
    private int serverPort = 0;
    
    private static final Logger logger = Logger.getLogger(TCPServer.class);
    
    //Constructor
    public TCPServer(int port,int poolSize){ 
        this.serverPort = port;
        this.pool = Executors.newFixedThreadPool(poolSize);
    }
    
    //Starting the server
    public void start() {
        
        logger.info("tcp server started on port " + String.valueOf(serverPort));
        
        try {
            mySocket = new ServerSocket(serverPort); // port convenu avec les clients
            while (true) {
                setConnectionSocket( mySocket.accept() ); //// réception bloquante
                
                logger.info("La connexion est ouverte " + getConnectionSocket().toString());
                
                pool.execute(new BetHandler(getConnectionSocket()));					
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
                    if (!pool.awaitTermination(60, TimeUnit.SECONDS)) logger.debug("Pool did not terminate");
                }
            } catch (InterruptedException ie) {
                    // (Re-)Cancel if current thread also interrupted
                    pool.shutdownNow();
                    // Preserve interrupt status
                    Thread.currentThread().interrupt();
            }
            finally
            {
                if (connectionSocket != null) {
                    try {
                        connectionSocket.close();
                    }
                    catch (final IOException e) {
                        logger.info("Unable to write log to file.");
                    }                    
                }                    
            }
        } 
    
    @Override
    public void run() {
        start();
    }
    
    public ServerSocket getMySocket() {
        return mySocket;
    }

    public Socket getConnectionSocket() {
        return connectionSocket;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setMySocket(ServerSocket mySocket) {
        this.mySocket = mySocket;
    }

    public void setConnectionSocket(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
    
    
    
    
}
