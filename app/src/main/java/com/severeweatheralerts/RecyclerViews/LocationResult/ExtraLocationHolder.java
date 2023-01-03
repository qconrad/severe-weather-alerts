package com.severeweatheralerts.RecyclerViews.LocationResult;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.R;

public class ExtraLocationHolder extends RecyclerView.ViewHolder {
  public ConstraintLayout card;
  public TextView locationName;
  public ImageView notificationStateIcon;
  public TextView notificationState;

  public ExtraLocationHolder(View view) {
    super(view);
    card = view.findViewById(R.id.extra_location_card);
    locationName = view.findViewById(R.id.extra_location_name);
    notificationStateIcon = view.findViewById(R.id.notification_state);
    notificationState = view.findViewById(R.id.notification_state_text);
  }
}