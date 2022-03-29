package com.example.tennisapplication.UI;

import static com.example.tennisapplication.database.AppDatabase.getInstance;
import static com.example.tennisapplication.database.AppDatabase.initializeDemoData;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.R;
import com.example.tennisapplication.database.AppDatabase;
import com.example.tennisapplication.database.repository.PlayerRepository;
import com.example.tennisapplication.sessions.SessionManager;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private PlayerRepository repository;
    SessionManager sessionManager;

    /**
     * Initialisation method of the Main Activity
     *
     * @param savedInstanceState with the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initilaize SessionManager and repository
        sessionManager = new SessionManager(getApplicationContext());
        repository = ((BaseApp) getApplication()).getPlayerRepository();

        // Initialize different buttons
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.gobutton);
        MaterialButton newAccountbtn = (MaterialButton) findViewById(R.id.createnewaccountbutton);

        // Login button onclicklistener that will call the attemptLogin method
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString();
                String passwordV = password.getText().toString();
                attemptLogin(email, passwordV);
            }
        });

        // Create New Account Button
        newAccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountActivity();
            }
        });
    }

    /**
     * Method that attempt to login in order to access the HomePage, if login failed, inform the user
     *
     * @param email the email of the user
     * @param passwordV the password of the user
     */
    private void attemptLogin(String email, String passwordV) {
        repository.getPlayer(email,getApplication()).observe(MainActivity.this, playerEntity -> {
            if (playerEntity != null) {
                if (playerEntity.getPassword().equals(passwordV)) {
                    //Session
                    SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
                    editor.putString(BaseActivity.PREFS_USER, playerEntity.getEmail());
                    editor.apply();

                    // login correct
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    openHomePageActivity();
                }
                else {
                    // login incorrect
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                // login incorrect
                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method that redirect the user to the Home Page Activity.
     *
     * trigger : connection Button.
     */
    private void openHomePageActivity(){
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    /**
     * Method that redirect the user to the Account Activity.
     *
     * trigger : account button situated on the top right of the toolbar.
     */
    private void openAccountActivity(){
        //initializeDemoData(AppDatabase.getInstance(this));
        repository.getPlayer("",getApplication()).observe(MainActivity.this, playerEntity -> {});
        Intent intent = new Intent(this, NewAccountActivity.class);
        startActivity(intent);
    }
}