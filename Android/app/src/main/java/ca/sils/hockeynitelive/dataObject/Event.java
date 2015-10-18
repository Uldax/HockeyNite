// Identification
package ca.sils.hockeynitelive.dataObject;


// Importations
import java.io.Serializable;


/**
 * Created by Luc Saucier on 15-10-18.
 */
public class Event implements Serializable
{
    // Constantes
    private static final    long    serialVersionUID = -5590704311853640594L;


    // Variables
    private                 int     time;
    private                 String  message;


    // Constructeur
    public Event(String p_message)
    {
        time    = 0;
        message = p_message;
    }

    public Event(int p_time, String p_message)
    {
        time    = p_time;
        message = p_message;
    }


    // Accesseurs
    public int getTime()
    {
        return time;
    }

    public void setTime(int p_time)
    {
        time = p_time;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String p_message)
    {
        message = p_message;
    }


    // Méthodes
    @Override
    public String toString()
    {
        String texte = "Event [time=" + time + ", message=" + message + "]";
        return texte;
    }
}
