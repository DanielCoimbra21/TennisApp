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
        list.add("Account");
        list.add("sign-Out");
        list.add("Banana");
        list.add("Theo I LUV U");




        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    //clicked Account

                    startActivity(new Intent(MenuActivity.this, AccountActivity.class));

                }else if(position==1){
                    //clicked SignOut
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    //
                    builder.setTitle("Log out");
                    //set Login false
                    sessionManager.setLogin(false);
                    //set username (mail) empty
                    sessionManager.setUsername("");


                    startActivity(new Intent(MenuActivity.this, MainActivity.class));
                }else{

                }
            }
        });
    }
}