package com.example.tennisapplication.UI;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.R;
import com.example.tennisapplication.database.entity.CourtEntity;
import com.example.tennisapplication.database.repository.CourtRepository;
import com.example.tennisapplication.database.repository.PlayerRepository;
import com.example.tennisapplication.sessions.SessionManager;
import com.example.tennisapplication.util.OnAsyncEventListener;
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
        repository.signIn(email, passwordV, task -> {
            if (task.isSuccessful()) {
                openHomePageActivity();
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            } else {
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
        Intent intent = new Intent(this, NewAccountActivity.class);
        startActivity(intent);
    }
}