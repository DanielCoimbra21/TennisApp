package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tennisapplication.R;
import com.example.tennisapplication.UI.AccountActivity;
import com.example.tennisapplication.UI.ChooseTimeActivity;
import com.google.android.material.button.MaterialButton;

public class CourtTypeActivity extends AppCompatActivity {

    private boolean isIndoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_type);

        MaterialButton Indoor = (MaterialButton) findViewById(R.id.indoorbutton);
        MaterialButton Outdoor = (MaterialButton) findViewById(R.id.outdoorbutton);
        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);

        Indoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isIndoor = true;
                openChooseTimeActivity();
            }
        });

        Outdoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isIndoor = false;
                openChooseTimeActivity();
            }
        });

        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountActivity();
            }
        });
    }

    private void openChooseTimeActivity(){
        Intent intent = new Intent(this, ChooseTimeActivity.class);
        startActivity(intent);
    }

    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}