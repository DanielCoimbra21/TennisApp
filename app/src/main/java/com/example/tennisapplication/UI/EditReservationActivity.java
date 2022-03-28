package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.R;
import com.example.tennisapplication.database.async.player.CreatePlayer;
import com.example.tennisapplication.database.async.reservation.CreateReservation;
import com.example.tennisapplication.database.async.reservation.UpdateReservation;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.database.repository.PlayerRepository;
import com.example.tennisapplication.database.repository.ReservationRepository;
import com.example.tennisapplication.util.OnAsyncEventListener;
import com.example.tennisapplication.viewModel.player.PlayerViewModel;
import com.example.tennisapplication.viewModel.reservation.ReservationViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class EditReservationActivity extends AppCompatActivity {

    private static final String TAG = "EditAccountActivity";

    private EditText etHourEdit;
    private EditText etDateEdit;
    private String owner;

    private String curDate;
    private int hour;
    private int courtId;
    private ReservationViewModel reservationViewModel;
    private ReservationEntity reservation;
    private ReservationRepository repository;
    private List<ReservationEntity> reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reservation);


        repository = ((BaseApp) getApplication()).getReservationRepository();


        // get les informations précédemment entrées
        Intent intent = getIntent();
        curDate = intent.getStringExtra("date");
        hour = Integer.valueOf(intent.getStringExtra("schedule"));
        courtId = intent.getIntExtra("courtNumber", -1);

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        owner = settings.getString(BaseActivity.PREFS_USER, null);

        initializeEdit();

        // Create menu Button
        ImageView menuBtn = (ImageView) findViewById(R.id.menubutton);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuActivity();
            }
        });

        // Create account Button
        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountActivity();
            }
        });

        int reservationId = getIntent().getIntExtra("reservationId", 0);
        ReservationViewModel.Factory factory = new ReservationViewModel.Factory(getApplication(), reservationId);

        reservationViewModel = ViewModelProviders.of(this, factory).get(ReservationViewModel.class);
        reservationViewModel.getReservation().observe(this, reservationEntity -> {
            if (reservationEntity != null){
                reservation = reservationEntity;
                etHourEdit.setText(reservation.getSchedule());
                etDateEdit.setText(reservation.getDate());
            }
        });

    }

    private void initializeEdit() {

        etHourEdit = findViewById(R.id.tv_hourEdit);
        etDateEdit = findViewById(R.id.tv_dateEdit);


        Button saveBtn = findViewById(R.id.button_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges(
                        etHourEdit.getText().toString(),
                        etDateEdit.getText().toString()
                );
            }
        });

    }

    private void saveChanges(String hour, String date) {

        reservations = new ArrayList<>();

        if (hour.isEmpty()){
            etHourEdit.setError(getString(R.string.error_invalid_champ));
            etHourEdit.requestFocus();
            etHourEdit.setText("");
            return;
        }

        if (date.isEmpty()){
            etDateEdit.setError(getString(R.string.error_invalid_champ));
            etDateEdit.requestFocus();
            etDateEdit.setText("");
            return;
        }

        int i = courtId;

        repository.getNotAvailableCourts(hour, curDate, getApplication()).observe(this, reservationEntities -> {
            if (reservationEntities != null)
            {
                reservations = reservationEntities;
                for (ReservationEntity r: reservations) {
                    if(i != r.getCourtNumber())
                    {
                        if(!"".equals(hour) || !"".equals(date)){
                            reservation.setSchedule(hour);
                            reservation.setDate(date);
                            reservationViewModel.updateReservation(reservation, new OnAsyncEventListener() {
                                @Override
                                public void onSuccess() {
                                    Log.d(TAG, "updateAccount: success");
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    Log.d(TAG, "updateAccount: failure", e);
                                }
                            });
                        }
                    }
                }
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


}