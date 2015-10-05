package protocole;

import java.io.Serializable;

public class MessageParam<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -46077520054353868L;
	private T param; 
	
	public MessageParam(T a){
		param=a;
	}

	public T getParam() {
		return param;
	}

	@Override
	public String toString() {
		return "MessageParam [param=" + String.valueOf(param) + "]";
	}


}
