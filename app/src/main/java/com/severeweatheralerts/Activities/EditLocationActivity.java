package com.severeweatheralerts.Activities;

import static com.severeweatheralerts.FileDB.getLocationsDao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Preferences.ChannelPreferences;
import com.severeweatheralerts.R;

public class EditLocationActivity extends AppCompatActivity {
  private Location location;
  private int locationIndex;
  private boolean deleted;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_location);
    locationIndex = getIntent().getExtras().getInt("locationIndex", 0);
    location = getLocationsDao(this).getLocation(locationIndex);
    setUI();
  }

  private void setUI() {
    setNameTextbox();
    setNotifySwitchClickability();
    setNotifyMethodClickability();
    setEditCustomClickability();
  }

  private void setNotifySwitchClickability() {
    SwitchCompat notifySwitch = findViewById(R.id.notify_switch);
    notifySwitch.setChecked(location.isNotifying());
  }

  private void setNotifyMethodClickability() {
    RadioButton useDefault = findViewById(R.id.use_default);
    RadioButton setCustom = findViewById(R.id.set_custom);
    useDefault.setChecked(location.getChannelPreferences() == null);
    setCustom.setChecked(location.getChannelPreferences() != null);
    useDefault.setEnabled(location.isNotifying());
    setCustom.setEnabled(location.isNotifying());
  }

  private void setEditCustomClickability() {
    Button editCustom = findViewById(R.id.edit_custom_btn);
    editCustom.setEnabled(location.isNotifying() && location.getChannelPreferences() != null);
  }

  private void setNameTextbox() {
    TextView locationNameTextView = findViewById(R.id.edit_location_name);
    locationNameTextView.setText(location.getName());
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (!deleted) getLocationsDao(this).setLocation(locationIndex, location);
  }

  @Override
  public void onBackPressed() {
    setResult(Activity.RESULT_OK, new Intent());
    finish();
  }

  ActivityResultLauncher<Intent> locationSelectorResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
          result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
              Intent data = result.getData();
              location.setName(data.getStringExtra("name"));
              location.setCoordinate(new GCSCoordinate(data.getDoubleExtra("lat", 0.0), data.getDoubleExtra("lon", 0.0)));
              setNameTextbox();
            }
          });

  public void setPositionName(View view) {
    locationSelectorResult.launch(new Intent(EditLocationActivity.this, LocationPickerActivity.class));
  }

  public void deleteClick(View view) {
    AlertDialog alertDialog = new AlertDialog.Builder(EditLocationActivity.this).create();
    alertDialog.setTitle("Delete Confirmation");
    alertDialog.setMessage("Are you sure you want to permanently delete this location and all its preferences? This cannot be undone.");
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", (dialog, which) -> deleteLocation());
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", (dialog, which) -> dialog.dismiss());
    alertDialog.show();
  }

  private void deleteLocation() {
    getLocationsDao(this).deleteLocation(locationIndex);
    deleted = true;
    finish();
  }

  public void notifyToggle(View view) {
    SwitchCompat notifySwitch = findViewById(R.id.notify_switch);
    location.setNotify(notifySwitch.isChecked());
    setNotifyMethodClickability();
    setEditCustomClickability();
  }

  public void useCustomPreferences(View view) {
    location.setChannelPreferences(new ChannelPreferences());
    setEditCustomClickability();
  }

  public void useDefaultPreferences(View view) {
    location.setChannelPreferences(null);
    setEditCustomClickability();
  }

  public void editCustomClick(View view) {
    Intent intent = new Intent(EditLocationActivity.this, ChannelPreferencesActivity.class);
    intent.putExtra("locationIndex", locationIndex);
    startActivity(intent);
  }
}