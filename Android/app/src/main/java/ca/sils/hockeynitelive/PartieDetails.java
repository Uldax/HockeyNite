package ca.sils.hockeynitelive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ca.sils.hockeynitelive.adapter.EventAdapter;
import dataObject.Event;
import dataObject.Match;
import dataObject.Team;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Appel parent
        super.onCreate(savedInstanceState);

        // Placer le contexte
        setContentView(R.layout.activity_partie_details);

        Intent intent = getIntent();
        idMatch = intent.getExtras().getInt("idMatch");


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //TODO Get match detail with ID

        /** Fausse données **/
        currentMatch = new Match(25,new Team("tha"),new Team("BBBBBb"));
        currentMatch.goalDomicile();
        currentMatch.addEvent(new Event(Event.GOAL, "Pas but de A !!!"));
        currentMatch.addEvent(new Event(Event.PENALITY,"B pas content"));
        currentMatch.goalExterieur();
        currentMatch.addEvent(new Event(Event.GOAL,"La revenche de B"));
        currentMatch.setTime(120);



        if(currentMatch != null){
            /** Set Data **/

            //When match id done :
            progressBar.setVisibility(View.INVISIBLE);

            TextView teamNameLocal = (TextView) findViewById(R.id.tvPdEquipeLocaleNom);
            TextView teamNameVisitor = (TextView) findViewById(R.id.tvPdEquipeVisiteurNom);
            TextView scoreLocal = (TextView) findViewById(R.id.scoreA);
            TextView scoreVisitor = (TextView) findViewById(R.id.scoreB);
            TextView periode = (TextView) findViewById(R.id.tvPdPeriode);
            TextView timer = (TextView) findViewById(R.id.tvPdTempsRestant);

            teamNameLocal.setText(currentMatch.getDomicile().getName());
            teamNameVisitor.setText(currentMatch.getExterieur().getName());
            scoreLocal.setText(currentMatch.getDomicileScore()); // <---------------- FUCK !
            scoreVisitor.setText(currentMatch.getExterieurScore());
            periode.setText(String.valueOf(currentMatch.getPeriode()));
            timer.setText(getResources().getString(R.string.PdPeriode) + " : " +currentMatch.getStringTime());

            listEvent = (ListView) findViewById(R.id.listEvent);
            adapter = new EventAdapter(this, R.layout.adapter_match_event, currentMatch);
            listEvent.setAdapter(adapter);
        }


        Button buPdPari = (Button) findViewById(R.id.buPdPari);
        buPdPari.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Pari.class);
                TextView tvPartie = (TextView) findViewById(R.id.tvPdPartieNom);
                TextView tvVisiteurNom = (TextView) findViewById(R.id.tvPdEquipeVisiteurNom);
                TextView tvLocaleNom = (TextView) findViewById(R.id.tvPdEquipeLocaleNom);
                intent.putExtra(EXTRA_PARTIE, tvPartie.getText());
                intent.putExtra(EXTRA_EQUIPE_VISITEUR_NOM, tvVisiteurNom.getText());
                intent.putExtra(EXTRA_EQUIPE_LOCALE_NOM, tvLocaleNom.getText());
                startActivity(intent);
            }
        });

        Button buPdRafraichir = (Button) findViewById(R.id.buPdRafraichir);
        buPdRafraichir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO : refresh data
            }
        });







    }

    @Override
    protected void onResume() {
        // Appel parent
        super.onResume();

        // Activité en avant-plan
        avantPlan = true;

        // Affichage du statut d'avant-plan par Toast
        Toast.makeText(PartieDetails.this, "HockeyNiteLive - onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause()
    {
        // Appel parent
        super.onPause();

        // Activité en arrière-plan
        avantPlan = false;

        // Affichage du statut d'avant-plan par Toast
        Toast.makeText(PartieDetails.this, "HockeyNiteLive - onPause", Toast.LENGTH_SHORT).show();
    }

}
