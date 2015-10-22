package ca.sils.hockeynitelive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ca.sils.hockeynitelive.Communication.TCPHelper;
import ca.sils.hockeynitelive.Communication.UDPHelper;
import dataObject.Bet;
import dataObject.Match;

public class PariActivity extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "PariActivity";
    private int idMatch ;
    private String nameExterieur ;
    private String nameDomicile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Appel parent
        super.onCreate(savedInstanceState);

        // Placer contexte
        setContentView(R.layout.activity_pari);

        // Placer le nom de la partie
        Intent intent = getIntent();

        idMatch = intent.getExtras().getInt("idMatch");
        nameDomicile = intent.getExtras().getString("domicileName");
        nameExterieur = intent.getExtras().getString("exterieurName");

        //test que les valeurs sont cohérente :
        if( nameDomicile.equals("") || nameDomicile.equals("") || idMatch < 0){
            //Todo handle error
        }

        TextView tvPartie = (TextView) findViewById(R.id.tvPariPartie);
        String partie = intent.getStringExtra(PartieDetails.EXTRA_PARTIE);
        tvPartie.setText(partie);

        // Placer le nom des équipes
        RadioButton radioVisiteur = (RadioButton) findViewById(R.id.raPariVisiteur);
        String visiteurNom = intent.getStringExtra(PartieDetails.EXTRA_EQUIPE_VISITEUR_NOM);
        radioVisiteur.setText(visiteurNom);

        RadioButton radioLocale = (RadioButton) findViewById(R.id.raPariLocale);
        String localeNom = intent.getStringExtra(PartieDetails.EXTRA_EQUIPE_LOCALE_NOM);
        radioLocale.setText(localeNom);
    }

    @Override
    public void onClick(View v)
    {
        //Create the bet
        DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        RadioButton radio = (RadioButton) findViewById(R.id.raPariLocale);
        EditText editText = (EditText) findViewById(R.id.etMontant);
        float amount = Float.valueOf(editText.getText().toString());
        if(amount > 0){
            String name = nameExterieur;
            if( radio.isChecked() ){
                name = nameDomicile;

            }
            Bet b = new Bet(dateFormat.format(date) + "-" + Bet.getGeneratedId(),idMatch, name, amount);
            new MakeBet(b).start();
        }
        else{
            //Todo handle error with toast
        }

        // Fin de cette activité
        //finish();
    }

    private class MakeBet extends Thread {  // note : AsynchTask pour les threads UI

        Bet betToSend = null;
        public MakeBet(Bet bet) {
            super("MakeBet");  // donner un nom au thread à des fins de debug
            betToSend = bet;
        }

        @Override
        public void run() {
            Log.d(TAG, "MajeBet running");
            try {

                TCPHelper tcp = new TCPHelper();
                InetAddress adr;
                adr = InetAddress.getByName(getApplication().getSharedPreferences(getResources().getString(R.string.FileShared), Context.MODE_PRIVATE).getString(getResources().getString(R.string.Serveur_adresse), "192.168.1.1"));

                // Placer les paramètres de communications
                tcp.setServeur(adr, 1248);
                int result = tcp.sendBet(betToSend);
                if(result == 1)
                {
                    Log.i(TAG,"Succés pour l'objet b courant");
                }
                else if(result == 0)
                {
                    Log.i(TAG, "l'ajout à  echoué, car la période est plus grande que 2");
                }
                else
                {
                    Log.i(TAG, "l'ajout à  echoué, error de stream");
                }
                //Todo add return
                Log.d(TAG, "bet send");
            }
            catch (Exception e) {

                return;
            }
        }
    }
}
