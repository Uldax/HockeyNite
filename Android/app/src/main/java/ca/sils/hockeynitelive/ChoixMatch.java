package ca.sils.hockeynitelive;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.InetAddress;

import ca.sils.hockeynitelive.Communication.Communication;
import ca.sils.hockeynitelive.Communication.Udp;
import ca.sils.hockeynitelive.adapter.MatchAdapter;
import dataObject.ListMatchName;

public class ChoixMatch extends AppCompatActivity {

    private String adresseIP = null;
    private MatchAdapter adapter;
    private GridView gridView;
    private ProgressBar progressBar;
    private BroadcastReceiver receiver;
    private Intent comService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Appel parent
        super.onCreate(savedInstanceState);
        // Placer contenu
        setContentView(R.layout.activity_choix_match);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ListMatchName listeParties = (ListMatchName) intent.getSerializableExtra(Communication.COM_MESSAGE);
                updateData(listeParties);
            }
        };

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PartieDetails.class);
                intent.putExtra("idMatch", adapter.getItemId(position));
                startActivity(intent);

            }
        });

        Button button = (Button) findViewById(R.id.buTrouverPartie);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                EditText etAdresseIP = (EditText) findViewById(R.id.etAdresseIP);
                adresseIP = etAdresseIP.getText().toString();

                if (adresseIP.isEmpty()) {
                    // Initialisation à vide
                    adresseIP = null;

                    // Message Toast
                    Toast.makeText(ChoixMatch.this, "HokeyNiteLive - Adresse vide", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences.Editor editor = getApplication().getSharedPreferences(getResources().getString(R.string.FileShared), Context.MODE_PRIVATE).edit();
                editor.putString(getResources().getString(R.string.Serveur_adresse),adresseIP);
                editor.commit();
                comService = new Intent(getApplicationContext(), Communication.class);
                startService(comService);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Communication.COM_RESULT)
        );
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    private void getList() {
        // Lecture des parties
        Udp commUdp = new Udp();
        InetAddress adr;

        try {
            adr = InetAddress.getByName(getApplication().getSharedPreferences(getResources().getString(R.string.FileShared),Context.MODE_PRIVATE).getString(getResources().getString(R.string.Serveur_adresse),"192.168.1.1"));
        } catch (Exception e) {
            Toast.makeText(ChoixMatch.this, "Adresse invalide", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        // Placer les paramètres de communications
        commUdp.setServeur(adr, 6780, 6779);

        // Lecture de la liste des parties
        ListMatchName listeParties = commUdp.getListMatchName();

        this.updateData(listeParties);
    }


    public void updateData(ListMatchName listeParties){
        progressBar.setVisibility(View.INVISIBLE);
        if (listeParties == null) {
            Toast.makeText(ChoixMatch.this, "Liste non-disponible", Toast.LENGTH_SHORT).show();
            return;
        }
        adapter = new MatchAdapter(this, R.layout.adapter_match, listeParties.getList());
        gridView.setAdapter(adapter);
    }

}
