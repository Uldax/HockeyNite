package dataManagement;

import org.apache.log4j.Logger;

import dataObject.Match;

public class TimeManager implements Runnable {
	
	private static final Logger logger = Logger.getLogger(TimeManager.class);
	private ListeDesMatchs data;
	private int interval =0;
	
	public TimeManager(int interval) {
		this.data = ListeDesMatchs.getInstance();
		this.interval = interval;
		logger.info("Time manager with "+ String.valueOf(interval) + " interval created");
	}
	@Override
	public void run() {
		Match[] listMatch = data.getAllMatch();
		if(listMatch != null){
			updateMatchTime(listMatch);
			logger.info("Time updated");
		} else {
			logger.info("No match to update");
		}
	}
	
	//Thread safe
	private void updateMatchTime(Match[] listMatch){
		synchronized (listMatch) {		
			for(int i=0; i< listMatch.length; i++) {
				if(listMatch[i] != null && listMatch[i].getTime() < Match.MAX_TIME ){
					Match match = listMatch[i];
	                                logger.info("UpdateMatchTime: Match: " + String.valueOf(i) + " Old time: " + String.valueOf(match.getTime()) );
					match.setTime(match.getTime() + interval);
	                                logger.info("UpdateMatchTime: Match: " + String.valueOf(i) + " New Time: " + String.valueOf(match.getTime() ));        
				}
				
			}
		}
	}

}
