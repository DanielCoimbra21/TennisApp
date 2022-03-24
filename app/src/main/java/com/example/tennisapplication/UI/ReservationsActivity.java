package com.example.tennisapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.tennisapplication.R;
import com.example.tennisapplication.adapter.RecyclerAdapter;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.sessions.SessionManager;
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
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(View v, int position) {

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

    private void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }


}