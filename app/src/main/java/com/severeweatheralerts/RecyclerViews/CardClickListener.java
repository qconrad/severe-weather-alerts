package com.severeweatheralerts.RecyclerViews;

import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.Alerts.Alert;

public interface CardClickListener {
  void onCardClick(Alert alert, RecyclerView.ViewHolder holder);
}
