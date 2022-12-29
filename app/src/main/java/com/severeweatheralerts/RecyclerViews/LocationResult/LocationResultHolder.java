package com.severeweatheralerts.RecyclerViews.LocationResult;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.R;

public class LocationResultHolder extends RecyclerView.ViewHolder {
  public ConstraintLayout card;
  public TextView address;
  public TextView coordinates;

  public LocationResultHolder(View view) {
    super(view);
    card = view.findViewById(R.id.address_search_result);
    address = view.findViewById(R.id.address_result_text);
    coordinates = view.findViewById(R.id.address_result_coordinate);
  }
}