// Identification
package ca.sils.hockeynitelive.Utils;


// Importations
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;

import ca.sils.hockeynitelive.Protocole.Message;


/**
 * Created by Luc Saucier on 15-10-18.
 */
public class Marshallizer
{
    // Méthodes
    public static Object unmarshall(DatagramPacket p_request)
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(p_request.getData());
        ObjectInput in = null;

        try
        {
            in = new ObjectInputStream(bis);
            Message out = (Message) in.readObject();
            return out;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] marshallize(Serializable p_object)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(p_object);
            oos.close();

            byte[] obj = baos.toByteArray();
            return obj;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
