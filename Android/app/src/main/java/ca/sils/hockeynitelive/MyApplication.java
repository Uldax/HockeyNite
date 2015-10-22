package ca.sils.hockeynitelive;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import dataObject.Bet;

/**
 * Created by cbongiorno on 20/10/2015.
 */
public class MyApplication extends Application {

    private SharedPreferences prefs;
    List<Bet> betHistory = new ArrayList<Bet>();

    public void onCreate() {
        super.onCreate();
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //set port and host;
        prefs.edit().putString("host", "127.0.0.1");
        prefs.edit().putString("port", "6789");
        prefs.edit().commit();
    }
    public void addBet(Bet b) {
        // On ajoute le bet courant Ã  notre liste de Bet
        betHistory.add(b);
    }
    public List<Bet> getBet(){
        return betHistory;
    }

}
