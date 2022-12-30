package com.severeweatheralerts.Activities;

import static com.severeweatheralerts.FileDB.getLocationsDao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Preferences.ChannelPreferences;
import com.severeweatheralerts.Preferences.ChannelPreferencesDataHolder;
import com.severeweatheralerts.R;

public class EditLocationActivity extends AppCompatActivity {
  private Location locationRef;
  private int locationIndex;
  private RadioButton useDefault;
  private RadioButton setCustom;
  private SwitchCompat notifySwitch;

  private String locationName;
  ChannelPreferences channelPreferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_location);
    locationIndex = getIntent().getExtras().getInt("locationIndex", 0);
    locationRef = getLocationsDao(this).getLocation(locationIndex);
    locationName = locationRef.getName();
    notifySwitch = findViewById(R.id.notify_switch);
    useDefault = findViewById(R.id.use_default);
    setCustom = findViewById(R.id.set_custom);
    setUI(locationRef);
  }

  private void setUI(Location location) {
    setNameText(location.getName());
    setNotifyToggleSwitch(location.isNotifying());
    setNotifyMethodEnabled(location.isNotifying());
    setNotifyMethod(location.getChannelPreferences() == null);
    setEditCustomEnabled(location.isNotifying() && location.getChannelPreferences() != null);
  }

  private void setNotifyToggleSwitch(boolean isNotifying) {
    SwitchCompat notifySwitch = findViewById(R.id.notify_switch);
    notifySwitch.setChecked(isNotifying);
  }

  private void setNotifyMethodEnabled(boolean isNotifying) {
    useDefault.setEnabled(isNotifying);
    setCustom.setEnabled(isNotifying);
  }

  private void setNotifyMethod(boolean isUsingDefault) {
    useDefault.setChecked(isUsingDefault);
    setCustom.setChecked(!isUsingDefault);
  }

  private void setEditCustomEnabled(boolean isEnabled) {
    Button editCustom = findViewById(R.id.edit_custom_btn);
    editCustom.setEnabled(isEnabled);
  }

  private void setNameText(String name) {
    TextView locationNameTextView = findViewById(R.id.edit_activity_location_name);
    locationNameTextView.setText(name);
  }

  @Override
  public void onBackPressed() {
    setResult(Activity.RESULT_OK, new Intent());
    finish();
  }

  public void renameClick() {
    showRenamePopup();
  }

  public void deleteClick() {
    new AlertDialog.Builder(EditLocationActivity.this)
            .setTitle(getString(R.string.location_delete_confirmation_title))
            .setMessage(getString(R.string.location_delete_confirmation))
            .setPositiveButton("Yes", (dialog, which) -> deleteLocationAndClose())
            .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
            .show();
  }

  public void showRenamePopup() {
    final EditText input = new EditText(this);
    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
    input.setText(locationName);
    input.setFilters(new android.text.InputFilter[] { new android.text.InputFilter.LengthFilter(Constants.MAX_LOCATION_NAME_LENGTH) });
    input.requestFocus();

    new AlertDialog.Builder(this)
      .setTitle("Enter Location Name")
      .setView(input)
      .setPositiveButton("OK", (dialog, which) -> {
        TextView locationNameTextView = findViewById(R.id.edit_activity_location_name);
        locationNameTextView.setText(input.getText().toString());
        locationName = input.getText().toString();
      })
      .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
      .show();
  }

  private void deleteLocationAndClose() {
    getLocationsDao(this).deleteLocation(locationIndex);
    finish();
  }

  public void notifyToggle(View view) {
    notifySwitch = findViewById(R.id.notify_switch);
    setNotifyMethodEnabled(notifySwitch.isChecked());
    setEditCustomEnabled(notifySwitch.isChecked() && setCustom.isChecked());
  }

  public void useCustomPreferences(View view) {
    setEditCustomEnabled(true);
  }

  public void useDefaultPreferences(View view) {
    showCustomPreferenceDeletionConfirmation();
    setEditCustomEnabled(false);
  }

  private void showCustomPreferenceDeletionConfirmation() {
    new AlertDialog.Builder(this)
      .setTitle("Delete Custom Preferences")
      .setMessage("Are you sure you want to delete the custom preferences for this location? This cannot be undone.")
      .setPositiveButton("Yes", (dialog, which) -> dialog.dismiss())
      .setNegativeButton("No", (dialog, which) -> goBackToCustom(dialog))
      .show();
  }

  private void goBackToCustom(DialogInterface dialog) {
    setCustom.setChecked(true);
    setEditCustomEnabled(true);
    dialog.dismiss();
  }

  public void editCustomClick(View view) {
    Intent intent = new Intent(this, ChannelPreferencesActivity.class);

     if (channelPreferences != null) { // Cached but not yet saved preferences
       intent.putExtra("useDefault", false);
       ChannelPreferencesDataHolder.getInstance().setChannelPreferences(channelPreferences);
     }
     else if (getLocationsDao(this).getLocation(locationIndex).getChannelPreferences() != null) { // Saved preferences
         intent.putExtra("useDefault", false);
         ChannelPreferences channelPreferences = new ChannelPreferences(getLocationsDao(this).getLocation(locationIndex).getChannelPreferences());
         ChannelPreferencesDataHolder.getInstance().setChannelPreferences(channelPreferences);
    } else { // No preferences
      intent.putExtra("useDefault", true);
    }

    // Start channel preferences activity and wait for result
    channelPreferencesResultLauncher.launch(intent);
  }

  // Result from channel preferences activity
  ActivityResultLauncher<Intent> channelPreferencesResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
    if (result == null || result.getResultCode() != Activity.RESULT_OK) return;
    channelPreferences = ChannelPreferencesDataHolder.getInstance().getChannelPreferences();
  });

  public void actionMenuClick(View view) {
    PopupMenu popup = new PopupMenu(this, view);
    handleMenuClick(popup);
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.edit_location_menu, popup.getMenu());
    popup.show();
  }

  private void handleMenuClick(PopupMenu popup) {
    popup.setOnMenuItemClickListener(item -> {
      if (item.getItemId() == R.id.action_rename) renameClick();
      if (item.getItemId() == R.id.action_delete) deleteClick();
      return true;
    });
  }

  public void saveClick(View view) {
    saveDataToLocation(locationRef);
    finish();
  }

  private void saveDataToLocation(Location location) {
    location.setName(locationName);
    location.setNotify(notifySwitch.isChecked());
    if (notifySwitch.isChecked() && setCustom.isChecked()) {
      location.setChannelPreferences(ChannelPreferencesDataHolder.getInstance().getChannelPreferences());
      return;
    }
    location.setChannelPreferences(null);
  }

  public void cancelClick(View view) {
    finish();
  }
}