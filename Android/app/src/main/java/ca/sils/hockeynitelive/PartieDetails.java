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

        if(currentMatch != null){
            //When match id done :
            progressBar.setVisibility(View.INVISIBLE);
            listEvent = (ListView) findViewById(R.id.listEvent);
            adapter = new EventAdapter(this, R.layout.adapter_match, currentMatch);
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


        /** Set Data **/
        TextView teamNameLocal = (TextView) findViewById(R.id.tvPdEquipeLocaleNom);
        TextView teamNameVisitor = (TextView) findViewById(R.id.tvPdEquipeVisiteurNom);
        TextView scoreLocal = (TextView) findViewById(R.id.tvPdEquipeLocalePoints);
        TextView scoreVisitor = (TextView) findViewById(R.id.tvPdEquipeVisiteurPoints);
        TextView Periode = (TextView) findViewById(R.id.tvPdPeriode);
        TextView Timer = (TextView) findViewById(R.id.tvPdTempsRestant);




    }

    @Override
    protected void onResume()
    {
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
