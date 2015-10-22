package ca.sils.hockeynitelive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ca.sils.hockeynitelive.Communication.AutoUpdateService;
import ca.sils.hockeynitelive.Communication.detailsUpdateService;
import ca.sils.hockeynitelive.adapter.EventAdapter;
import dataObject.Match;

public class PartieDetails extends AppCompatActivity
{
    // Variables
    public final static String EXTRA_PARTIE = "ca.sils.hockeynitelive.partie";
    public final static String EXTRA_EQUIPE_VISITEUR_NOM = "ca.sils.hockeynitelive.equipevisiteurnom";
    public final static String EXTRA_EQUIPE_LOCALE_NOM = "ca.sils.hockeynitelive.equipelocalenom";

    boolean avantPlan = false;
    private Match currentMatch = null;
    private ListView listEvent = null;

    private int idMatch = 0;
    private EventAdapter adapter;
    private ProgressBar progressBar;

    private BroadcastReceiver receiver;
    private Intent detService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Appel parent
        super.onCreate(savedInstanceState);

        // Placer le contexte
        setContentView(R.layout.activity_partie_details);

        Intent intent = getIntent();
        idMatch =(int) intent.getExtras().getLong("idMatch");
        Log.i("PartieDetails onCreate", "getExtras " + String.valueOf(idMatch));

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Match currentMatch = (Match) intent.getSerializableExtra(detailsUpdateService.DET_MESSAGE);
                updateData(currentMatch);
            }
        };

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        //Bouton pari
        Button buPdPari = (Button) findViewById(R.id.buPdPari);
        buPdPari.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PariActivity.class);
                intent.putExtra("idMatch", idMatch);
                intent.putExtra("domicileName", currentMatch.getDomicile().getName());
                intent.putExtra("exterieurName", currentMatch.getExterieur().getName());
                startActivity(intent);
            }
        });

        //button for force refresh
        Button buPdRafraichir = (Button) findViewById(R.id.buPdRafraichir);
        buPdRafraichir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                detService = new Intent(getApplicationContext(), detailsUpdateService.class);
                detService.putExtra(detailsUpdateService.ID_MATCH,idMatch);
                startService(detService);
            }
        });

        detService = new Intent(getApplicationContext(), detailsUpdateService.class);
        detService.putExtra(detailsUpdateService.ID_MATCH, idMatch);
        startService(detService);
    }

    @Override
    protected void onResume() {
        super.onResume();
        avantPlan = true;
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(AutoUpdateService.COM_RESULT)
        );
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        avantPlan = false;
        stopService(detService);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void updateData(Match currentMatch){
        progressBar.setVisibility(View.INVISIBLE);
        if(currentMatch != null){

            this.currentMatch = currentMatch;

            TextView teamNameLocal = (TextView) findViewById(R.id.tvPdEquipeLocaleNom);
            TextView teamNameVisitor = (TextView) findViewById(R.id.tvPdEquipeVisiteurNom);
            TextView scoreLocal = (TextView) findViewById(R.id.scoreA);
            TextView scoreVisitor = (TextView) findViewById(R.id.scoreB);
            TextView periode = (TextView) findViewById(R.id.tvPdPeriode);
            TextView timer = (TextView) findViewById(R.id.tvPdTempsRestant);

            teamNameLocal.setText(currentMatch.getDomicile().getName());
            teamNameVisitor.setText(currentMatch.getExterieur().getName());
            scoreLocal.setText(String.valueOf(currentMatch.getDomicileScore()));
            scoreVisitor.setText(String.valueOf(currentMatch.getExterieurScore()));
            periode.setText(getResources().getString(R.string.PdPeriode) + " : " +String.valueOf(currentMatch.getPeriode()));
            timer.setText(currentMatch.getStringTime());

            listEvent = (ListView) findViewById(R.id.listEvent);
            adapter = new EventAdapter(this, R.layout.adapter_match_event, currentMatch);
            listEvent.setAdapter(adapter);
        }else{
            Toast.makeText(this,"Erreur réseau",Toast.LENGTH_LONG).show();
        }
    }

}
