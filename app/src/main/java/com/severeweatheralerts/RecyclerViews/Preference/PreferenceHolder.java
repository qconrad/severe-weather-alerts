package com.severeweatheralerts.RecyclerViews.Preference;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.severeweatheralerts.R;

public class PreferenceHolder extends ViewHolder {
  public TextView alertName;
  public Button post;
  public Button update;
  public Button cancel;

  public PreferenceHolder(View view) {
    super(view);
    alertName = view.findViewById(R.id.alert_pref_text);
    post = view.findViewById(R.id.post_button);
    update = view.findViewById(R.id.update_button);
    cancel = view.findViewById(R.id.cancel_button);
  }
}
