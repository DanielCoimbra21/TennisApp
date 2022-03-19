package com.example.tennisapplication.UI;

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
import com.example.tennisapplication.database.repository.PlayerRepository;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private PlayerRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = ((BaseApp) getApplication()).getPlayerRepository();

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.gobutton);
        MaterialButton newAccountbtn = (MaterialButton) findViewById(R.id.createnewaccountbutton);

        // Login button
        // unique password et username actif : admin et admin (tkt c'est secure)
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
                // passe à la vue de création d'un account
                openAccountActivity();
            }
        });
    }

    private void attemptLogin(String email, String passwordV) {

        repository.getPlayer(email,getApplication()).observe(MainActivity.this, playerEntity -> {
            if (playerEntity != null) {
                if (playerEntity.getPassword().equals(passwordV)) {

                    //Session
                    SharedPreferences.Editor editor = getSharedPreferences(BaseApp.PREFS_NAME, 0).edit();
                    editor.putString(BaseApp.PREFS_NAME, playerEntity.getEmail());
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
        });
    }


    private void openHomePageActivity(){
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    private void openAccountActivity(){
        Intent intent = new Intent(this, NewAccountActivity.class);
        startActivity(intent);
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

}