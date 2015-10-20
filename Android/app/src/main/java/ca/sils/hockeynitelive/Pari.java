package ca.sils.hockeynitelive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Pari extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Appel parent
        super.onCreate(savedInstanceState);

        // Placer contexte
        setContentView(R.layout.activity_pari);

        // Placer le nom de la partie
        Intent intent = getIntent();

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
        // Message via Toast
        Button button = (Button) findViewById(R.id.buPariEnvoi);
        EditText editText = (EditText) findViewById(R.id.etMontant);
        Toast.makeText(Pari.this, "HockeyNiteLive - " + button.getText() + " - " + editText.getText(), Toast.LENGTH_SHORT).show();

        // Fin de cette activité
        finish();
    }
}
