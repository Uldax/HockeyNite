// Identification
package ca.sils.hockeynitelive.dataObject;


// Importations
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Luc Saucier on 15-10-18.
 */
public class Match implements Serializable {
    // Constantes
    private static final long serialVersionUID = -8080958380151755727L;
    private final int PERIODE_TIME = 20 * 60;
    private final int BREAK_TIME = 15 * 60;
    private final int MAX_TIME = 3 * PERIODE_TIME + 2 * BREAK_TIME;


    // Variables
    private List<Event> matchEvent = new ArrayList<Event>();

    private Team domicile = null;
    private int domicileScore = 0;

    private Team exterieur = null;
    private int exterieurScore = 0;

    //20 minute per periode with 15min of break
    private int periode;
    private int time = 0;
    //for easy the time management

    private int periodeStart = 0;
    private boolean pause = false;


    // Constructeur
    public Match(Team p_domicile, Team p_exterieur) {
        domicile = p_domicile;
        exterieur = p_exterieur;
        periode = 1;
    }


    // Accesseurs
    public int getTime() {
        return time;
    }

    public void setTime(int p_time) {
        time = p_time;
    }

    public Team getDomicile() {
        return domicile;
    }

    public void setDomicile(Team p_team) {
        domicile = p_team;
    }

    public Team getExterieur() {
        return exterieur;
    }

    public void setExterieur(Team p_team) {
        exterieur = p_team;
    }

    public List<Event> getMatchEvent()
    {
        return matchEvent;
    }

    public void setMatchEvent(List<Event> p_matchEvent)
    {
        matchEvent = p_matchEvent;
    }


    // Validations
    public Boolean EstPause()
    {
        return pause;
    }


    // Méthodes
    public synchronized void goalDomicile()
    {
        domicileScore++;
    }

    public synchronized void goalExterieur()
    {
        exterieurScore++;
    }

    public synchronized void addEvent(Event p_event)
    {
        p_event.setTime(time);
        matchEvent.add(p_event);
    }

    public int[] splitToTimes()
    {
        int hours = (int) time / 3600;
        int remainder = (int) time - hours * 3600;
        int mins = remainder / 60;
        remainder -= mins * 60;
        int secs = remainder;

        int[] ints = {hours, mins, secs};
        return ints;
    }

    private void handlePeriode()
    {
        switch(periode)
        {
            case 1:
                if (time >= PERIODE_TIME && pause == false)
                {
                    pause = true;
                    matchEvent.add(new Event(time, "First intermission"));
                }
                else if ((time >= PERIODE_TIME + BREAK_TIME) && pause == true)
                {
                    pause = false;
                    matchEvent.add(new Event(time, "Second period"));
                    periodeStart = time;
                    periode = 2;
                }
                break;
            case 2:
                if ((time >= periodeStart + PERIODE_TIME) && pause == false)
                {
                    pause = true;
                    matchEvent.add(new Event(time, "Second intermission"));
                }
                else if ((time >= periodeStart + PERIODE_TIME + BREAK_TIME) && pause == true)
                {
                    pause = false;
                    matchEvent.add(new Event(time, "Third period"));
                    periodeStart = time;
                    periode = 3;
                }
                break;
            case 3:
                if ((time >= periodeStart + PERIODE_TIME) && pause == false)
                {
                    pause = true;
                    matchEvent.add(new Event(time, "End of game"));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public String toString()
    {
        String texte = "Match [time=" + time + ", domicile=" + domicile + ", domicileScore=" + domicileScore + ", exterieur="
                + exterieur + ", exterieurScore=" + exterieurScore + ", matchEvent=" ;
        for (Event e : matchEvent)
            texte += e.toString() + "\t";
        texte += ", periode=" + periode + "]";

        return texte;
    }



}
