package ca.sils.hockeynitelive.Communication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.net.InetAddress;

import ca.sils.hockeynitelive.R;
import dataObject.ListMatchName;

/**
 * Created by cbongiorno on 20/10/2015.
 */
public class AutoUpdateService extends Service {

    public static final String TAG = "ComService";

    // intervalle entre les maj = 10 secondes
    static final int DELAY = 10000;

    // est-ce que le service
//  est en train de s’exécuter ?
    private boolean runFlag = false;

    // thread séparé qui effectue la MAJ
    private Updater updater;
    private LocalBroadcastManager broadcaster =  LocalBroadcastManager.getInstance(this);

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // créer le fil de MAJ
        // à la création du service
        this.updater = new Updater();

        Log.d(TAG, "onCreated");
    }

    static final public String COM_RESULT = "REQUEST_PROCESSED";

    static final public String COM_MESSAGE = "COM_MSG";

    public void sendResult(ListMatchName message) {
        Intent intent = new Intent(COM_RESULT);
        if(message != null)
            intent.putExtra(COM_MESSAGE, message);
        broadcaster.sendBroadcast(intent);
    }

    @Override
    public int onStartCommand(Intent intent,
                              int flags,
                              int startId) {
        super.onStartCommand(intent, flags, startId);

        // démarrer le fil de MAJ
        // au démarrage du service


        //10if(this.updater.isAlive()) this.updater.stop(); //TODO don't work
        this.runFlag = true;
        this.updater.start();

        Log.d(TAG, "onStarted");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // arrêter et détruire le fil de MAJ
        // à la destruction du service
        // mettre updater à null pour le GC

        this.runFlag = false;
        this.updater.interrupt();
        this.updater = null;

        Log.d(TAG, "onDestroyed");
    }

    /**
     * Thread that performs the actual update from the online service
     */
    private class Updater extends Thread {  // note : AsynchTask pour les threads UI

        public Updater() {
            super("UpdaterService-Updater");  // donner un nom au thread à des fins de debug
        }

        @Override
        public void run() {                 // méthode invoquée pour démarrer le fil
            AutoUpdateService comService = AutoUpdateService.this;  // réf. Sur le service
            while (comService.runFlag) {  // MAJ via les méthode onStartCOmmand et onDestroy
                Log.d(TAG, "Updater running");
                try {
                    /* Get DATA */
                    UDPHelper commUDPHelper = new UDPHelper();
                    InetAddress adr;

                    try {
                        adr = InetAddress.getByName(getApplication().getSharedPreferences(getResources().getString(R.string.FileShared),Context.MODE_PRIVATE).getString(getResources().getString(R.string.Serveur_adresse),"192.168.1.1"));
                    } catch (Exception e) {
                        sendResult(null);
                        return;
                    }

                    // Placer les paramètres de communications
                    commUDPHelper.setServeur(adr, 6780,6779);

                    // Lecture de la liste des parties
                    ListMatchName listeParties = commUDPHelper.getListMatchName();

                    sendResult(listeParties);

                    Log.d(TAG, "Updater ran");
                    Thread.sleep(DELAY);          // s’endormir entre chaque mise à jour
                } catch (InterruptedException e) {
                    // exception est déclenchée lorsqu’on signale interrupt()
                    comService.runFlag = false;
                }
            }
        }
    } // Updater
}
