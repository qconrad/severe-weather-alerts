package com.severeweatheralerts.RecyclerViews.Alert;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.CardClickListener;

import java.util.ArrayList;
import java.util.Date;

public class AlertRecyclerViewAdapter extends RecyclerView.Adapter<AlertCardHolder> {
  private final ArrayList<Alert> alertItemList;
  private CardClickListener clickListener;

  public void setClickListener(CardClickListener clickListener) {
    this.clickListener = clickListener;
  }

  public AlertRecyclerViewAdapter(ArrayList<Alert> alertItemList) {
    this.alertItemList = alertItemList;
  }

  @NonNull
  @Override
  public AlertCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View alertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_card, parent, false);
    return new AlertCardHolder(alertView);
  }

  @Override
  public void onBindViewHolder(final AlertCardHolder holder, final int position) {
    Alert curAlert = alertItemList.get(position);
    holder.title.setText(curAlert.getName());
    holder.icon.setImageResource(curAlert.getIcon());
    holder.card.setCardBackgroundColor(curAlert.getColorAt(new Date()));

    holder.card.setOnClickListener(v -> {
      if (clickListener != null) clickListener.onCardClick(position, holder);
    });
  }

  @Override
  public int getItemCount() {
    return alertItemList.size();
  }
}
