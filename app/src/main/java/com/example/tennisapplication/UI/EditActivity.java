package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tennisapplication.R;
import com.example.tennisapplication.database.async.player.CreatePlayer;
import com.example.tennisapplication.database.async.player.DeletePlayer;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.util.OnAsyncEventListener;
import com.example.tennisapplication.viewModel.player.PlayerViewModel;
import com.google.android.material.button.MaterialButton;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "AccountDetailActivity";

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etAge;
    private EditText etPhone;
    private EditText etPwd1;
    private EditText etPwd2;
    private PlayerViewModel playerViewModel;

    private PlayerEntity player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Call initialisation method that will initialize all the fields
        initializeForm();

        // Create menu Button
        ImageView menuBtn = (ImageView) findViewById(R.id.menubutton);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuActivity();
            }
        });

        // Create account Button
        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountActivity();
            }
        });
    }

    private void initializeForm() {
        etFirstName = findViewById(R.id.tv_firstname);
        etLastName = findViewById(R.id.tv_lastname);
        etEmail = findViewById(R.id.tv_email);
        etAge = findViewById(R.id.tv_age);
        etPhone = findViewById(R.id.tv_phone);
        etPwd1 = findViewById(R.id.tv_password1);
        etPwd2 = findViewById(R.id.tv_password2);


        Button saveBtn = findViewById(R.id.button_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges(
                        etFirstName.getText().toString(),
                        etLastName.getText().toString(),
                        etEmail.getText().toString(),
                        etAge.getText().toString(),
                        etPhone.getText().toString(),
                        etPwd1.getText().toString(),
                        etPwd2.getText().toString()
                );
            }
        });

        saveBtn.setOnClickListener(view -> saveChanges(
                etFirstName.getText().toString(),
                etLastName.getText().toString(),
                etEmail.getText().toString(),
                etAge.getText().toString(),
                etPhone.getText().toString(),
                etPwd1.getText().toString(),
                etPwd2.getText().toString()
        ));
    }



    private void saveChanges(String firstName, String lastName, String email, String age, String phone ,String pwd, String pwd2) {
        if (!pwd.equals(pwd2)) {
            etPwd1.setError(getString(R.string.error_invalid_password));
            etPwd1.requestFocus();
            etPwd1.setText("");
            etPwd2.setText("");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            return;
        }

        PlayerEntity newClient = new PlayerEntity(email,pwd,firstName,lastName,age,phone,"player",0,0);

        new CreatePlayer(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                //Log.d(TAG, "createUserWithEmail: success");
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                //Log.d(TAG, "createUserWithEmail: failure", e);
                setResponse(false);
            }
        }).execute(newClient);
    }

    private void setResponse(Boolean response) {
        if (response) {
            final SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
            editor.putString(BaseActivity.PREFS_USER, etEmail.getText().toString());
            editor.apply();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            //etEmail.setError(getString(R.string.error_used_email));
            etEmail.requestFocus();
        }
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

    /**
     * Method that redirect the user to the Account Activity.
     *
     * trigger : account Button situated on the toolbar
     */
    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}