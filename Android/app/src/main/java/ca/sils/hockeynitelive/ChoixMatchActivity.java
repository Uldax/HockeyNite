package ca.sils.hockeynitelive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import ca.sils.hockeynitelive.Communication.AutoUpdateService;
import ca.sils.hockeynitelive.adapter.MatchAdapter;
import dataObject.ListMatchName;

public class ChoixMatchActivity extends AppCompatActivity {

    private String adresseIP = null;
    private MatchAdapter adapter;
    private GridView gridView;
    private ProgressBar progressBar;
    private BroadcastReceiver receiver;
    private Intent comService = null;
    private ListMatchName listMatch = null;
    private final String STATE_LISTMATCH = "listmatch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Appel parent
        super.onCreate(savedInstanceState);
        // Placer contenu
        setContentView(R.layout.activity_choix_match);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ListMatchName listeParties = (ListMatchName) intent.getSerializableExtra(AutoUpdateService.COM_MESSAGE);
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
                Log.i("gridView onClick",String.valueOf(adapter.getItemId(position)));
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
                    Toast.makeText(ChoixMatchActivity.this, "HokeyNiteLive - Adresse vide", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(comService != null){
                    stopService(comService);
                    comService = null;
                }
                SharedPreferences.Editor editor = getApplication().getSharedPreferences(getResources().getString(R.string.FileShared), Context.MODE_PRIVATE).edit();
                editor.putString(getResources().getString(R.string.Serveur_adresse),adresseIP);
                editor.commit();
                comService = new Intent(getApplicationContext(), AutoUpdateService.class);
                startService(comService);
            }
        });

        if (savedInstanceState != null) {
            this.listMatch = (ListMatchName) savedInstanceState.getSerializable(STATE_LISTMATCH);
            this.updateData(this.listMatch);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(AutoUpdateService.COM_RESULT)
        );
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(STATE_LISTMATCH, this.listMatch);;
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        if(this.comService!= null){
            stopService(this.comService);
            this.comService = null;
        }
        super.onStop();
    }

    public void updateData(ListMatchName listMatch){
        progressBar.setVisibility(View.INVISIBLE);
        if (listMatch == null) {
            Toast.makeText(ChoixMatchActivity.this, "Liste non-disponible", Toast.LENGTH_SHORT).show();
            return;
        }
        this.listMatch = listMatch;
        adapter = new MatchAdapter(this, R.layout.adapter_match, listMatch.getList());
        gridView.setAdapter(adapter);
    }

}
