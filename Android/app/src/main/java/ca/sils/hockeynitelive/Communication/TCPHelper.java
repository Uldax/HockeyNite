package ca.sils.hockeynitelive.Communication;


import android.nfc.Tag;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import dataObject.Bet;

/**
 * Created by pcontat on 22/10/2015.
 */

public class TCPHelper {

    private int serveurPort = 1248;
    private static final String TAG = "PariActivity";
    private InetAddress adresse = null;



    // Méthodes
    public void setServeur(InetAddress adresse, int portServeur)
    {
        this.adresse = adresse;
        this.serveurPort = portServeur;
    }

    public int sendBet(Bet bet) {
        try {
            Socket sClient = new Socket(adresse, serveurPort);
            InputStream is = sClient.getInputStream();
            OutputStream os = sClient.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(bet);

            oos.flush();

            int result = is.read();

            sClient.close();

            return result;

        } catch (IOException ex) {
           Log.e(TAG, ex.toString());
        }

        return -1;
    }

}
