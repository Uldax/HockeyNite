package protocole;

import java.net.InetAddress;

public class Request extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 361933411720337979L;
	public enum methodes {
		list, detail
	};
	
	private static final int MAX_NUM = 50000;
	private Object[] argument; // positionGPS  positionGPS  noTaxi
	private methodes methode = null;
	private static int numRequest = 0;
	
	public Request(){
		type = Message.REQUEST;
	}
	
	//warning race condition
	static synchronized public Request craftGetMatchList(InetAddress adress, int port) {
		Request request = new Request();
		request.setMethode(methodes.list);
		request.setNumero(numRequest);
		request.setDestinationPort(port);
		request.setDestination(adress);
		incrementNumRequest();
		return request;
	}

	static synchronized public Request craftGetMatchDetail(InetAddress adress, int port,int idMatch) {
		Request request = new Request();
		request.setMethode(methodes.detail);
		Object[] arg = {idMatch};
		request.setArgument(arg);
		request.setDestinationPort(port);
		request.setDestination(adress);
		request.setNumero(numRequest);
		incrementNumRequest();
		return request;
	}
	
	static void incrementNumRequest(){
		if(numRequest == MAX_NUM){
			numRequest = 0;
		}
		numRequest ++;
	}

	public Object[] getArgument() {
		return argument;
	}
	public void setArgument(Object[] argument) {
		this.argument = argument;
	}
	public methodes getMethode() {
		return methode;
	}
	public void setMethode(methodes methode) {
		this.methode = methode;
	}
}
