package ca.sils.hockeynitelive;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

import ca.sils.hockeynitelive.Communication.Udp;
import ca.sils.hockeynitelive.dataObject.ListMatchName;
import ca.sils.hockeynitelive.dataObject.Match;

public class ChoixMatch extends AppCompatActivity implements View.OnClickListener
{
    // Variables
    public final static String EXTRA_PARTIE = "ca.sils.hockeynitelive.partie";

    private String adresseIP = null;
    private DatagramSocket dsClientPartie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Appel parent
        super.onCreate(savedInstanceState);

        // Placer contenu
        setContentView(R.layout.activity_choix_match);
    }

    @Override
    public void onClick(View v)
    {
        // Affichage pour tests
        Button button = (Button) findViewById(v.getId());

        // Sortir si bouton pour trouver les parties
        if (button.getId() == R.id.buTrouverPartie)
        {
            // Placer l'adresse en mémoire
            EditText etAdresseIP = (EditText) findViewById(R.id.etAdresseIP);
            adresseIP = etAdresseIP.getText().toString();

            if (adresseIP.isEmpty())
            {
                // Initialisation à vide
                adresseIP = null;

                // Message Toast
                Toast.makeText(ChoixMatch.this, "HokeyNiteLive - Adresse vide", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lecture des parties
            Udp commUdp = new Udp();
            InetAddress adr = null;
            ;
            try
            {
                adr = InetAddress.getByName(adresseIP);
            }
            catch (Exception e)
            {
                Toast.makeText(ChoixMatch.this, "Adresse invalide", Toast.LENGTH_SHORT).show();
                return;
            }

            // Placer les paramètres de communications
            commUdp.setServeur(adr, 6780, 6779);

            // Lecture de la liste des parties
            ListMatchName listeParties = commUdp.getListMatchName();
            if (listeParties == null)
            {
                Toast.makeText(ChoixMatch.this, "Liste non-disponible", Toast.LENGTH_SHORT).show();
                return;
            }

            HashMap<Integer, String> liste = listeParties.getMatchName();


            // Disponibilité des parties - Simulation
            Button button1 = (Button) findViewById(R.id.buPartie1);
            Button button2 = (Button) findViewById(R.id.buPartie2);
            Button button3 = (Button) findViewById(R.id.buPartie3);
            Button button4 = (Button) findViewById(R.id.buPartie4);
            Button button5 = (Button) findViewById(R.id.buPartie5);
            Button button6 = (Button) findViewById(R.id.buPartie6);
            Button button7 = (Button) findViewById(R.id.buPartie7);
            Button button8 = (Button) findViewById(R.id.buPartie8);
            Button button9 = (Button) findViewById(R.id.buPartie9);
            Button button10 = (Button) findViewById(R.id.buPartie10);

            button1.setEnabled(true);
            button2.setEnabled(true);
            button3.setEnabled(true);
            button4.setEnabled(true);
            button5.setEnabled(true);
            button6.setEnabled(true);
            button7.setEnabled(true);
            button8.setEnabled(true);
            button9.setEnabled(true);
            button10.setEnabled(true);

            // Affichage Toast
            Toast.makeText(ChoixMatch.this, "HockeyNite Live - " + button.getText() + " - " + adresseIP, Toast.LENGTH_SHORT).show();

            // Fin
            return;
        }

        // Appel de la deuxième activité
        Intent intent = new Intent(this, PartieDetails.class);
        intent.putExtra(EXTRA_PARTIE, button.getText());
        startActivity(intent);
    }
}
