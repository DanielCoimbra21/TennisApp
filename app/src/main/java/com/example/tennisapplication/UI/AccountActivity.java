package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

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

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = "AccountDetailActivity";
    SessionManager sessionManager;
    private PlayerRepository repository;
    private PlayerEntity playerEntity;
    private PlayerViewModel viewModel;

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

        // initialisation des différents champs
        TextView firstName = (TextView) findViewById(R.id.accountSurname);
        TextView lastName = (TextView) findViewById(R.id.accountName);
        TextView age = (TextView) findViewById(R.id.accountAge);
        TextView mail = (TextView) findViewById(R.id.accountMailAddress);
        TextView phone = (TextView) findViewById(R.id.accountPhoneNumber);
        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME,0);
        String user = settings.getString(BaseActivity.PREFS_USER, null);
        PlayerViewModel.Factory factory = new PlayerViewModel.Factory(getApplication(), user);
        viewModel = ViewModelProviders.of(this, factory).get(PlayerViewModel.class);
        viewModel.getPlayer().observe(this, playerEntity -> {
            if (playerEntity != null) {
                this.playerEntity = playerEntity;
                if (playerEntity != null){
                    //Set Username on TextView
                    firstName.setText(playerEntity.getFirstName());
                    firstName.getText().toString();
                    lastName.setText(playerEntity.getLastName());
                    lastName.getText().toString();
                    age.setText(playerEntity.getAge());
                    age.getText().toString();
                    mail.setText(playerEntity.getEmail());
                    mail.getText().toString();
                    phone.setText(playerEntity.getPhoneNumber());
                    phone.getText().toString();
                }
            }
        });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditActivity();
            }
        });


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
        intent.putExtra("playerId", playerEntity.getEmail());
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
        viewModel.deletePlayer(playerEntity, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "deleteAccount:success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "deleteAccount:failure");
            }
        });
    }
}