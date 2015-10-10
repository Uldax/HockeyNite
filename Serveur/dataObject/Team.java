package dataObject;

import java.io.Serializable;

public class Team implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8872344033691745328L;
	private String name;
	
	public Team(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Team [name=" + name + "]";
	}
}