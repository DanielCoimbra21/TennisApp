package com.example.tennisapplication.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tennisapplication.R;
import com.example.tennisapplication.adapter.RecyclerAdapter;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.sessions.SessionManager;
import com.example.tennisapplication.util.OnAsyncEventListener;
import com.example.tennisapplication.util.RecyclerViewItemClickListener;
import com.example.tennisapplication.viewModel.reservation.ReservationListViewModel;
import com.example.tennisapplication.viewModel.reservation.ReservationViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ReservationsActivity extends AppCompatActivity {

    private static final String TAG = "ReservationsActivity";
    private List<ReservationEntity> reservations;
    private RecyclerAdapter<ReservationEntity> adapter;
    private ReservationListViewModel viewModel;

    /**
     * Initialisation method of the Reservation Activity
     *
     * @param savedInstanceState with the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        // Class initialisation
        RecyclerView recyclerView = findViewById(R.id.reservationsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        // On item Click, show reservation details
        // On Item Long Click, delete reservation
        reservations = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on reservation n: " + reservations.get(position).getIdReservation());

                Intent intent = new Intent(ReservationsActivity.this, ReservationDetailsActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("date", reservations.get(position).getDate());
                intent.putExtra("schedule", reservations.get(position).getSchedule());
                intent.putExtra("courtNumber", reservations.get(position).getCourtNum());
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + reservations.get(position).getIdReservation());
                createDeleteDialog(position);
            }
        });

        // will get and show the reservations
        ReservationListViewModel.Factory factory = new ReservationListViewModel.Factory(getApplication(), FirebaseAuth.getInstance().getCurrentUser().getUid());
        viewModel = new ViewModelProvider(this, factory).get(ReservationListViewModel.class);
        viewModel.getPlayerReservation().observe(this, reservationEntities -> {
            if (reservationEntities != null){
                reservations = reservationEntities;
                adapter.setData(reservations);
            }
        });
        recyclerView.setAdapter(adapter);

        // Creation of the Account button
        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountActivity();
            }
        });
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

    /**
     * Method that will call a pupup message to prevent missclicks.
     *
     * @param position the int position on the reservation list
     */
    private void createDeleteDialog(final int position) {
        final ReservationEntity reservationEntity = reservations.get(position);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.row_delete_item, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.title_activity_delete_reservation));
        alertDialog.setCancelable(false);

        final TextView deleteMessage = view.findViewById(R.id.tv_delete_item);
        deleteMessage.setText(getString(R.string.reservation_delete_msg));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_accept), (dialog, which) -> {
            Toast toast = Toast.makeText(this, getString(R.string.reservation_deleted), Toast.LENGTH_LONG);
            viewModel.deleteReservation(reservationEntity, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "deleteAccount: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "deleteAccount: failure", e);
                }
            });
            toast.show();
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }
}