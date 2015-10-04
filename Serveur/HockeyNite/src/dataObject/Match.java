package dataObject;

public class Match {
	private int time = 0;
	private Team domicile = null;
	private int domicileScore = 0;
	private Team exterieur = null;
	private int exterieurScore = 0;
	private Event matchEvent[] = null;
	private int periode;
	
	
	public Match(Team domicile,Team exterieur){
		this.domicile = domicile;
		this.exterieur = exterieur;
		this.periode = 1;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public Team getDomicile() {
		return domicile;
	}
	public void setDomicile(Team domicile) {
		this.domicile = domicile;
	}
	public Team getExterieur() {
		return exterieur;
	}
	public void setExterieur(Team exterieur) {
		this.exterieur = exterieur;
	}
	public Event[] getMatchEvent() {
		return matchEvent;
	}
	public void setMatchEvent(Event[] matchEvent) {
		this.matchEvent = matchEvent;
	}
}
