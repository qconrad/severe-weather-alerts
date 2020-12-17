package com.severeweatheralerts.AlertListRecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.severeweatheralerts.R;

public class AlertCardHolder extends ViewHolder {
  LinearLayout card;
  TextView title;
  //ImageView icon;

  public AlertCardHolder(View view) {
    super(view);
    card = view.findViewById(R.id.card_background);
    title = view.findViewById(R.id.card_title);
    //icon = view.findViewById(R.id.card_icon);
  }
}
