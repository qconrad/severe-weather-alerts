package com.severeweatheralerts.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.FileDBs;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.R;

import java.util.ArrayList;

public class ManageLocationsActivity extends AppCompatActivity {
  LocationsDao dao;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_manage_locations);
    dao = FileDBs.locationsDao;
  }

  @Override
  protected void onResume() {
    super.onResume();
    populateLocations();
  }

  private void populateLocations() {
    ArrayList<Location> locations = dao.getLocations();
    LinearLayout locationStack = findViewById(R.id.location_stack);
    locationStack.removeAllViews();
    if (noExtras(locations)) return;
    findViewById(R.id.no_locations_text).setVisibility(View.GONE);
    addLocationsToLayout(locationStack, locations);
  }

  private boolean noExtras(ArrayList<Location> locations) {
    return locations.size() <= 1;
  }

  private void addLocationsToLayout(LinearLayout locationStack, ArrayList<Location> locations) {
    for (int i = 1; i < locations.size(); i++)
      addLocationToStack(locations, locationStack, i);
  }

  private void addLocationToStack(ArrayList<Location> locations, LinearLayout locationStack, int i) {
    Button location = new Button(this);
    Intent intent = new Intent(ManageLocationsActivity.this, EditLocationActivity.class);
    intent.putExtra("locationIndex", i);
    location.setOnClickListener(view -> locationEditResult.launch(intent));
    location.setText(locations.get(i).getName());
    locationStack.addView(location);
  }

  ActivityResultLauncher<Intent> locationEditResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
          result -> { if (result.getResultCode() == Activity.RESULT_OK) populateLocations(); });

  ActivityResultLauncher<Intent> locationSelectorResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
          result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
              Intent data = result.getData();
              dao.addExtraLocation(new Location(data.getStringExtra("name")).setCoordinate(new GCSCoordinate(data.getDoubleExtra("lat", 0.0), data.getDoubleExtra("lon", 0.0))));
              populateLocations();
            }
          });

  public void addExtraLocation(View view) {
    Intent intent = new Intent(ManageLocationsActivity.this, LocationPickerActivity.class);
    locationSelectorResult.launch(intent);
  }
}