// Identification
package ca.sils.hockeynitelive.Protocole;


// Importations
import android.os.*;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.Arrays;

import ca.sils.hockeynitelive.dataObject.Match;


/**
 * Created by Luc Saucier on 15-10-18.
 */
public class Reply extends ca.sils.hockeynitelive.Protocole.Message
{
    // Constantes
    private static final long   serialVersionUID = 2622663736791338175L;  // Synchronisé avec serveur


    // Variables
    private Serializable        value;


    // Constructeur
    public Reply(InetAddress p_adresse, int p_port, int p_messageId)
    {
        type            = Message.REQUETE;
        destination     = p_adresse;
        destinationPort = p_port;
        messageId       = p_messageId;
    }


    // Accesseurs
    public Serializable getValue()
    {
        return value;
    }

    public void setValue(Serializable p_value)
    {
        if (p_value != null)
            value = p_value;
        else
            value = new MessageError(1);
    }


    // Méthode
    @Override
    public String toString()
    {
        String output = super.toString();
        if (value != null)
            if (value instanceof Match[])
                output += 	", value=" + Arrays.toString( (Match[])value) + "]";
            else
                output += 	", value=" + value.toString() + "]";

        return output;
    }
}
