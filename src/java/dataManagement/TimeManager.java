package dataManagement;



import dataObject.Match;

public class TimeManager implements Runnable {
	
	
	private ListeDesMatchs data;
	private int interval =0;
	
	public TimeManager(int interval) {
		this.data = ListeDesMatchs.getInstance();
		this.interval = interval;
		
	}
	@Override
	public void run() {
		Match[] listMatch = data.getAllMatch();
		if(listMatch != null){
			updateMatchTime(listMatch);
			
		} else {
			
		}
	}
	
	//Thread safe
	private synchronized void updateMatchTime(Match[] listMatch){
		for(int i=0; i< listMatch.length; i++) {
			if(listMatch[i] != null){
				Match match = listMatch[i];
                               
				match.setTime(match.getTime() + interval);
                                        
			}
			
		}
	}

}
