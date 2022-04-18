package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.R;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.repository.PlayerRepository;
import com.example.tennisapplication.util.OnAsyncEventListener;
import com.example.tennisapplication.viewModel.player.PlayerViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "AccountDetailActivity";

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etAge;
    private EditText etPhone;
    private EditText etPwd1;
    private String owner;
    private PlayerViewModel playerViewModel;
    private PlayerRepository repository;
    private PlayerEntity player;



    /**
     * Initialisation method of the EditAccount Activity
     *
     * @param savedInstanceState with the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        repository = ((BaseApp) getApplication()).getPlayerRepository();

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        owner = settings.getString(BaseActivity.PREFS_USER, null);

        // Call initialisation method that will initialize all the fields
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


        /**
         * Call the Player view model
         */
        PlayerViewModel.Factory factory = new PlayerViewModel.Factory(
                getApplication(), FirebaseAuth.getInstance().getCurrentUser().getUid());
        playerViewModel = new ViewModelProvider(this,factory).get(PlayerViewModel.class);
        playerViewModel.getPlayer().observe(this, accountEntity -> {
            if (accountEntity != null) {
                player = accountEntity;
                etFirstName.setText(player.getFirstName());
                etLastName.setText(player.getLastName());
                etAge.setText(player.getAge());
                etPhone.setText(player.getPhoneNumber());
            }
        });


    }


    /**
     * Method called to set the new infos about the player
     */
    private void initializeEdit() {
        etFirstName = findViewById(R.id.tv_firstname);
        etLastName = findViewById(R.id.tv_lastname);
        etAge = findViewById(R.id.tv_age);
        etPhone = findViewById(R.id.tv_phone);
        etPwd1 = findViewById(R.id.tv_password1);

        Button saveBtn = findViewById(R.id.button_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                        etFirstName.getText().toString(),
                        etLastName.getText().toString(),
                        etAge.getText().toString(),
                        etPhone.getText().toString(),
                        etPwd1.getText().toString()
                );
            }
        });
    }

    /**
     * Method used when player clicks on save data
     * If the information is not entered the rigth way it will display errors
     */
    private void saveChanges(String playerId, String firstName, String lastName,  String age, String phone ,String pwd) {

        if (firstName.isEmpty()){
            etFirstName.setError(getString(R.string.error_invalid_champ));
            etFirstName.requestFocus();
            etFirstName.setText("");
            return;
        }

        if (lastName.isEmpty()){
            etLastName.setError(getString(R.string.error_invalid_champ));
            etLastName.requestFocus();
            etLastName.setText("");
            return;
        }

        if (lastName.isEmpty()){
            etLastName.setError(getString(R.string.error_invalid_champ));
            etLastName.requestFocus();
            etLastName.setText("");
            return;
        }


        if (age.isEmpty()){
            etAge.setError(getString(R.string.error_invalid_champ));
            etAge.requestFocus();
            etAge.setText("");
            return;
        }

        if (phone.isEmpty()){
            etPhone.setError(getString(R.string.error_invalid_champ));
            etPhone.requestFocus();
            etPhone.setText("");
            return;
        }

        repository.signIn(FirebaseAuth.getInstance().getCurrentUser().getEmail(), pwd, task -> {
            if (task.isSuccessful()) {
                player.setAge(age);
                player.setFirstName(firstName);
                player.setLastName(lastName);
                player.setPhoneNumber(phone);
                playerViewModel.updatePlayer(player, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Update: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "Update: failure", e);
                    }
                });
            } else {
                Toast.makeText(EditActivity.this, "Bad Password", Toast.LENGTH_SHORT).show();
            }
        });

        openAccountActivity();
        finish();
    }


    /**
     * Method that redirect the user to the Menu Activity.
     *
     * trigger : menu button situated on the top left of the activity.
     */
    private void openMenuActivity(){
        Intent intent = new Intent(this, MenuActivity.class);
        finish();
        startActivity(intent);

    }

    /**
     * Method that redirect the user to the Account Activity.
     *
     * trigger : account Button situated on the toolbar
     */
    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        finish();
        startActivity(intent);

    }
}