package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.R;
import com.example.tennisapplication.database.async.player.CreatePlayer;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.util.OnAsyncEventListener;
import com.google.android.material.button.MaterialButton;

public class NewAccountActivity extends AppCompatActivity {

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etAge;
    private EditText etPhone;
    private EditText etPwd1;
    private EditText etPwd2;

    /**
     * Initialisation method of the New Account Activity
     *
     * @param savedInstanceState with the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        // Call the initialisation method of the activity
        initializeForm();
    }

    /**
     * Initialisation method of the New Account Activity
     *
     * trigger: onCreate Method
     */
    private void initializeForm() {
        // initialize the edit texts
        etFirstName = findViewById(R.id.tv_firstname);
        etLastName = findViewById(R.id.tv_lastname);
        etEmail = findViewById(R.id.tv_email);
        etAge = findViewById(R.id.tv_age);
        etPhone = findViewById(R.id.tv_phone);
        etPwd1 = findViewById(R.id.tv_password1);
        etPwd2 = findViewById(R.id.tv_password2);

        // Create the save button that will call the method saveChanges
        Button saveBtn = findViewById(R.id.button_register);
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
    }

    /**
     * Method that saves the new account to the database and verify that every field is correctly entered
     *
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param email the email of the user
     * @param age the age of the user
     * @param phone the phone of the user
     * @param pwd the password of the user
     * @param pwd2 the password confirmation of the user
     */
    private void saveChanges(String firstName, String lastName, String email, String age, String phone ,String pwd, String pwd2) {
        // check that the pwd and the email address are incorrect
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

        // if it is correct, then create a new client in the database
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

    /**
     * Method that set a response to the user in order to inform the failure/success of the database update
     *
     * @param response Boolean of the success/failure of the implementation
     */
    private void setResponse(Boolean response) {
        if (response) {
            final SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
            editor.putString(BaseActivity.PREFS_USER, etEmail.getText().toString());
            editor.apply();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            etEmail.requestFocus();
        }
    }
}