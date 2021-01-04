package com.severeweatheralerts.AlertListRecyclerView;

import androidx.annotation.NonNull;
import androidx.core.widget.AutoSizeableTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    Alert curAlert = alertItemList.get(position);
    holder.title.setText(curAlert.getName());

    float fiftyDP = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, Resources.getSystem().getDisplayMetrics());
    GradientDrawable gd = new GradientDrawable();
    gd.setCornerRadius(fiftyDP);
    gd.setColor(curAlert.getColor());
//
    holder.card.setBackground(gd);

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
