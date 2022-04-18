package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tennisapplication.R;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.repository.PlayerRepository;
import com.example.tennisapplication.sessions.SessionManager;
import com.example.tennisapplication.util.OnAsyncEventListener;
import com.example.tennisapplication.viewModel.player.PlayerViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = "AccountActivity";
    SessionManager sessionManager;
    private PlayerRepository repository;
    private PlayerViewModel viewModel ;
    private PlayerEntity player;

    /**
     * On create method for the AccountActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Button editButton = (Button) findViewById(R.id.editBtn);
        Button deleteButton = (Button) findViewById(R.id.deleteBtn);

        // Create menu Button
        ImageView menuBtn = (ImageView) findViewById(R.id.menubutton);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuActivity();
            }
        });

        // Create ListButton Button
        Button listReservations = (Button) findViewById(R.id.reservationHistory);
        listReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReservationsActivity();
            }
        });

        // initialisation des différents champs
        TextView firstName = (TextView) findViewById(R.id.accountSurname);
        TextView lastName = (TextView) findViewById(R.id.accountName);
        TextView age = (TextView) findViewById(R.id.accountAge);
        TextView mail = (TextView) findViewById(R.id.accountMailAddress);
        TextView phone = (TextView) findViewById(R.id.accountPhoneNumber);

        PlayerViewModel.Factory factory = new PlayerViewModel.Factory(
                getApplication(), FirebaseAuth.getInstance().getCurrentUser().getUid());
        viewModel = new ViewModelProvider(this, factory).get(PlayerViewModel.class);
        viewModel.getPlayer().observe(this, playerEntity -> {
            if (playerEntity != null){
                player  = playerEntity;
                if (player != null){
                    firstName.setText(player.getFirstName());
                    firstName.getText().toString();
                    lastName.setText(player.getLastName());
                    lastName.getText().toString();
                    age.setText(player.getAge());
                    age.getText().toString();
                    mail.setText(player.getEmail());
                    mail.getText().toString();
                    phone.setText(player.getPhoneNumber());
                    phone.getText().toString();
                }
            }
        });

        /**
         * set an action listener on editButton
         */
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditActivity();
            }
        });


        /**
         * set an action listner on delete button
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount();
                Toast.makeText(AccountActivity.this,"Account deleted", Toast.LENGTH_SHORT).show();
                openMainActivity();
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
     * Method that redirect the user to the Edit Activity.
     *
     * trigger : edit button.
     */
    private void openEditActivity(){
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("playerId", player.getEmail());
        startActivity(intent);
    }

    /**
     * Method that redirect the user to the Main Activity.
     *
     * trigger : action after clicking on the delete button.
     */
    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Method that deletes the account of the player.
     *
     * trigger : delecte button.
     */
    private void deleteAccount(){
        viewModel.deletePlayer(player, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "deleteAccount:success");
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "deleteAccount:failure");
            }
        });
    }


    /**
     * open the activity with all the reservations for this user
     */
    private void openReservationsActivity(){
        Intent intent = new Intent(this, ReservationsActivity.class);
        startActivity(intent);
    }
}