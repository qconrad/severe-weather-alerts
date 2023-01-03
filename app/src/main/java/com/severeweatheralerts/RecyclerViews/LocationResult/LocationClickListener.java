package com.severeweatheralerts.RecyclerViews.LocationResult;

import androidx.recyclerview.widget.RecyclerView;

public interface LocationClickListener {
  void onLocationClick(int locationIndex, RecyclerView.ViewHolder holder);
}
