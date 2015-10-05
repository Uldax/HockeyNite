package server;

import org.apache.log4j.Logger;

import dataObject.Match;

public class TimeManager implements Runnable {
	
	private static final Logger logger = Logger.getLogger(TimeManager.class);
	private DAO data;
	private int interval =0;
	
	public TimeManager(DAO data,int interval) {
		this.data = data;
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
	
	private void updateMatchTime(Match[] listMatch){
		for(int i=0; i< listMatch.length; i++) {
			if(listMatch[i] != null){
				Match match = listMatch[i];
				match.setTime(match.getTime() + interval);
			}
			
		}
	}

}
