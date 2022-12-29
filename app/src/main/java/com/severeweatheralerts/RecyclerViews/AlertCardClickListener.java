package com.severeweatheralerts.RecyclerViews;

import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.Alerts.Alert;

public interface AlertCardClickListener {
  void onCardClick(Alert alert, RecyclerView.ViewHolder holder);
}
