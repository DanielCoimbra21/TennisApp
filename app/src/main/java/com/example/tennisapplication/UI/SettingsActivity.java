package com.example.tennisapplication.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tennisapplication.R;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private RadioButton french;
    private RadioButton english;

    /**
     * Initialisation method of the Settings Activity
     *
     * @param savedInstanceState with the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Create ChangeButton
        Button switchLanguage = (Button) findViewById(R.id.changeButton);
        switchLanguage.setVisibility(View.INVISIBLE);

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

        // Create radioButtons
        french = (RadioButton) findViewById(R.id.radiobuttonfrench);
        french.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                english.setChecked(false);
                switchLanguage.setVisibility(View.VISIBLE);
            }
        });
        english = (RadioButton) findViewById(R.id.radiobuttonenglish);
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                french.setChecked(false);
                switchLanguage.setVisibility(View.VISIBLE);
            }
        });

        // switch language onclicklistener that will call the languageChange method
        switchLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languageChange();
            }
        });
    }

    /**
     * Method that will change the language of the mobile application. It will then refresh the activity
     * in order to directly see the language update.
     *
     * trigger : switch language toggle button then apply changes button.
     */
    private void languageChange(){
        if (french.isChecked()){
            String language  = "fr"; // français
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            // restart the activity
            finish();
            startActivity(getIntent());
        }else{
            String language  = "en"; // anglais
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            // restart the activity
            finish();
            startActivity(getIntent());
        }
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
     * Method that redirect the user to the Menu Activity.
     *
     * trigger : menu button situated on the top left of the activity.
     */
    private void openMenuActivity(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
