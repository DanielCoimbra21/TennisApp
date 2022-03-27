package com.example.tennisapplication.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tennisapplication.BaseApp;
import com.example.tennisapplication.R;
import com.example.tennisapplication.database.async.reservation.CreateReservation;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.database.repository.ReservationRepository;
import com.example.tennisapplication.util.OnAsyncEventListener;
import com.example.tennisapplication.viewModel.player.PlayerViewModel;
import com.example.tennisapplication.viewModel.reservation.ReservationListViewModel;
import com.example.tennisapplication.viewModel.reservation.ReservationViewModel;
import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;

public class ReservationDetailsActivity extends BaseActivity {

    private static final String TAG = "ReservationDetailsActivity";
    private static final int EDIT_RESERVATION= 1;
    private ReservationEntity reservation;
    private ReservationRepository reservationRepository;
    private NumberFormat defaultFormat;
    private ReservationViewModel reservationViewModel;
    private String curDate;
    private int hour;
    private int court;
    private PlayerEntity playerEntity;
    private PlayerViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);

        reservationRepository = ((BaseApp) getApplication()).getReservationRepository();

        // get les informations précédemment entrées
        Intent intent = getIntent();
        curDate = intent.getStringExtra("date");
        hour = Integer.valueOf(intent.getStringExtra("schedule"));
        court = intent.getIntExtra("courtNumber", -1);

        // Initialisation de la page
        // infos des pages précédentes
        TextView date = (TextView) findViewById(R.id.reservationDate);
        date.setText(curDate);
        TextView courtnb = (TextView) findViewById(R.id.reservationCourt);
        courtnb.setText(String.valueOf(court));
        TextView heureDebut = (TextView) findViewById(R.id.reservationFrom);
        heureDebut.setText(String.format("%02d",hour)+"h00");
        TextView heureFin = (TextView) findViewById(R.id.reservationTo);
        heureFin.setText(String.format("%02d",(hour+1))+"h00");

        // set le textView du nom Prénom de l'utilisateur actuel
        TextView name = (TextView) findViewById(R.id.reservationName);
        TextView surname = (TextView) findViewById(R.id.reservationSurname);
        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME,0);
        String user = settings.getString(BaseActivity.PREFS_USER, null);
        PlayerViewModel.Factory factory = new PlayerViewModel.Factory(getApplication(), user);
        viewModel = ViewModelProviders.of(this, factory).get(PlayerViewModel.class);
        viewModel.getPlayer().observe(this, playerEntity -> {
            if (playerEntity != null) {
                this.playerEntity = playerEntity;
                if (playerEntity != null){
                    //Set Username on TextView
                    name.setText(playerEntity.getLastName().toUpperCase());
                    name.getText().toString();
                    surname.setText(playerEntity.getFirstName());
                    surname.getText().toString();
                }
            }
        });


        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountActivity();
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

    }

    private void openMenuActivity(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }




        /*ReservationViewModel.Factory factory = new ReservationViewModel.Factory(
                getApplication(), reservationId);
        viewModel = ViewModelProviders.of(this, factory).get(ReservationViewModel.class);
        viewModel.getReservation().observe(this, reservationEntity -> {
            if (reservationEntity != null) {
                reservation = reservationEntity;
                updateContent();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, EDIT_RESERVATION, Menu.NONE, getString(R.string.title_activity_edit_account))
                .setIcon(R.drawable.ic_edit_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_RESERVATION) {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("reservationId", reservation.getIdReservation());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initiateView() {
        tvBalance = findViewById(R.id.accBalance);
        defaultFormat = NumberFormat.getCurrencyInstance();

        Button depositBtn = findViewById(R.id.depositButton);
        depositBtn.setOnClickListener(view -> generateDialog(R.string.action_deposit));

        Button withdrawBtn = findViewById(R.id.withdrawButton);
        withdrawBtn.setOnClickListener(view -> generateDialog(R.string.action_withdraw));
    }

    private void updateContent() {
        if (reservation != null) {
            setTitle(account.getName());
            tvBalance.setText(defaultFormat.format(account.getBalance()));
            Log.i(TAG, "Activity populated.");
        }
    }

    private void generateDialog(final int action) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.account_actions, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(action));
        alertDialog.setCancelable(false);


        final EditText accountMovement = view.findViewById(R.id.account_movement);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Double amount = Double.parseDouble(accountMovement.getText().toString());
                Toast toast = Toast.makeText(AccountDetailActivity.this, getString(R.string.error_withdraw), Toast.LENGTH_LONG);

                if (action == R.string.action_withdraw) {
                    if (account.getBalance() < amount) {
                        toast.show();
                        return;
                    }
                    Log.i(TAG, "Withdrawal: " + amount.toString());
                    account.setBalance(account.getBalance() - amount);
                }
                if (action == R.string.action_deposit) {
                    Log.i(TAG, "Deposit: " + amount.toString());
                    account.setBalance(account.getBalance() + amount);
                }
                viewModel.updateAccount(account, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "updateAccount: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "updateAccount: failure", e);
                    }
                });
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel),
                (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();*/
    }
