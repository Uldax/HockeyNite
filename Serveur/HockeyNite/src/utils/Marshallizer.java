package utils;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;

public class Marshallizer {

	public static Object unmarshall(byte[] request) {
		try {
			byte[] recvBuf = new byte[1000];
			ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
			ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
			Object o =  is.readObject();
			is.close();
			return o;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object unmarshall(DatagramPacket dp) {
		return unmarshall(dp.getData());
	}
	
	/**
	 * Serializes packet to be sent over udp to the manager tablet.
	 */
	static public byte[] marshallize(Serializable Object) {
	    try
	    {
	      ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
	      ObjectOutputStream oos = new ObjectOutputStream(baos);
	      oos.writeObject(Object);
	      oos.close();
	      // get the byte array of the object
	      byte[] obj= baos.toByteArray();
	      baos.close();
	      return obj;
	    }
	    catch(Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
}


