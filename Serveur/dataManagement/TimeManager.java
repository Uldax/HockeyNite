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
	private synchronized void updateMatchTime(Match[] listMatch){
		for(int i=0; i< listMatch.length; i++) {
			if(listMatch[i] != null){
				Match match = listMatch[i];
				match.setTime(match.getTime() + interval);
				//Update penality time
				if(match.getDomicile().hasPenality()){
					match.getDomicile().setPenalite(match.getDomicile().getPenalite() + interval);
					logger.info("update penality time for domicile");
				} 
				else if (match.getExterieur().hasPenality()){
					match.getExterieur().setPenalite(match.getExterieur().getPenalite() + interval);
					logger.info("update penality time for exterieur");
				}
			}
			
		}
	}

}
