package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.R;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.database.repository.ReservationRepository;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CourtSelectionActivity extends AppCompatActivity implements View.OnClickListener{

    private int courtSelected;
    private boolean[] courtReservedTab;
    private boolean isIndoor;
    private String curDate;
    private int hour;
    private List<ReservationEntity> reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_selection);

        reservations = new ArrayList<>();

        Intent intent = getIntent();
        isIndoor = intent.getBooleanExtra("isIndoor", true);
        curDate = intent.getStringExtra("curDate");
        hour = intent.getIntExtra("Hour", -1);

        // set le tableau qui va check si la réservation est pleine ou non
        courtReservedTab = new boolean[23];
        for(int i = 0 ; i < courtReservedTab.length ; i++){
            courtReservedTab[i] = false;
        }

        // initialiser tous les boutons de la page
        ImageView menuButton = (ImageView) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(this);
        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);
        toolbarButton.setOnClickListener(this);
        ImageView court1 = (ImageView) findViewById(R.id.t1);
        disableIfFull(court1);
        disableIfType(court1);
        court1.setOnClickListener(this);
        ImageView court2 = (ImageView) findViewById(R.id.t2);
        disableIfFull(court2);
        disableIfType(court2);
        court2.setOnClickListener(this);
        ImageView court3 = (ImageView) findViewById(R.id.t3);
        disableIfFull(court3);
        disableIfType(court3);
        court3.setOnClickListener(this);
        ImageView court4 = (ImageView) findViewById(R.id.t4);
        disableIfFull(court4);
        disableIfType(court4);
        court4.setOnClickListener(this);
        ImageView court5 = (ImageView) findViewById(R.id.t5);
        disableIfFull(court5);
        disableIfType(court5);
        court5.setOnClickListener(this);
        ImageView court6 = (ImageView) findViewById(R.id.t6);
        disableIfFull(court6);
        disableIfType(court6);
        court6.setOnClickListener(this);
        ImageView court7 = (ImageView) findViewById(R.id.t7);
        disableIfFull(court7);
        disableIfType(court7);
        court7.setOnClickListener(this);

    }

    private void openMenuActivity(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void openSummaryActivity(){
        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra("curDate", curDate);
        intent.putExtra("isIndoor", isIndoor);
        intent.putExtra("Hour", hour);
        intent.putExtra("court", courtSelected);
        startActivity(intent);
    }

    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    private void disableIfType(ImageView courtToCheck){
        int i = Integer.valueOf((String) courtToCheck.getTag());
        if (isIndoor){
            if (i == 6 || i == 7){
                courtToCheck.setVisibility(View.VISIBLE);
            }else {
                courtReservedTab[i] = true;
                courtToCheck.setVisibility(View.INVISIBLE);
            }
        }else{
            if (i == 6 || i == 7){
                courtReservedTab[i] = true;
                courtToCheck.setVisibility(View.INVISIBLE);
            }else {
                courtToCheck.setVisibility(View.VISIBLE);
            }
        }
    }

    private void disableIfFull(ImageView courtToCheck){

        int i = Integer.valueOf((String) courtToCheck.getTag());
        ReservationRepository repository = ((BaseApp) getApplication()).getReservationRepository();

        String h1 = String.valueOf(hour);

        repository.getNotAvailableCourts(h1, curDate, getApplication()).observe(CourtSelectionActivity.this, reservationEntities -> {

            if (reservationEntities != null)
            {
                reservations = reservationEntities;
                for (ReservationEntity r: reservations) {
                    if(i == r.getCourtNumber())
                    {
                        courtReservedTab[i] = true;
                        courtToCheck.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.toolbaraccountbutton){
            openAccountActivity();
        }
        else if(view.getId() == R.id.menubutton){
            openMenuActivity();
        }
        else {
            ImageView test = (ImageView) findViewById(view.getId());
            int i = Integer.parseInt((String) test.getTag());
            if(courtReservedTab[i] != true){
                courtSelected = i;
                openSummaryActivity();
            }
            else{
                Toast.makeText(this, "court réservé / non disponible", Toast.LENGTH_SHORT).show();
            }
        }
    }
}