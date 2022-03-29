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
import android.widget.Toast;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.R;
import com.example.tennisapplication.database.async.reservation.CreateReservation;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.database.repository.ReservationRepository;
import com.example.tennisapplication.util.OnAsyncEventListener;
import com.example.tennisapplication.viewModel.player.PlayerViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.concurrent.atomic.AtomicBoolean;

public class SummaryActivity extends AppCompatActivity {

    private boolean isIndoor;
    private String curDate;
    private int hour;
    private int court;
    private PlayerEntity playerEntity;
    private ReservationRepository reservationRepository;
    private PlayerViewModel viewModel;

    /**
     * Initialisation method of the Summary Activity
     *
     * @param savedInstanceState with the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // get the reservation repository
        reservationRepository = ((BaseApp) getApplication()).getReservationRepository();

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

        // Afficher le court choisi
        ImageView court1 = (ImageView) findViewById(R.id.t1);
        ImageView court2 = (ImageView) findViewById(R.id.t2);
        ImageView court3 = (ImageView) findViewById(R.id.t3);
        ImageView court4 = (ImageView) findViewById(R.id.t4);
        ImageView court5 = (ImageView) findViewById(R.id.t5);
        ImageView court6 = (ImageView) findViewById(R.id.t6);
        ImageView court7 = (ImageView) findViewById(R.id.t7);

        // By default, every court is invisible on the UI
        court1.setVisibility(View.INVISIBLE);
        court2.setVisibility(View.INVISIBLE);
        court3.setVisibility(View.INVISIBLE);
        court4.setVisibility(View.INVISIBLE);
        court5.setVisibility(View.INVISIBLE);
        court6.setVisibility(View.INVISIBLE);
        court7.setVisibility(View.INVISIBLE);

        // If a certain court is selected, it will be activated on the UI
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

        // Create account button
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
        String h = String.valueOf(hour);

        // OnClickListener on the book button
        Button bookButton = (Button) findViewById(R.id.bookButton);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReservation(h, curDate, playerEntity.getEmail(), court);
            }
        });
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
     * Method that redirect the user to the Account Activity.
     *
     * trigger : account Button situated on the toolbar
     */
    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    /**
     * Method that add the selected reservation to the database.
     *
     * trigger : book button.
     * @param schedule, this is the hour of the court reservation
     * @param date, this is the date in string
     * @param playerId, this is the id of the user that is registrated
     * @param courtNumber, this is the number of the selected court.
     */
    private void addReservation(String schedule, String date, String playerId, int courtNumber){
        ReservationEntity newReservation = new ReservationEntity(schedule, date, playerId, courtNumber);
        new CreateReservation(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false);
            }
        }).execute(newReservation);
    }

    /**
     * Method that will handle the creation status and will change page if it is successfully created.
     *
     * trigger : addReservation Method.
     * @param response, this is the status of the success/failure of the reservation in the database.
     */
    private void setResponse(Boolean response) {
        if (response) {
            final SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
            editor.putString(BaseActivity.PREFS_USER, playerEntity.getEmail());
            editor.apply();

            Intent intent = new Intent(this, HomePageActivity.class);
            finish();
            startActivity(intent);
            Toast.makeText(SummaryActivity.this, "Reservation Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }
}