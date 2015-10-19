package dataObject;

import java.io.Serializable;

public class TinyMatch implements Serializable{
	private static final long serialVersionUID = -790805962088102648L;
	private String teamA;
	private String teamB;
	private int time = 0;
	
	
	public String getTeamA() {
		return teamA;
	}
	public void setTeamA(String teamA) {
		this.teamA = teamA;
	}
	public String getTeamB() {
		return teamB;
	}
	public void setTeamB(String teamB) {
		this.teamB = teamB;
	}
	public int getTime() {
		return time;
	}
	public String getStringTime(){
		String matchSentence = "";
		int[] currentTime = splitToTimes();
		for( int j = 0; j < 3 ; j++)	{	
			matchSentence +=currentTime[j];
			if(j<2) {
				matchSentence+= " : ";
			}
		}
		return matchSentence;
	}
	public void setTime(int time) {
		this.time = time;
	}
	private int[] splitToTimes()
	{
	    int hours = (int) time / 3600;
	    int remainder = (int) time - hours * 3600;
	    int mins = remainder / 60;
	    remainder = remainder - mins * 60;
	    int secs = remainder;

	    int[] ints = {hours , mins , secs};
	    return ints;
	}
	
	public String toString(){
		String matchSentence = getTeamA() + " VS " +getTeamB() + " Timer :  "+getStringTime(); 
		return matchSentence;
	}
	
	
	
}
