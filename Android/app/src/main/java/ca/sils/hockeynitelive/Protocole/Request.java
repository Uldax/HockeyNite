// Identification
package ca.sils.hockeynitelive.Protocole;


// Importations
import java.net.InetAddress;


/**
 * Created by Luc Saucier on 15-10-18.
 */
public class Request extends Message
{
    // Énumération
    public enum requestTypes { list, details };


    // Constantes
    private static final    long            serialVersionUID    = 361933411720337979L;  // Synchro
    private static final    int             MAX_NUM             = 50000;


    // Variables
    private                 Object[]        argument;
    private                 requestTypes    requestType         = null;
    private static          int             requestId           = 0;


    // Constructeur
    public Request()
    {
    }


    // Accesseurs
    public Object[] getArgument()
    {
        return argument;
    }
    public void setArgument(Object[] p_argument)
    {
        argument = p_argument;
    }

    public requestTypes getRequestType()
    {
        return requestType;
    }
    public void setRequestType(requestTypes p_requestType)
    {
        requestType = p_requestType;
    }


    // Méthodes
    public static synchronized Request craftGetMatchList(InetAddress p_adresse, int p_port)
    {
        Request request = new Request();
        request.setRequestType(requestTypes.list);
        request.setMessageId(requestId);
        request.setDestination(p_adresse);
        request.setDestinationPort(p_port);
        incrementRequestId();
        return request;
    }

    public static synchronized Request craftGetMatchDetail(InetAddress p_adresse, int p_port, int p_matchId)
    {
        Request request = new Request();
        request.setRequestType(requestTypes.details);
        Object[] arg = {p_matchId};
        request.setArgument(arg);
        request.setMessageId(requestId);
        request.setDestination(p_adresse);
        request.setDestinationPort(p_port);
        incrementRequestId();
        return request;
    }

    private static void incrementRequestId()
    {
        if (requestId == MAX_NUM)
            requestId = 0;
        requestId++;
    }
}
