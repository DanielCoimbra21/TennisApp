package com.example.tennisapplication.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.R;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.database.repository.ReservationRepository;
import com.example.tennisapplication.util.OnAsyncEventListener;
import com.example.tennisapplication.viewModel.player.PlayerViewModel;
import com.example.tennisapplication.viewModel.reservation.ReservationListViewModel;
import com.example.tennisapplication.viewModel.reservation.ReservationViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import java.text.NumberFormat;
import java.util.List;

public class ReservationDetailsActivity extends BaseActivity {

    private ReservationRepository reservationRepository;
    private PlayerEntity playerEntity;
    private String curDate;
    private int hour;
    private int court;
    private PlayerViewModel viewModel;


    /**
     * Initialisation method of the Reservation details Activity
     *
     * @param savedInstanceState with the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);

        reservationRepository = ((BaseApp) getApplication()).getReservationRepository();

        // get previous informations
        Intent intent = getIntent();
        curDate = intent.getStringExtra("date");
        hour = Integer.valueOf(intent.getStringExtra("schedule"));
        court = intent.getIntExtra("courtNumber", -1);

        // Initialization of the page
        TextView date = (TextView) findViewById(R.id.reservationDate);
        date.setText(curDate);
        TextView courtnb = (TextView) findViewById(R.id.reservationCourt);
        courtnb.setText(String.valueOf(court));
        TextView heureDebut = (TextView) findViewById(R.id.reservationFrom);
        heureDebut.setText(String.format("%02d",hour)+"h00");
        TextView heureFin = (TextView) findViewById(R.id.reservationTo);
        heureFin.setText(String.format("%02d",(hour+1))+"h00");
        TextView name = (TextView) findViewById(R.id.reservationName);
        TextView surname = (TextView) findViewById(R.id.reservationSurname);
        PlayerViewModel.Factory factory = new PlayerViewModel.Factory(getApplication(), FirebaseAuth.getInstance().getCurrentUser().getUid());
        viewModel = new ViewModelProvider(this,factory).get(PlayerViewModel.class);
        viewModel.getPlayer().observe(this, playerEntity -> {
            if (playerEntity != null) {
                this.playerEntity = playerEntity;
                if (playerEntity != null){
                    name.setText(playerEntity.getLastName().toUpperCase());
                    name.getText().toString();
                    surname.setText(playerEntity.getFirstName());
                    surname.getText().toString();
                }
            }
        });

        // toolbar Button
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
     * trigger : Account button situated on the top rigth of the activity.
     */
    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}
