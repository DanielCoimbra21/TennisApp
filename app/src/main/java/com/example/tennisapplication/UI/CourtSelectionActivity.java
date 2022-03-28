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

    /**
     * Initialisation method of the Court Selection Activity
     *
     * @param savedInstanceState with the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_selection);

        // Get the previous data
        Intent intent = getIntent();
        isIndoor = intent.getBooleanExtra("isIndoor", true);
        curDate = intent.getStringExtra("curDate");
        hour = intent.getIntExtra("Hour", -1);

        // set le tableau qui va check si la réservation est pleine ou non
        reservations = new ArrayList<>();
        courtReservedTab = new boolean[23];
        for(int i = 0 ; i < courtReservedTab.length ; i++){
            courtReservedTab[i] = false;
        }

        // initialiser tous les boutons de la page et les ajouter à un tableau afin de plus facilement ajouter des méthodes par la suite
        ImageView[] courts = new ImageView[7];
        ImageView menuButton = (ImageView) findViewById(R.id.menubutton);
        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);
        ImageView court1 = (ImageView) findViewById(R.id.t1);
        ImageView court2 = (ImageView) findViewById(R.id.t2);
        ImageView court3 = (ImageView) findViewById(R.id.t3);
        ImageView court4 = (ImageView) findViewById(R.id.t4);
        ImageView court5 = (ImageView) findViewById(R.id.t5);
        ImageView court6 = (ImageView) findViewById(R.id.t6);
        ImageView court7 = (ImageView) findViewById(R.id.t7);

        // ajouter les courts au tableau de courts
        courts[0] = court1;
        courts[1] = court2;
        courts[2] = court3;
        courts[3] = court4;
        courts[4] = court5;
        courts[5] = court6;
        courts[6] = court7;

        // Add the onclicklistener for every buttons
        menuButton.setOnClickListener(this);
        toolbarButton.setOnClickListener(this);

        // ajouter les différents paramètres aux courts grâce au tableau
        for (int i = 0 ; i < courts.length ; i++){
            courts[i].setOnClickListener(this);
            disableIfFull(courts[i]);
            disableIfType(courts[i]);
        }
    }

    /**
     * Method that redirect the user to the Menu Activity.
     *
     * trigger : menu button situated on the top left of the activity.
     */
    private void openMenuActivity(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    /**
     * Method that redirect the user to the Summary Activity.
     *
     * trigger : account Button situated on the toolbar
     */
    private void openSummaryActivity(){
        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra("curDate", curDate);
        intent.putExtra("isIndoor", isIndoor);
        intent.putExtra("Hour", hour);
        intent.putExtra("court", courtSelected);
        startActivity(intent);
    }

    /**
     * Method that redirect the user to the Account Activity.
     *
     * trigger : account Button situated on the toolbar
     */
    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    /**
     * Method that will disable certain courts based on user type choice.
     *
     * @param courtToCheck the court to check. Every court will pass here at the start of the activity
     */
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

    /**
     * Method that checks at the start of the application which court is reserved based on the previous choices of the user
     *
     * @param courtToCheck the court to check. Every court will pass here at the start of the activity
     */
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

    /**
     * Method that react to the clicks of the user
     *
     * @param view the current view in order to detect the onClick elements
     */
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