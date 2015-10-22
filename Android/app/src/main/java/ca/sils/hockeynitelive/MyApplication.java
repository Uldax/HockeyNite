package ca.sils.hockeynitelive;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by cbongiorno on 20/10/2015.
 */
public class MyApplication extends Application {

    private SharedPreferences prefs;

    public void onCreate() {
        super.onCreate();
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //set port and host;
        prefs.edit().putString("host", "127.0.0.1");
        prefs.edit().putString("port", "6789");
    }
}
