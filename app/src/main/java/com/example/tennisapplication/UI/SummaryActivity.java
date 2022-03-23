package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tennisapplication.R;
import com.example.tennisapplication.UI.AccountActivity;
import com.example.tennisapplication.UI.MenuActivity;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.viewModel.player.PlayerViewModel;
import com.google.android.material.button.MaterialButton;

public class SummaryActivity extends AppCompatActivity {

    private boolean isIndoor;
    private String curDate;
    private int hour;
    private int court;
    private PlayerEntity playerEntity;
    private PlayerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // get les informations précédemment entrées
        Intent intent = getIntent();
        isIndoor = intent.getBooleanExtra("isIndoor", true);
        curDate = intent.getStringExtra("curDate");
        hour = intent.getIntExtra("Hour", -1);
        court = intent.getIntExtra("court", -1);

        // Initialisation de la page
        // infos des pages précédentes
        TextView date = (TextView) findViewById(R.id.resultDate);
        date.setText(curDate);
        TextView courtnb = (TextView) findViewById(R.id.resultCourt);
        courtnb.setText(String.valueOf(court));
        TextView heureDebut = (TextView) findViewById(R.id.resultFrom);
        heureDebut.setText(String.valueOf(hour)+"h00");
        TextView heureFin = (TextView) findViewById(R.id.resultTo);
        heureFin.setText(String.valueOf(hour+1)+"h00");

        // set le textView du nom Prénom de l'utilisateur actuel
        TextView name = (TextView) findViewById(R.id.resultName);
        TextView surname = (TextView) findViewById(R.id.resultSurname);
        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME,0);
        String user = settings.getString(BaseActivity.PREFS_USER, null);
        PlayerViewModel.Factory factory = new PlayerViewModel.Factory(getApplication(), user);
        viewModel = ViewModelProviders.of(this, factory).get(PlayerViewModel.class);
        viewModel.getPlayer().observe(this, playerEntity -> {
            if (playerEntity != null) {
                this.playerEntity = playerEntity;
                if (playerEntity != null){
                    //Set Username on TextView
                    name.setText(playerEntity.getLastName().toUpperCase());
                    name.getText().toString();
                    surname.setText(playerEntity.getFirstName());
                    surname.getText().toString();
                }
            }
        });

        // afficher le court choisi
        ImageView court1 = (ImageView) findViewById(R.id.t1);
        ImageView court2 = (ImageView) findViewById(R.id.t2);
        ImageView court3 = (ImageView) findViewById(R.id.t3);
        ImageView court4 = (ImageView) findViewById(R.id.t4);
        ImageView court5 = (ImageView) findViewById(R.id.t5);
        ImageView court6 = (ImageView) findViewById(R.id.t6);
        ImageView court7 = (ImageView) findViewById(R.id.t7);
        court1.setVisibility(View.INVISIBLE);
        court2.setVisibility(View.INVISIBLE);
        court3.setVisibility(View.INVISIBLE);
        court4.setVisibility(View.INVISIBLE);
        court5.setVisibility(View.INVISIBLE);
        court6.setVisibility(View.INVISIBLE);
        court7.setVisibility(View.INVISIBLE);

        switch (court){
            case 1 :
                court1.setVisibility(View.VISIBLE);
                break;
            case 2 :
                court2.setVisibility(View.VISIBLE);
                break;
            case 3 :
                court3.setVisibility(View.VISIBLE);
                break;
            case 4 :
                court4.setVisibility(View.VISIBLE);
                break;
            case 5 :
                court5.setVisibility(View.VISIBLE);
                break;
            case 6 :
                court6.setVisibility(View.VISIBLE);
                break;
            case 7 :
                court7.setVisibility(View.VISIBLE);
                break;
        }

        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountActivity();
            }
        });

        // Create menu Button
        ImageView menuBtn = (ImageView) findViewById(R.id.menubutton);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuActivity();
            }
        });
    }

    private void openMenuActivity(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}