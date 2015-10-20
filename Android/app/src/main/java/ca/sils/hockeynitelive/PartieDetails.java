package ca.sils.hockeynitelive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PartieDetails extends AppCompatActivity implements View.OnClickListener
{
    // Variables
    public final static String EXTRA_PARTIE = "ca.sils.hockeynitelive.partie";
    public final static String EXTRA_EQUIPE_VISITEUR_NOM = "ca.sils.hockeynitelive.equipevisiteurnom";
    public final static String EXTRA_EQUIPE_LOCALE_NOM = "ca.sils.hockeynitelive.equipelocalenom";

    boolean avantPlan = false;

    int idMatch = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Appel parent
        super.onCreate(savedInstanceState);

        // Placer le contexte
        setContentView(R.layout.activity_partie_details);

        // Placer le nom de la partie
        TextView tvPartie = (TextView) findViewById(R.id.tvPdPartieNom);
        Intent intent = getIntent();
        idMatch = intent.getExtras().getInt("idMatch");
        tvPartie.setText(String.valueOf(idMatch));
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

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buPdPari:
                // Passer aux paris
                Intent intent = new Intent(this, Pari.class);
                TextView tvPartie = (TextView) findViewById(R.id.tvPdPartieNom);
                TextView tvVisiteurNom = (TextView) findViewById(R.id.tvPdEquipeVisiteurNom);
                TextView tvLocaleNom = (TextView) findViewById(R.id.tvPdEquipeLocaleNom);
                intent.putExtra(EXTRA_PARTIE, tvPartie.getText());
                intent.putExtra(EXTRA_EQUIPE_VISITEUR_NOM, tvVisiteurNom.getText());
                intent.putExtra(EXTRA_EQUIPE_LOCALE_NOM, tvLocaleNom.getText());
                startActivity(intent);
                break;
            case R.id.buPdRafraichir:
                // Afficher un message de rafraîchissement via Toast
                Button button = (Button) findViewById(R.id.buPdRafraichir);
                Toast.makeText(PartieDetails.this, "HockeyNiteLife - " + button.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
