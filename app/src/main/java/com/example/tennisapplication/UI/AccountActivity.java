package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tennisapplication.R;
import com.example.tennisapplication.database.repository.PlayerRepository;

public class AccountActivity extends AppCompatActivity {

    private PlayerRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        TextView myText = findViewById(R.id.playerName);
        myText.setText("TEst");
    }
}