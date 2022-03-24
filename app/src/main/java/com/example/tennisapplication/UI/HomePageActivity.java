package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tennisapplication.R;
import com.example.tennisapplication.UI.AccountActivity;
import com.example.tennisapplication.UI.BookCourtActivity;
import com.google.android.material.button.MaterialButton;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);
        MaterialButton bookButton = (MaterialButton) findViewById(R.id.bookacourtbutton);

        MaterialButton reservationsButton = findViewById(R.id.myReservationsButton);


        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBookCourtActivity();
            }
        });

        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountActivity();
            }
        });

        reservationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMyReservations();
            }
        });
    }

    private void openBookCourtActivity(){
        Intent intent = new Intent(this, BookCourtActivity.class);
        startActivity(intent);
    }


    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    private void openMyReservations(){
        Intent intent = new Intent(this, ReservationsActivity.class);
        startActivity(intent);
    }
}