package com.example.tennisapplication.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tennisapplication.R;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.viewModel.reservation.ReservationListViewModel;
import com.example.tennisapplication.viewModel.reservation.ReservationViewModel;

import java.text.NumberFormat;

public class ReservationDetailsActivity extends BaseActivity {

    /*private static final String TAG = "ReservationDetailsActivity";
    private static final int EDIT_RESERVATION= 1;

    private ReservationEntity reservation;

    private NumberFormat defaultFormat;

    private ReservationViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);

        int reservationId = getIntent().getIntExtra("reservationId", 0);

        initiateView();

        ReservationViewModel.Factory factory = new ReservationViewModel.Factory(
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
        menu.add(0, EDIT_ACCOUNT, Menu.NONE, getString(R.string.title_activity_edit_account))
                .setIcon(R.drawable.ic_edit_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_ACCOUNT) {
            Intent intent = new Intent(this, EditAccountActivity.class);
            intent.putExtra("accountId", account.getId());
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
        if (account != null) {
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
