package com.example.tennisapplication.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
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

import java.util.ArrayList;
import java.util.List;

public class ReservationsActivity extends AppCompatActivity {

    private static final String TAG = "ReservationsActivity";

    private List<ReservationEntity> reservations;
    private RecyclerAdapter<ReservationEntity> adapter;
    private ReservationListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        MaterialButton toolbarButton = (MaterialButton) findViewById(R.id.toolbaraccountbutton);

        RecyclerView recyclerView = findViewById(R.id.reservationsRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        SharedPreferences settings = getSharedPreferences(SessionManager.PREFS_NAME, 0);
        String user = settings.getString(SessionManager.PREFS_USER, null);

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
                intent.putExtra("reservationId", reservations.get(position).getIdReservation());
                intent.putExtra("date", reservations.get(position).getDate());
                intent.putExtra("schedule", reservations.get(position).getSchedule());
                intent.putExtra("playerId", reservations.get(position).getPlayerEmail());
                intent.putExtra("courtNumber", reservations.get(position).getCourtNumber());
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + reservations.get(position).getIdReservation());

                createDeleteDialog(position);
            }
        });

        ReservationListViewModel.Factory factory = new ReservationListViewModel.Factory(getApplication(), user);

        viewModel = ViewModelProviders.of(this, factory).get(ReservationListViewModel.class);
        viewModel.getPlayerReservation().observe(this, reservationEntities -> {
            if (reservationEntities != null){
                reservations = reservationEntities;
                adapter.setData(reservations);
            }
        });

        recyclerView.setAdapter(adapter);

        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountActivity();
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

    private void createDeleteDialog(final int position) {
        final ReservationEntity reservationEntity = reservations.get(position);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.row_delete_item, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.title_activity_delete_reservation));
        alertDialog.setCancelable(false);

        final TextView deleteMessage = view.findViewById(R.id.tv_delete_item);
        deleteMessage.setText(String.format(getString(R.string.reservation_delete_msg), reservationEntity.getPlayerEmail()));

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