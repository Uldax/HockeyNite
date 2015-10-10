package dataManagement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;


import dataObject.Match;
import dataObject.Team;
public class ListeDesMatchs {
	private static final Logger logger = Logger.getLogger(ListeDesMatchs.class);
	public final static int MAX_MATCH = 10;
	private static final int INTERVAL_TIME = 10;
	
	private Match ListMatch[] = new Match[MAX_MATCH];
	public static int MODIF_TIME = 5000;
	
	// update play time every INTERVAL_TIME sec
	private ScheduledExecutorService scheduler =  null;
	private ScheduledFuture<?> timerHandle = null;
	
	
	//Initialisation with some ramdom value	
	public ListeDesMatchs(){
		Team t1 =  new Team("A");
		Team t2 =  new Team("B");
		Team t3 =  new Team("C");
		Team t4 =  new Team("D");
		Match M1 = new Match(t1,t2);
		Match M2 = new Match(t3,t4);
		ListMatch[0] = M1;
		ListMatch[1]  = M2;
		logger.info("Dao ini");		
		scheduler = Executors.newScheduledThreadPool(1);
		startTimer();
		this.startEventManager();		
	}
	
	//Get on match detail 
	public synchronized Match getMatch(int index){
		if(ListMatch[index] != null){
			return ListMatch[index];
		} else {
			logger.error("Undefined index");
			return null;
		}
	}
	
	//Get all match information
	public Match[] getAllMatch(){
		if (ListMatch.length < 1) {
		    return null;
		}
		return ListMatch;
	}
	
	private void startEventManager(){
		new Thread(new EventManager(this)).start();
	}
	
	//Create a task that run every INTERVAL_TIME second
	//Manage the time of every Match
	private void startTimer() {
	   Runnable timer = new TimeManager(this,INTERVAL_TIME);
	   // start the timer task
	   timerHandle = scheduler.scheduleAtFixedRate(timer, INTERVAL_TIME, INTERVAL_TIME, TimeUnit.SECONDS);
	   logger.info("Timer scheduler started");
	}
	
	private void stopTimer(){
		if(timerHandle != null ){
			timerHandle.cancel(true);
		}
	}
}
