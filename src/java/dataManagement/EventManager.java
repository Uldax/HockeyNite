package dataManagement;

import java.util.Random;
import java.util.concurrent.Semaphore;


import dataObject.Event;
import dataObject.Match;
import dataObject.Team;

public class EventManager implements Runnable {
	
	
	private ListeDesMatchs data;
	private int timerEvent = 0;
	private Random r = new Random();
	
	public EventManager() {
		this.data = ListeDesMatchs.getInstance();
		
		this.timerEvent = ListeDesMatchs.MODIF_TIME * 5;
		
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
	        	int eventOnMatch = r.nextInt(2);   
	        	if (listMatch[eventOnMatch] != null){
	        		if (!listMatch[eventOnMatch].isPause()){
	        			Team teamEvent;
	        			if(r.nextInt(1) == 0){
	        				teamEvent = listMatch[eventOnMatch].getDomicile();
	        				listMatch[eventOnMatch].goalDomicile();
	        			} else{
	        				teamEvent = listMatch[eventOnMatch].getExterieur();
	        				listMatch[eventOnMatch].goalExterieur();
	        			}
	        			listMatch[eventOnMatch].addEvent(new Event("Goal " + teamEvent.toString()));
	        			
	        			//Penality probability 
	        			if(r.nextInt(30) < 5){
	        				//if there is no actual penality
	        				if( ! listMatch[eventOnMatch].getDomicile().hasPenality() && (r.nextInt(1) == 0) ){
	        					listMatch[eventOnMatch].getDomicile().setPenalite(1);
	        					listMatch[eventOnMatch].addEvent(new Event("Penality for " + teamEvent.toString()));
	        					
	        				}
	        				else if( ! listMatch[eventOnMatch].getExterieur().hasPenality() && (r.nextInt(1) == 0)){
	        					listMatch[eventOnMatch].getExterieur().setPenalite(1);
	        					listMatch[eventOnMatch].addEvent(new Event("Penality for " + teamEvent.toString()));
	        					
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
