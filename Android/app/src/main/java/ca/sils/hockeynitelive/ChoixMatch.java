package ca.sils.hockeynitelive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

import ca.sils.hockeynitelive.Communication.Udp;
import ca.sils.hockeynitelive.adapter.MatchAdapter;
import dataObject.ListMatchName;

public class ChoixMatch extends AppCompatActivity
{
    // Variables
    public final static String EXTRA_PARTIE = "ca.sils.hockeynitelive.partie";

    private String adresseIP = null;
    private DatagramSocket dsClientPartie = null;
    private MatchAdapter adapter;
    private GridView gridView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Appel parent
        super.onCreate(savedInstanceState);
        // Placer contenu
        setContentView(R.layout.activity_choix_match);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PartieDetails.class);
                intent.putExtra(EXTRA_PARTIE, position);
                startActivity(intent);

            }
        });

        Button button = (Button) findViewById(R.id.buTrouverPartie);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
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
                getList(adresseIP);
            }
        });

    }

    private void getList(String adresseIP){
        // Lecture des parties
        Udp commUdp = new Udp();
        InetAddress adr = null;

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

        gridView = (GridView) findViewById(R.id.grid_view);

        // Instance of ImageAdapter Class
        adapter = new MatchAdapter(this, R.layout.adapter_match, listeParties.getList());
        gridView.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
