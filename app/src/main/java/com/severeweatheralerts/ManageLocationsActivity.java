package com.severeweatheralerts;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;

import java.util.ArrayList;

public class ManageLocationsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_manage_locations);
    populateLocations();
  }

  private void populateLocations() {
    ArrayList<Location> locations = LocationsDao.getInstance(this).getLocations();
    if (noExtras(locations)) return;
    findViewById(R.id.no_locations_text).setVisibility(View.GONE);
    addLocationsToLayout(locations);
  }

  private boolean noExtras(ArrayList<Location> locations) {
    return locations.size() <= 1;
  }

  private void addLocationsToLayout(ArrayList<Location> locations) {
    LinearLayout locationStack = findViewById(R.id.location_stack);
    for (int i = 1; i < locations.size(); i++)
      addLocationToStack(locations, locationStack, i);
  }

  private void addLocationToStack(ArrayList<Location> locations, LinearLayout locationStack, int i) {
    Button location = new Button(this);
    location.setText(locations.get(i).getName());
    locationStack.addView(location);
  }
}