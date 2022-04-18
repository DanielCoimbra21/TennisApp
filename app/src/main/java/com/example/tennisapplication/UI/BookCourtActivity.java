package com.example.tennisapplication.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tennisapplication.R;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BookCourtActivity extends AppCompatActivity {

    private String curDate;

    /**
     * Initialisation method of the Book Court Activity
     *
     * @param savedInstanceState with the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_court);

        // Create Calendar View Button
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                month++;
                curDate = String.valueOf(dayOfMonth) + "." + String.valueOf(month) + "." + String.valueOf(year);
            }
        });

        // Create Next Activity Button
        MaterialButton nextActivity = (MaterialButton) findViewById(R.id.nextbutton);
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mettre la date d'aujourd'hui si rien n'est sélectionné
                if(curDate == null){
                    Date date = new Date();
                    curDate = new SimpleDateFormat("d.M.yyyy").format(date);
                }
                // afficher la date sélectionnée grâce à un toast et passer à la suite
                Toast.makeText(BookCourtActivity.this, "sip: "+ curDate, Toast.LENGTH_SHORT).show();
                openCourtTypeActivity();
            }
        });

        // Create menu Button
        ImageView menuBtn = (ImageView) findViewById(R.id.menubutton);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuActivity();
            }
        });

        // Create ToolBar Button
        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountActivity();
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
     * Method that redirect the user to the Court Type Activity.
     *
     * trigger : when user clicks on the next activity button.
     */
    private void openCourtTypeActivity(){
        Intent intent = new Intent(this, CourtTypeActivity.class);
        intent.putExtra("curDate", curDate);
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
}