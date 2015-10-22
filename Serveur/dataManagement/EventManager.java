package dataManagement;

import java.util.Random;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

import dataObject.Event;
import dataObject.Match;
import dataObject.Team;

public class EventManager implements Runnable {
	
	private static final Logger logger = Logger.getLogger(EventManager.class);
	private ListeDesMatchs data;
	private int timerEvent = 0;
	private Random r = new Random();
	
	public EventManager() {
		this.data = ListeDesMatchs.getInstance();
		logger.info(data.toString());
		this.timerEvent = ListeDesMatchs.MODIF_TIME * 5;
		logger.info("Event manager created");
	}
	
	@Override
	public void run() {
		randomValue();
	}
	
	private void randomValue(){
		while (true) {	
			Semaphore dataSem = data.getSem();
			try {
				//acquire semaphore
				dataSem.acquire();
				Match[] listMatch = data.getAllMatch();
				//get ramdom match to update
	        	int eventOnMatch = r.nextInt(data.getAllMatchName().getList().size());   
	        	if (listMatch[eventOnMatch] != null){
	        		if (!listMatch[eventOnMatch].isPause() && listMatch[eventOnMatch].getTime() < Match.MAX_TIME ){
	        			Team teamEvent;
	        			if(r.nextInt(2) == 0){
	        				teamEvent = listMatch[eventOnMatch].getDomicile();
	        				listMatch[eventOnMatch].goalDomicile();
	        			} else{
	        				teamEvent = listMatch[eventOnMatch].getExterieur();
	        				listMatch[eventOnMatch].goalExterieur();
	        			}
	        			listMatch[eventOnMatch].addEvent(new Event(Event.GOAL,"Goal for team " + teamEvent.getName()));
	        			logger.info("Event manager - Goal " + teamEvent.toString());
	        			//Penality probability 
	        			if(r.nextInt(30) < 5){
	        				//if there is no actual penality
	        				if( ! listMatch[eventOnMatch].getDomicile().hasPenality() && (r.nextInt(1) == 0) ){
	        					listMatch[eventOnMatch].getDomicile().setPenalite(1);
	        					listMatch[eventOnMatch].addEvent(new Event(Event.PENALITY,"Penality for " + teamEvent.getName()));
	        					logger.info("Penality for domicile " + listMatch[eventOnMatch].getDomicile().toString());
	        				}
	        				else if( ! listMatch[eventOnMatch].getExterieur().hasPenality() && (r.nextInt(1) == 0)){
	        					listMatch[eventOnMatch].getExterieur().setPenalite(1);
	        					listMatch[eventOnMatch].addEvent(new Event(Event.PENALITY,"Penality for " + teamEvent.getName()));
	        					logger.info("Penality for exterior " + listMatch[eventOnMatch].getExterieur().toString());
	        				}
	        			}
	        		}
	        	}
	        	dataSem.release();   
	            int nextPossibleEvent = r.nextInt(timerEvent)+(timerEvent/2);
	            Thread.sleep(nextPossibleEvent);
			} catch (InterruptedException e) {
	            	
	        }
            finally {
            	dataSem.release();
         	}
        }
	}

}
