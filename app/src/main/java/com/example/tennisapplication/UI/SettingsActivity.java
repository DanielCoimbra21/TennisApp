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

        switchLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languageChange();
            }
        });
    }

    private void languageChange(){
        if (french.isChecked()){
            String language  = "fr"; // français
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            finish();
            startActivity(getIntent());
        }else{
            String language  = "en"; // anglais
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            finish();
            startActivity(getIntent());
        }
    }

    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    private void openMenuActivity(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
