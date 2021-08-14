package com.severeweatheralerts.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.R;

public class EditLocationActivity extends AppCompatActivity {
  private LocationsDao dao;
  private int locationIndex;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_location);
    dao = LocationsDao.getInstance(this);
    locationIndex = getIntent().getExtras().getInt("locationIndex", 0);
    setNameText();
  }

  private void setNameText() {
    TextView locationNameTextView = findViewById(R.id.edit_location_name);
    locationNameTextView.setText(dao.getName(locationIndex));
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
              dao.setName(locationIndex, data.getStringExtra("name"));
              dao.setCoordinate(locationIndex, data.getDoubleExtra("lat", 0.0), data.getDoubleExtra("lon", 0.0));
              setNameText();
            }
          });

  public void setPositionName(View view) {
    locationSelectorResult.launch(new Intent(EditLocationActivity.this, LocationPickerActivity.class));
  }
}