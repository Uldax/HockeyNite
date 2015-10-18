// Identification
package ca.sils.hockeynitelive.dataObject;


// Importations
import java.io.Serializable;
import java.util.HashMap;


/**
 * Created by Luc Saucier on 15-10-18.
 */
public class ListMatchName implements Serializable
{
    // Constantes
    private static final long serialVersionUID = -8663297309570067180L;


    // Variables
    private HashMap<Integer, String> matchName = new HashMap<Integer, String>();


    // Constructeur
    public ListMatchName(Match[] p_allMatch)
    {
        for (int i = 0; i < p_allMatch.length; i++)
        {
            if (p_allMatch[i] != null)
            {
                Match currentMatch = p_allMatch[i];
                int[] currentTime = currentMatch.splitToTimes();
                String matchSentence = currentMatch.getExterieur().getName() + " VS " +
                                        currentMatch.getDomicile().getName() +
                                        " Timer: ";
                for (int j = 0; j < 3; j++)
                {
                    matchSentence += currentTime[j];
                    if (j < 2)
                        matchSentence += ":";
                }

                matchName.put(i, matchSentence);
            }
        }
    }


    // Accesseurs
    public HashMap<Integer, String> getMatchName()
    {
        return matchName;
    }


    // Méthodes
    public String toString()
    {
        return "ListMatchName [matchName=" + matchName + "]";
    }
}
