package com.example.tennisapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tennisapplication.R;
import com.example.tennisapplication.database.entity.PlayerEntity;
import com.example.tennisapplication.database.entity.ReservationEntity;
import com.example.tennisapplication.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private List<T> mData;
    private RecyclerViewItemClickListener mListener;


    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextView;
        ViewHolder(TextView textView) {
            super(textView);
            mTextView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        T item = mData.get(position);
        if (item.getClass().equals(ReservationEntity.class))
            holder.mTextView.setText(((ReservationEntity) item).getIdReservation().toString()+" " + ((ReservationEntity) item).getSchedule());
        if (item.getClass().equals(PlayerEntity.class))
            holder.mTextView.setText(((PlayerEntity) item).getFirstName() + " " + ((PlayerEntity) item).getLastName());
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<T> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof ReservationEntity) {
                        return ((ReservationEntity) mData.get(oldItemPosition)).getIdReservation().equals(((ReservationEntity) data.get(newItemPosition)).getIdReservation());
                    }
                    if (mData instanceof PlayerEntity) {
                        return ((PlayerEntity) mData.get(oldItemPosition)).getEmail().equals(
                                ((PlayerEntity) data.get(newItemPosition)).getEmail());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof ReservationEntity) {
                        ReservationEntity newReservation = (ReservationEntity) data.get(newItemPosition);
                        ReservationEntity oldReservation = (ReservationEntity) mData.get(newItemPosition);
                        return newReservation.getIdReservation().equals(oldReservation.getIdReservation())
                                && Objects.equals(newReservation.getDate(), oldReservation.getDate())
                                && Objects.equals(newReservation.getCourtNumber(), oldReservation.getCourtNumber())
                                && Objects.equals(newReservation.getSchedule(), oldReservation.getSchedule())
                                && Objects.equals(newReservation.getPlayerEmail(), oldReservation.getPlayerEmail());
                    }
                    if (mData instanceof PlayerEntity) {
                        PlayerEntity newClient = (PlayerEntity) data.get(newItemPosition);
                        PlayerEntity oldClient = (PlayerEntity) mData.get(newItemPosition);
                        return Objects.equals(newClient.getEmail(), oldClient.getEmail())
                                && Objects.equals(newClient.getFirstName(), oldClient.getFirstName())
                                && Objects.equals(newClient.getLastName(), oldClient.getLastName())
                                && newClient.getPassword().equals(oldClient.getPassword());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
