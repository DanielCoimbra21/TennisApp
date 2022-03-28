package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tennisapplication.R;
import com.example.tennisapplication.UI.AccountActivity;
import com.example.tennisapplication.UI.ChooseTimeActivity;
import com.google.android.material.button.MaterialButton;

public class CourtTypeActivity extends AppCompatActivity {

    private boolean isIndoor;
    private String curDate;

    /**
     * Initialisation method of the Court Type Activity
     *
     * @param savedInstanceState with the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_type);

        // get the previous information entered by the user
        Intent intent = getIntent();
        curDate = intent.getStringExtra("curDate");

        // Create Indoor Button that set the isIndoor variable
        MaterialButton Indoor = (MaterialButton) findViewById(R.id.indoorbutton);
        Indoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isIndoor = true;
                openChooseTimeActivity();
            }
        });

        // Create Outdoor Button that set the isIndoor variable
        MaterialButton Outdoor = (MaterialButton) findViewById(R.id.outdoorbutton);
        Outdoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isIndoor = false;
                openChooseTimeActivity();
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

        // Create Toolbar Burron
        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountActivity();
            }
        });
    }

    /**
     * Method that redirect the user to the Choose Time Activity.
     *
     * trigger : when user clicks on a type button.
     */
    private void openChooseTimeActivity(){
        Intent intent = new Intent(this, ChooseTimeActivity.class);
        intent.putExtra("isIndoor", isIndoor);
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