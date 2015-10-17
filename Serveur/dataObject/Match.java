package dataObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Match implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8080958380151755727L;
        private int id = 0;
	private int time = 0;
	private Team domicile = null;
	private int domicileScore = 0;
	private Team exterieur = null;
	private int exterieurScore = 0;
	private List<Event> matchEvent = new ArrayList<Event>();
	//20 minute per periode with 15min of break
	private int periode;
	//for easy the time gestion
	private int periodeStart = 0;
	private boolean pause = false;
	private final int PERIODE_TIME = 20*60;
	private final int BREAK_TIME = 15*60;
	private final int MAX_TIME = 3 * PERIODE_TIME + 2 * BREAK_TIME;
	
	
	public Match(int id, Team domicile,Team exterieur){		
                this.id = id;
                this.domicile = domicile;
		this.exterieur = exterieur;
		this.periode = 1;
	}
	public int getTime() {
		return time;
	}
	
	//Set time 
	public void setTime(int time) {
		if( time <= MAX_TIME) {
			this.time = time;
			handlePeriode();
		}	
	}
	
	private void handlePeriode(){
		switch (periode) {
		case 1:
			if (time >= PERIODE_TIME && pause == false) {
				pause = true;
				matchEvent.add( new Event(time, "First time break"));
			}
			else if ((time >= PERIODE_TIME + BREAK_TIME) && pause == true) {
				pause = false;
				matchEvent.add( new Event(time, "Here we go for the second periode"));
				periodeStart = time;
				periode = 2;
			}
			break;
		case 2:
			if ((time >= periodeStart + PERIODE_TIME ) && pause == false ) {
				pause = true;
				matchEvent.add( new Event(time, "Second time break"));
			}
			else if ((time >= periodeStart + PERIODE_TIME + BREAK_TIME ) && pause == true) {
				pause = false;
				matchEvent.add( new Event(time, "here we go for the last periode"));
				periodeStart = time;
				periode = 3;
			}
			break;
		case 3:
			if ((time >= periodeStart + PERIODE_TIME ) && pause == false ) {
				pause = true;
				matchEvent.add( new Event(time, "This is the end of the game"));
			}	
			break;
		default:
			break;
		}
	}
	public Team getDomicile() {
		return domicile;
	}
	public void setDomicile(Team domicile) {
		this.domicile = domicile;
	}
	public synchronized void goalDomicile(){
		this.domicileScore += 1;
	}
	public Team getExterieur() {
		return exterieur;
	}
	public void setExterieur(Team exterieur) {
		this.exterieur = exterieur;
	}
	public synchronized void goalExterieur(){
		this.exterieurScore += 1;
	}
	public List<Event> getMatchEvent() {
		return matchEvent;
	}
	public void setMatchEvent(List<Event> matchEvent) {
		this.matchEvent = matchEvent;
	}
	public synchronized void addEvent(Event event){
		event.setTime(this.time);
		this.matchEvent.add(event);
	}
	public Boolean isPause(){
		return this.pause;
	}

        public int getPeriode() {
            return periode;
        }

    public int getId() {
        return id;
    }
        
        
	
	public int[] splitToTimes()
	{
	    int hours = (int) time / 3600;
	    int remainder = (int) time - hours * 3600;
	    int mins = remainder / 60;
	    remainder = remainder - mins * 60;
	    int secs = remainder;

	    int[] ints = {hours , mins , secs};
	    return ints;
	}
	
	@Override
	public String toString() {
		String echo =  "Match [time=" + time + ", domicile=" + domicile + ", domicileScore=" + domicileScore + ", exterieur="
				+ exterieur + ", exterieurScore=" + exterieurScore + ", matchEvent=" ; 
				for (Event e : matchEvent)
				{
				    echo += e.toString() + "\t";
				}
				echo += ", periode=" + periode + "]";
		return echo;
	}
}
