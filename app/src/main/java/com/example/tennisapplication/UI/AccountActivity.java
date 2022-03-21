package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tennisapplication.R;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.repository.PlayerRepository;
import com.example.tennisapplication.sessions.SessionManager;
import com.example.tennisapplication.viewModel.player.PlayerViewModel;

public class AccountActivity extends AppCompatActivity {

    SessionManager sessionManager;

    private PlayerRepository repository;
    private PlayerEntity playerEntity;
    TextView tvEmail;

    private PlayerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME,0);
        String user = settings.getString(BaseActivity.PREFS_USER, null);

        PlayerViewModel.Factory factory = new PlayerViewModel.Factory(getApplication(), user);
        viewModel = ViewModelProviders.of(this, factory).get(PlayerViewModel.class);
        viewModel.getPlayer().observe(this, reservaEntity -> {
            if (reservaEntity != null) {
                playerEntity = reservaEntity;
                updateContent();
            }
        });

        tvEmail = findViewById(R.id.tv_email);


    }

    private void updateContent(){
        if (playerEntity != null){
            //Set Username on TextView
            tvEmail.setText(playerEntity.getEmail());
            tvEmail.getText().toString();
        }
    }
}