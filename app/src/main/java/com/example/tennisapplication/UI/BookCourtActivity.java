package com.example.tennisapplication.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.tennisapplication.R;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BookCourtActivity extends AppCompatActivity {

    private String curDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_court);

        MaterialButton nextActivity = (MaterialButton) findViewById(R.id.nextbutton);
        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendar);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                curDate = String.valueOf(dayOfMonth) + "." + String.valueOf(month) + "." + String.valueOf(year);
            }
        });

        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mettre la date d'aujourd'hui si rien n'est sélectionné
                if(curDate == null){
                    Date date = new Date();
                    curDate = new SimpleDateFormat("dd.MM.yyyy").format(date);
                }
                // afficher la date sélectionnée grâce à un toast et passer à la suite
                Toast.makeText(BookCourtActivity.this, "sip: "+ curDate, Toast.LENGTH_SHORT).show();
                openCourtTypeActivity();
            }
        });

        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountActivity();
            }
        });
    }


    private void openCourtTypeActivity(){
        Intent intent = new Intent(this, CourtTypeActivity.class);
        // envoyer la date
        intent.putExtra("curDate", curDate);
        startActivity(intent);
    }

    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}