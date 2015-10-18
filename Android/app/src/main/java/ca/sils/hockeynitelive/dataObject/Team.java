// Identification
package ca.sils.hockeynitelive.dataObject;


// Importations
import java.io.Serializable;


/**
 * Created by Luc Saucier on 15-10-18.
 */
public class Team implements Serializable
{
    // Constantes
    private static final long serialVersionUID = 8872344033691745328L;


    // Variables
    private String name;


    // Constructeur
    public Team(String p_name)
    {
        name = p_name;
    }


    // Accesseurs
    public String getName()
    {
        return name;
    }

    public void setName(String p_name)
    {
        this.name = p_name;
    }


    // Méthodes
    @Override
    public String toString()
    {
        String texte = "Team [name=" + name + "]";
        return texte;
    }
}
