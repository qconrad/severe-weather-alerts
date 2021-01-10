package com.severeweatheralerts.RecyclerViews;

import androidx.recyclerview.widget.RecyclerView;

public interface CardClickListener {
  void onCardClick(int position, RecyclerView.ViewHolder holder);
}
