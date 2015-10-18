// Identification
package ca.sils.hockeynitelive.Protocole;

// Importations
import java.io.Serializable;

/**
 * Created by Luc Saucier on 15-10-18.
 */
public class MessageError implements Serializable
{
    // Constantes
    private static final long serialVersionUID = -8965673978924406565L;  // Synchronisé avec serveur

    public final static     int     NULLPOINTEUR    = 1;
    public final static     int     METHODERROR     = 2;


    // Variables
    private                 int     code;
    private                 String  message;


    // Constructeur
    public MessageError()
    {
        message = "An error appear";
    }

    public MessageError(int p_code)
    {
        code = p_code;
        defineMessage(code);
    }

    // Accesseurs
    public int getCode()
    {
        return code;
    }
    public void setCode(int p_code)
    {
        code = p_code;
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
    private void defineMessage(int code)
    {
        // Sélection pour convertir Id en texte
        switch(code)
        {
            case NULLPOINTEUR:
                message="null pointer exception";
                break;
            case METHODERROR:
                message="this method doesn't exist";
                break;
            default:
                message="unknown error";
                break;
        }
    }

    @Override
    public String toString()
    {
        String texte = "Error [code=" + code + ", message=" + message + "]";
        return texte;
    }
}
