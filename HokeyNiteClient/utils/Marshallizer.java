package utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;



public class Marshallizer {

	public static Object unmarshall(DatagramPacket request) {
		
		ByteArrayInputStream bis = new ByteArrayInputStream(request.getData());
		ObjectInput in = null;
		try {
		in = new ObjectInputStream(bis);
		Message o =  (Message) in.readObject();
		return o;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
		
		/*
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
		return null; */
	}
	
	
	
	/**
	 * Serializes packet to be sent over udp to the manager tablet.
	 */
	static public byte[] marshallize(Serializable Object) {
	    try
	    {
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      ObjectOutputStream oos = new ObjectOutputStream(baos);
	      oos.writeObject(Object);
	      oos.close();
	      // get the byte array of the object
	      byte[] obj= baos.toByteArray();
	      //baos.close();
	      return obj;
	    }
	    catch(Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
}


