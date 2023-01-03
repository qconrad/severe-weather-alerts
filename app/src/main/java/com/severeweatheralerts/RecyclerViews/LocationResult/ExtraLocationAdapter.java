package com.severeweatheralerts.RecyclerViews.LocationResult;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.R;

import java.util.ArrayList;

public class ExtraLocationAdapter extends RecyclerView.Adapter<ExtraLocationHolder> {
  private final ArrayList<Location> locations;
  private LocationClickListener locationClickListener;

  public ExtraLocationAdapter(ArrayList<Location> locations) {
    this.locations = locations;
  }

  public void setOnLocationClickListener(LocationClickListener locationClickListener) {
    this.locationClickListener = locationClickListener;
  }

  @NonNull
  @Override
  public ExtraLocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View locationResultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_card, parent, false);
    return new ExtraLocationHolder(locationResultView);
  }

  @Override
  public void onBindViewHolder(@NonNull ExtraLocationHolder holder, int position) {
    Location location = locations.get(position);
    holder.locationName.setText(location.getName());

    // Set spacing in between cards
    if (position != 0) addTopSpacing(holder, 20);

    holder.notificationState.setText(getNotificationState(location));
    holder.notificationStateIcon.setImageResource(getNotificationStateIcon(location));
    if (locationClickListener != null) holder.card.setOnClickListener(v -> locationClickListener.onLocationClick(position, holder));
  }

  private void addTopSpacing(ExtraLocationHolder holder, int spacingPixels) {
    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.card.getLayoutParams();
    params.topMargin = spacingPixels;
    holder.card.setLayoutParams(params);
  }

  private int getNotificationStateIcon(Location location) {
    if (location.isNotifying()) {
      if (location.getChannelPreferences() == null) return R.drawable.ic_baseline_notifications_active_24;
      else return R.drawable.ic_settings; // gear icon for custom notifications
    }
    return R.drawable.ic_baseline_notifications_off_24;
  }

  private String getNotificationState(Location location) {
    if (location.isNotifying()) {
      if (location.getChannelPreferences() == null)
        return "Default location notifications";
      return "Custom notifications";
    }
    return "Notifications disabled";
  }

  @Override
  public int getItemCount() {
    return locations.size();
  }
}
