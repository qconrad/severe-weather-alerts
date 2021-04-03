package com.severeweatheralerts.RecyclerViews.Preference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Preferences.Channel;
import com.severeweatheralerts.Preferences.ChannelPreferences;
import com.severeweatheralerts.R;

import static com.severeweatheralerts.Preferences.ChannelColors.getChannelColor;

public class PreferenceAdapter extends RecyclerView.Adapter<PreferenceHolder> {
  private final String[] alertList;
  private final ChannelPreferences channelPreferences;
  private PreferenceClickListener clickListener;

  public void setClickListener(PreferenceClickListener clickListener) {
    this.clickListener = clickListener;
  }

  public PreferenceAdapter(String[] alertList, ChannelPreferences channelPreferences) {
    this.alertList = alertList;
    this.channelPreferences = channelPreferences;
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
    Channel postChannel   = channelPreferences.getChannel(alertList[position], Alert.Type.POST);
    Channel updateChannel = channelPreferences.getChannel(alertList[position], Alert.Type.UPDATE);
    Channel cancelChannel = channelPreferences.getChannel(alertList[position], Alert.Type.CANCEL);
    holder.post.setText(postChannel.toString());
    holder.post.setBackgroundColor(getChannelColor(postChannel));
    holder.update.setText(updateChannel.toString());
    holder.update.setBackgroundColor(getChannelColor(updateChannel));
    holder.cancel.setText(cancelChannel.toString());
    holder.cancel.setBackgroundColor(getChannelColor(cancelChannel));
    holder.post.setOnClickListener(v   -> { if (clickListener != null) clickListener.onClick(Alert.Type.POST, position); });
    holder.update.setOnClickListener(v -> { if (clickListener != null) clickListener.onClick(Alert.Type.UPDATE, position); });
    holder.cancel.setOnClickListener(v -> { if (clickListener != null) clickListener.onClick(Alert.Type.CANCEL, position); });
  }

  @Override
  public int getItemCount() {
    return alertList.length;
  }
}
