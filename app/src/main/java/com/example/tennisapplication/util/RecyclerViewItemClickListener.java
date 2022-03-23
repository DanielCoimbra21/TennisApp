package com.example.tennisapplication.util;

import android.view.View;

public interface RecyclerViewItemClickListener {
    void onItemClick(View v, int position);
    void onItemLongClick(View v, int position);
}
