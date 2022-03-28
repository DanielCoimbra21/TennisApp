package com.example.tennisapplication.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

    /**
     * Initialisation method of the Menu Activity
     *
     * @param savedInstanceState with the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Initialize the session Manager
        sessionManager = new SessionManager(getApplicationContext());

        // Initialize the menu list
        ListView listView = findViewById(R.id.listview);
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.menu_list_account)); // p0
        list.add(getString(R.string.menu_list_menu)); // p1
        list.add(getString(R.string.menu_list_about)); // p2
        list.add(getString(R.string.menu_list_settings)); // p3
        list.add(getString(R.string.menu_list_logout)); // p4

        // initialize the list that will be show on the UI
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

        // set onClickListener on the list. It will do something depending on which item you clicked
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
                    SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
                    editor.remove(BaseActivity.PREFS_USER);
                    editor.apply();

                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
            }
        });
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
     * Method that redirect the user to the Home Page Activity.
     *
     * trigger : user clicks on the "Menu" button of the list.
     */
    private void openMainMenuActivity(){
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    /**
     * Method that redirect the user to the About Activity.
     *
     * trigger : user clicks on the "About" button of the list.
     */
    private void openAboutActivity(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    /**
     * Method that redirect the user to the Settings Activity.
     *
     * trigger : user clicks on the "Settings" button of the list.
     */
    private void openSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}