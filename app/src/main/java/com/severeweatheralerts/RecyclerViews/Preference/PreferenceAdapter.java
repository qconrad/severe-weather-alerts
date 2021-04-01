package com.severeweatheralerts.RecyclerViews.Preference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Preferences.ChannelPreferences;
import com.severeweatheralerts.R;

public class PreferenceAdapter extends RecyclerView.Adapter<PreferenceHolder> {
  private final String[] alertList;
  private final ChannelPreferences channelPreferences;
  private final int locationIndex;
  private PreferenceClickListener clickListener;

  public void setClickListener(PreferenceClickListener clickListener) {
    this.clickListener = clickListener;
  }

  public PreferenceAdapter(String[] alertList, ChannelPreferences channelPreferences, int locationIndex) {
    this.alertList = alertList;
    this.channelPreferences = channelPreferences;
    this.locationIndex = locationIndex;
  }

  @NonNull
  @Override
  public PreferenceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View preferenceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_preference, parent, false);
    return new PreferenceHolder(preferenceView);
  }

  @Override
  public void onBindViewHolder(final PreferenceHolder holder, final int position) {
    holder.alertName.setText(alertList[position]);
    holder.post.setText(channelPreferences.getChannel(locationIndex, Alert.Type.POST, alertList[position]).toString());
    holder.update.setText(channelPreferences.getChannel(locationIndex, Alert.Type.UPDATE, alertList[position]).toString());
    holder.cancel.setText(channelPreferences.getChannel(locationIndex, Alert.Type.CANCEL, alertList[position]).toString());
    holder.post.setOnClickListener(v   -> { if (clickListener != null) clickListener.onClick(Alert.Type.POST, position); });
    holder.update.setOnClickListener(v -> { if (clickListener != null) clickListener.onClick(Alert.Type.UPDATE, position); });
    holder.cancel.setOnClickListener(v -> { if (clickListener != null) clickListener.onClick(Alert.Type.CANCEL, position); });
  }

  @Override
  public int getItemCount() {
    return alertList.length;
  }
}
