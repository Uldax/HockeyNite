package dataManagement;

import java.util.Random;
import org.apache.log4j.Logger;
import dataObject.Event;
import dataObject.Match;
import dataObject.Team;

public class EventManager implements Runnable {
	
	private static final Logger logger = Logger.getLogger(EventManager.class);
	private ListeDesMatchs data;
	private int timerEvent = 0;
	private Random r = new Random();
	
	public EventManager(ListeDesMatchs data) {
		this.data = data;
		this.timerEvent = ListeDesMatchs.MODIF_TIME * 5;
		logger.info("Event manager created");
	}
	
	@Override
	public void run() {
		randomValue();
	}
	
	private void randomValue(){
		while(true) {		
			synchronized (data) {
				Match[] listMatch = data.getAllMatch();
	        	int eventOnMatch = r.nextInt(2);        	
	        	if(listMatch[eventOnMatch] != null){
	        		if(!listMatch[eventOnMatch].isPause()){
	        			Team teamEvent;
	        			if(r.nextInt(1) == 0){
	        				teamEvent = listMatch[eventOnMatch].getDomicile();
	        				listMatch[eventOnMatch].goalDomicile();
	        			}else{
	        				teamEvent = listMatch[eventOnMatch].getExterieur();
	        				listMatch[eventOnMatch].goalExterieur();
	        			}
	        			listMatch[eventOnMatch].addEvent(new Event("Goal " + teamEvent.toString()));
	        			logger.info("Event manager - Goal " + teamEvent.toString());
	        		}
	        	}
	        	
	            try {
	            	int nextPossibleEvent = r.nextInt(timerEvent)+(timerEvent/2);
	                Thread.sleep(nextPossibleEvent);
	            } catch (InterruptedException e) {}
			}
        
        }
	}

}
