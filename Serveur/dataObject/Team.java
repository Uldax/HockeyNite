package dataObject;

import java.io.Serializable;

public class Team implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8872344033691745328L;
	private String name;
	private int penalite = 0;
	private static final int PENALITY_TIME = 2*60;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Team(String name) {
		this.name = name;
	}
	public int getPenalite() {
		return penalite;
	}
	
	public void setPenalite(int penalite) {
		if(penalite >= PENALITY_TIME){
			this.penalite = 0;
		} else {
			this.penalite = penalite;
		}
	}
	
	//Tell if there is a penality 
	public boolean hasPenality(){
		if(penalite > 0)
			return true;
		else return false;
	}

	@Override
	public String toString() {
		return "Team [name=" + name + "]" + "penalite=" + hasPenality();
	}
}
