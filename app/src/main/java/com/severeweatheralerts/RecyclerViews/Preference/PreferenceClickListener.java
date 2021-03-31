package com.severeweatheralerts.RecyclerViews.Preference;

import com.severeweatheralerts.Alerts.Alert;

public interface PreferenceClickListener {
  void onClick(Alert.Type type, int index);
}
