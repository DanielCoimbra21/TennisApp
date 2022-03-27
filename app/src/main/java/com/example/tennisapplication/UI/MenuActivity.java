package com.example.tennisapplication.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tennisapplication.R;
import com.example.tennisapplication.sessions.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sessionManager = new SessionManager(getApplicationContext());

        ListView listView = findViewById(R.id.listview);

        List<String> list = new ArrayList<>();
        list.add(getString(R.string.menu_list_account)); // p0
        list.add(getString(R.string.menu_list_menu)); // p1
        list.add(getString(R.string.menu_list_about)); // p2
        list.add(getString(R.string.menu_list_settings)); // p3
        list.add(getString(R.string.menu_list_logout)); // p4





        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    openAccountActivity();
                }else if(position==1){
                    openMainMenuActivity();
                }else if(position==2){
                    openAboutActivity();
                }else if(position==3){
                    openSettingsActivity();
                }else if(position==4){
                    //clicked SignOut
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Log out");
                    //set Login false
                    sessionManager.setLogin(false);
                    //set username (mail) empty
                    sessionManager.setUsername("");
                    BaseActivity b = new BaseActivity();
                    b.logout();
                }
            }
        });
    }

    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    private void openMainMenuActivity(){
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    private void openAboutActivity(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void openSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}