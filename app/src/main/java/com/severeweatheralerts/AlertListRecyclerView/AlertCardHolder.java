package com.severeweatheralerts.AlertListRecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.severeweatheralerts.R;

public class AlertCardHolder extends ViewHolder {
  CardView card;
  TextView title;
  ImageView icon;

  public AlertCardHolder(View view) {
    super(view);
    card = view.findViewById(R.id.card);
    title = view.findViewById(R.id.card_title);
    icon = view.findViewById(R.id.card_icon);
  }
}
