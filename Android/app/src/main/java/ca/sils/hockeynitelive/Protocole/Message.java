// Identification
package ca.sils.hockeynitelive.Protocole;

// Importations
import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by Luc Saucier on 15-10-18.
 */
public class Message implements Serializable
{
    // Constantes
    private static final    long            serialVersionUID = 1L;  // Synchronisé avec serveur

    public final static     int             REQUETE = 0;
    public final static     int             REPONSE = 1;


    // Variables
    protected               int             type;
    protected               int             messageId;

    protected               InetAddress     destination;
    protected               int             destinationPort;


    // Accesseurs
    public int getType()
    {
        return type;
    }
    public void setType(int p_type)
    {
        type = p_type;
    }

    public int getMessageId()
    {
        return messageId;
    }
    public void setMessageId(int p_messageId)
    {
        this.messageId = p_messageId;
    }

    public InetAddress getDestination()
    {
        return destination;
    }
    public void setDestination(InetAddress p_destination)
    {
        destination = p_destination;
    }

    public int getDestinationPort()
    {
        return destinationPort;
    }
    public void setDestinationPort(int p_destinationPort)
    {
        destinationPort = p_destinationPort;
    }


    // Validations
    public boolean estRequete()
    {
        return (type == REQUETE);
    }
    public boolean estReponse()
    {
        return (type == REPONSE);
    }


    // Méthode
    @Override
    public String toString()
    {
        String output = "Message [type=" + type + ", messageId=" + messageId +
                        ", destination=" + destination + ", destinationPort=" + destinationPort;
        return output;
    }


}
