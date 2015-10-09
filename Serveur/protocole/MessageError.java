package protocole;

import java.io.Serializable;

public class MessageError implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8965673978924406565L;
	public final static int NULLPOINTEUR = 1;
	public final static int METHODEERROR = 2;
	private int code;
	private String message;
	
	public MessageError() {
		message = "An error apear";
	}
	
	public MessageError(int code){
		this.code = code;
		defineMessage(code);
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private void defineMessage(int code){
		switch (code) {
		case NULLPOINTEUR:
			message= "null pointer exeption";
			break;
		case METHODEERROR:
			message= "this method doesn't exist";
			break;
			
		default:
			message = "unknow error";
			break;
		}
	}

	@Override
	public String toString() {
		return "Error [code=" + code + ", message=" + message + "]";
	}
}
