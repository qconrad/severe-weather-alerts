package com.severeweatheralerts.RecyclerViews.Alert;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.severeweatheralerts.R;

public class AlertCardHolder extends ViewHolder {
  public CardView card;
  public TextView title;
  public ImageView icon;

  public AlertCardHolder(View view) {
    super(view);
    card = view.findViewById(R.id.alert_card);
    title = view.findViewById(R.id.card_title);
    icon = view.findViewById(R.id.card_icon);
  }
}
