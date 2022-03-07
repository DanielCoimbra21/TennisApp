package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tennisapplication.R;

public class ChooseTimeActivity extends AppCompatActivity implements View.OnClickListener{

    private int selectedTime;
    private boolean[] buttonReservedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);

        // set le tableau qui va check si la réservation est pleine ou non
        buttonReservedTab = new boolean[23];
        for(int i = 0 ; i < buttonReservedTab.length ; i++){
            buttonReservedTab[i] = false;
        }

        // appel et set des différents boutons
        Button toolbarButton = (Button) findViewById(R.id.toolbaraccountbutton);
        toolbarButton.setOnClickListener(this);
        Button button7 = (Button) findViewById(R.id.button7);
        disableIfFull(button7);
        button7.setOnClickListener(this);
        Button button8 = (Button) findViewById(R.id.button8);
        disableIfFull(button8);
        button8.setOnClickListener(this);
        Button button9 = (Button) findViewById(R.id.button9);
        disableIfFull(button9);
        button9.setOnClickListener(this);
        Button button10 = (Button) findViewById(R.id.button10);
        disableIfFull(button10);
        button10.setOnClickListener(this);
        Button button11 = (Button) findViewById(R.id.button11);
        disableIfFull(button11);
        button11.setOnClickListener(this);
        Button button12 = (Button) findViewById(R.id.button12);
        disableIfFull(button12);
        button12.setOnClickListener(this);
        Button button13 = (Button) findViewById(R.id.button13);
        disableIfFull(button13);
        button13.setOnClickListener(this);
        Button button14 = (Button) findViewById(R.id.button14);
        disableIfFull(button14);
        button14.setOnClickListener(this);
        Button button15 = (Button) findViewById(R.id.button15);
        disableIfFull(button15);
        button15.setOnClickListener(this);
        Button button16 = (Button) findViewById(R.id.button16);
        disableIfFull(button16);
        button16.setOnClickListener(this);
        Button button17 = (Button) findViewById(R.id.button17);
        disableIfFull(button17);
        button17.setOnClickListener(this);
        Button button18 = (Button) findViewById(R.id.button18);
        disableIfFull(button18);
        button18.setOnClickListener(this);
        Button button19 = (Button) findViewById(R.id.button19);
        disableIfFull(button19);
        button19.setOnClickListener(this);
        Button button20 = (Button) findViewById(R.id.button20);
        disableIfFull(button20);
        button20.setOnClickListener(this);
        Button button21 = (Button) findViewById(R.id.button21);
        disableIfFull(button21);
        button21.setOnClickListener(this);
        Button button22 = (Button) findViewById(R.id.button22);
        disableIfFull(button22);
        button22.setOnClickListener(this);
    }

    private void disableIfFull(Button buttonToCheck){
        int i = Integer.valueOf((String) buttonToCheck.getTag());

        // remplacer 1==0 par du code logique en relation avec la base de donnée
        if(1==0){
            //buttonToCheck.setTextColor(500000);
            buttonReservedTab[i] = true;
            buttonToCheck.setBackgroundResource(R.drawable.rounded_disabled_button);
        }
    }

    private void openCourtSelectionActivity(){
        Intent intent = new Intent(this, CourtSelectionActivity.class);
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
        }
        else {
            Button test = (Button) findViewById(view.getId());
            int i = Integer.parseInt((String) test.getTag());
            if(buttonReservedTab[i] != true){
                selectedTime = i;
                openCourtSelectionActivity();
            }
            else{
                Toast.makeText(this, "sorry c'est complet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int getSelectedTime(){
        return selectedTime;
    }
}