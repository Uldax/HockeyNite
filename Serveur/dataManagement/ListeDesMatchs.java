package dataManagement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import dataObject.ListMatchName;
import dataObject.Match;
import dataObject.Team;

public class ListeDesMatchs {
	private static final Logger logger = Logger.getLogger(ListeDesMatchs.class);
	public final static int MAX_MATCH = 10;
	private static final int INTERVAL_TIME = 10;
	
	private Match ListMatch[] = new Match[MAX_MATCH];
	public static int MODIF_TIME = 5000;
	public Semaphore sem = null;
	
	// updateplay time every INTERVAL_TIME sec
	private ScheduledExecutorService scheduler =  null;
	private ScheduledFuture<?> timerHandle = null;
	
	
	/** Technique du Holder */
	private static class SingletonHolder
	{		
		/** Instance unique non préinitialise */
		private final static ListeDesMatchs instance = new ListeDesMatchs();
	}
 
	/** Point d'acces pour l'instance unique du singleton */
	public static ListeDesMatchs getInstance()
	{
		return SingletonHolder.instance;
	}
		
	//Initialisation with some ramdom value	
	private ListeDesMatchs(){
		sem = new Semaphore( 1);
		logger.info("Semaphore init");	
		Team t1 =  new Team("A");
		Team t2 =  new Team("B");
		Team t3 =  new Team("C");
		Team t4 =  new Team("D");
		Match M1 = new Match(1,t1,t2);
		Match M2 = new Match(2,t3,t4);
		ListMatch[0] = M1;
		ListMatch[1]  = M2;
		logger.info("ListDesMatch init");			
	}
	
	public void startThreadUpdate(){
		scheduler = Executors.newScheduledThreadPool(1);
		startTimer();
		startEventManager();	
	}
	
	//Get on match detail 
	public Match getMatch(int index){
		Match match = null;
		try {
			sem.acquire();
			if(ListMatch[index] != null){
				match = ListMatch[index];
			} else {
				logger.error("Undefined index");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			   sem.release();
		}
		return match;
	}
	
	//Get all match information
	public Match[] getAllMatch(){
		if (ListMatch.length < 1) {
		    return null;
		}
		return ListMatch;
	}
	
	//Get list of match name with associate id 
	public ListMatchName getAllMatchName(){
		if (ListMatch.length < 1) {
		    return null;
		}
		ListMatchName matchName = new ListMatchName(ListMatch);
		return matchName;
	}
	
	private void startEventManager(){
		new Thread(new EventManager()).start();
	}
	
	//Create a task that run every INTERVAL_TIME second
	//Manage the time of every Match
	private void startTimer() {
	   Runnable timer = new TimeManager(INTERVAL_TIME);
	   // start the timer task
	   timerHandle = scheduler.scheduleAtFixedRate(timer, INTERVAL_TIME, INTERVAL_TIME, TimeUnit.SECONDS);
	   logger.info("Timer scheduler started");
	}
	
	private void stopTimer(){
		if(timerHandle != null ){
			timerHandle.cancel(true);
		}
	}
	
	public Semaphore getSem() {
		return sem;
	}

	public void setSem(Semaphore sem) {
		this.sem = sem;
	}
}
