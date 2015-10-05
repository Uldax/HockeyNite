package dataObject;

import java.io.Serializable;

public class Event implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5590704311853640594L;
	private int time;
	private String message;

	
	public Event(int time,String message){
		this.time = time;
		this.message = message;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Event [time=" + time + ", message=" + message + "]";
	}
}
