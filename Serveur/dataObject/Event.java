package dataObject;

import java.io.Serializable;

public class Event implements Serializable {

	private static final long serialVersionUID = -5590704311853640594L;
	private int time;
	private String message;
	private int type;
	
	final static public int GOAL = 0;
	final static public int PENALITY = 1;

	public Event(int type, String message){
		this.type = type;
		this.time = 0;
		this.message = message;
	}
	public Event(int type, int time,String message){
		this.type = type;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
