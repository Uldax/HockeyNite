import java.io.Serializable;

public class MyObject implements Serializable {

	private static final long serialVersionUID = 1L;
	int a;
	String b;

	public MyObject(int a, String b){
		this.a = a;
		this.b = b;
	}

	public String toString() {
		StringBuffer sb =  new StringBuffer() ;
		return sb.append(a).append(" ").append(b).toString() ;
	}
}
