package com.severeweatheralerts;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AlertRecyclerViewAdapter extends RecyclerView.Adapter<AlertRecyclerViewAdapter.AlertHolder> {
  private List<Alert> alertItemList;
  private AlertCardClickedListener clickListener;

  public void setClickListener(AlertCardClickedListener clickListener) {
    this.clickListener = clickListener;
  }

  public AlertRecyclerViewAdapter(List<Alert> alertItemList) {
    this.alertItemList = alertItemList;
  }

  @Override
  public AlertHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View alertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_card, parent, false);
    return new AlertHolder(alertView);
  }

  @Override
  public void onBindViewHolder(final AlertHolder holder, final int position) {
    holder.title.setText(alertItemList.get(position).getName());
//    holder.alertIcon.setImageResource(alertItemList.get(position).getIcon());

    holder.card.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (clickListener != null)
          clickListener.onAlertCardClicked(position);
      }
    });
  }

  @Override
  public int getItemCount() {
    return alertItemList.size();
  }

  public static class AlertHolder extends RecyclerView.ViewHolder {
    LinearLayout card;
    TextView title;
    //ImageView icon;

    public AlertHolder(View view) {
      super(view);
      card = view.findViewById(R.id.card_background);
      title = view.findViewById(R.id.card_title);
      //icon = view.findViewById(R.id.card_icon);
    }
  }
}
