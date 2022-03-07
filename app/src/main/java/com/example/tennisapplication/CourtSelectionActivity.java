package com.example.tennisapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class CourtSelectionActivity extends AppCompatActivity implements View.OnClickListener{

    private int courtSelected;
    private boolean[] courtReservedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_selection);

        // set le tableau qui va check si la réservation est pleine ou non
        courtReservedTab = new boolean[23];
        for(int i = 0 ; i < courtReservedTab.length ; i++){
            courtReservedTab[i] = false;
        }

        // initialiser tous les boutons de la page
        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);
        toolbarButton.setOnClickListener(this);
        ImageView court1 = (ImageView) findViewById(R.id.t1);
        disableIfFull(court1);
        court1.setOnClickListener(this);
        ImageView court2 = (ImageView) findViewById(R.id.t2);
        disableIfFull(court2);
        court2.setOnClickListener(this);
        ImageView court3 = (ImageView) findViewById(R.id.t3);
        disableIfFull(court3);
        court3.setOnClickListener(this);
        ImageView court4 = (ImageView) findViewById(R.id.t4);
        disableIfFull(court4);
        court4.setOnClickListener(this);
        ImageView court5 = (ImageView) findViewById(R.id.t5);
        disableIfFull(court5);
        court5.setOnClickListener(this);
        ImageView court6 = (ImageView) findViewById(R.id.t6);
        disableIfFull(court6);
        court6.setOnClickListener(this);
        ImageView court7 = (ImageView) findViewById(R.id.t7);
        disableIfFull(court7);
        court7.setOnClickListener(this);

        court3.setVisibility(View.INVISIBLE);
    }

    private void openSummaryActivity(){
        Intent intent = new Intent(this, SummaryActivity.class);
        startActivity(intent);
    }

    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    private void disableIfFull(ImageView courtToCheck){
        int i = Integer.valueOf((String) courtToCheck.getTag());

        // remplacer 1==0 par une fonction qui retourne true si il n'y a plus de court disponible a la date/heure choisie
        if(1==0){
            courtReservedTab[i] = true;
            courtToCheck.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.toolbaraccountbutton){
            openAccountActivity();
        }
        else {
            ImageView test = (ImageView) findViewById(view.getId());
            int i = Integer.parseInt((String) test.getTag());
            if(courtReservedTab[i] != true){
                courtSelected = i;
                openSummaryActivity();
            }
            else{
                Toast.makeText(this, "sorry c'est complet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int getSelectedCourt(){
        return courtSelected;
    }
}