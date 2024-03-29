package com.example.tennisapplication.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.tennisapplication.R;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity{

    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedIn";

    /**
     * Static variable for selected item position. Which can be used in child activity to know which item is selected from the list.
     */
    protected static int position;

    /**
     * Method that redirect the user to the Main Activity.
     *
     * trigger : logout button.
     */
    public void logout() {
        SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
        editor.remove(BaseActivity.PREFS_USER);
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
