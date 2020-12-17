package com.severeweatheralerts.AlertListRecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.severeweatheralerts.Alert;
import com.severeweatheralerts.R;

import java.util.List;

public class AlertRecyclerViewAdapter extends RecyclerView.Adapter<AlertCardHolder> {
  private List<Alert> alertItemList;
  private AlertCardClickedListener clickListener;

  public void setClickListener(AlertCardClickedListener clickListener) {
    this.clickListener = clickListener;
  }

  public AlertRecyclerViewAdapter(List<Alert> alertItemList) {
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
    holder.title.setText(alertItemList.get(position).getName());
//    holder.alertIcon.setImageResource(alertItemList.get(position).getIcon());

    holder.card.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (clickListener != null) clickListener.onAlertCardClicked(position);
      }
    });
  }

  @Override
  public int getItemCount() {
    return alertItemList.size();
  }
}
