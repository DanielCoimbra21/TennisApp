package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tennisapplication.R;
import com.example.tennisapplication.UI.AccountActivity;
import com.example.tennisapplication.UI.BookCourtActivity;
import com.google.android.material.button.MaterialButton;

public class HomePageActivity extends AppCompatActivity {

    /**
     * Initialisation method of the Home Page Activity
     *
     * @param savedInstanceState with the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Create menu Button
        ImageView menuBtn = (ImageView) findViewById(R.id.menubutton);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuActivity();
            }
        });

        // Create book Button
        MaterialButton bookButton = (MaterialButton) findViewById(R.id.bookacourtbutton);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBookCourtActivity();
            }
        });

        // Create toolbar Button
        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountActivity();
            }
        });

        // Create Reservation Button
        MaterialButton reservationsButton = findViewById(R.id.myReservationsButton);
        reservationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMyReservations();
            }
        });
    }

    /**
     * Method that redirect the user to the Book Court Activity.
     *
     * trigger : when user clicks on the book.
     */
    private void openBookCourtActivity(){
        Intent intent = new Intent(this, BookCourtActivity.class);
        startActivity(intent);
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
     * Method that redirect the user to the Reservation Activity.
     *
     * trigger : open reservations button.
     */
    private void openMyReservations(){
        Intent intent = new Intent(this, ReservationsActivity.class);
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