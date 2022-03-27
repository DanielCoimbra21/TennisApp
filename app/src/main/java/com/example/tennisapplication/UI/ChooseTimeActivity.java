package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.R;
import com.example.tennisapplication.database.repository.ReservationRepository;

public class ChooseTimeActivity extends AppCompatActivity implements View.OnClickListener{

    private int selectedTime;
    private boolean[] buttonReservedTab;
    private boolean isIndoor;
    private String curDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);

        Intent intent = getIntent();
        isIndoor = intent.getBooleanExtra("isIndoor", true);
        curDate = intent.getStringExtra("curDate");

        // set le tableau qui va check si la réservation est pleine ou non
        buttonReservedTab = new boolean[23];
        for(int i = 0 ; i < buttonReservedTab.length ; i++){
            buttonReservedTab[i] = false;
        }

        // appel et set des différents boutons
        Button toolbarButton = (Button) findViewById(R.id.toolbaraccountbutton);
        toolbarButton.setOnClickListener(this);
        Button button7 = (Button) findViewById(R.id.button7);

        button7.setOnClickListener(this);
        Button button8 = (Button) findViewById(R.id.button8);

        button8.setOnClickListener(this);
        Button button9 = (Button) findViewById(R.id.button9);

        button9.setOnClickListener(this);
        Button button10 = (Button) findViewById(R.id.button10);

        button10.setOnClickListener(this);
        Button button11 = (Button) findViewById(R.id.button11);

        button11.setOnClickListener(this);
        Button button12 = (Button) findViewById(R.id.button12);

        button12.setOnClickListener(this);
        Button button13 = (Button) findViewById(R.id.button13);

        button13.setOnClickListener(this);
        Button button14 = (Button) findViewById(R.id.button14);

        button14.setOnClickListener(this);
        Button button15 = (Button) findViewById(R.id.button15);

        button15.setOnClickListener(this);
        Button button16 = (Button) findViewById(R.id.button16);

        button16.setOnClickListener(this);
        Button button17 = (Button) findViewById(R.id.button17);

        button17.setOnClickListener(this);
        Button button18 = (Button) findViewById(R.id.button18);

        button18.setOnClickListener(this);
        Button button19 = (Button) findViewById(R.id.button19);

        button19.setOnClickListener(this);
        Button button20 = (Button) findViewById(R.id.button20);

        button20.setOnClickListener(this);
        Button button21 = (Button) findViewById(R.id.button21);

        button21.setOnClickListener(this);
        Button button22 = (Button) findViewById(R.id.button22);

        button22.setOnClickListener(this);
    }



    private void openCourtSelectionActivity(){
        Intent intent = new Intent(this, CourtSelectionActivity.class);
        intent.putExtra("curDate", curDate);
        intent.putExtra("isIndoor", isIndoor);
        intent.putExtra("Hour", selectedTime);
        startActivity(intent);
    }

    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.toolbaraccountbutton){
            openAccountActivity();
        }else {
            selectedTime = Integer.valueOf((String) view.getTag());
            System.out.println(selectedTime);
            openCourtSelectionActivity();
        }
    }

    public int getSelectedTime(){
        return selectedTime;
    }
}