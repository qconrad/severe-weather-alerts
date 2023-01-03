package com.severeweatheralerts.Activities;

import static com.severeweatheralerts.FileDB.getLocationsDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.LocationResult.ExtraLocationAdapter;
import com.severeweatheralerts.UserSync.UserSyncWorkScheduler;

import java.util.ArrayList;

public class ManageLocationsActivity extends AppCompatActivity {
  LocationsDao dao;
  ExtraLocationAdapter extraLocationAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_manage_locations);
    dao = getLocationsDao(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    populateLocations();
  }

  private void initializeRecyclerView(ArrayList<Location> locations) {
    RecyclerView recyclerView = findViewById(R.id.extra_locations_recycler_view);
    extraLocationAdapter = new ExtraLocationAdapter(locations);
    extraLocationAdapter.setOnLocationClickListener((locationIndex, holder) -> {
      Intent intent = new Intent(ManageLocationsActivity.this, EditLocationActivity.class);
      intent.putExtra("locationIndex", locationIndex + 1);
      locationEditResult.launch(intent);
    });
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(extraLocationAdapter);
  }

  private void populateLocations() {
    ArrayList<Location> locations = dao.getLocations();
    ArrayList<Location> extraLocations = new ArrayList<>(locations.subList(1, locations.size()));
    initializeRecyclerView(extraLocations);
    if (dao.hasExtraLocations()) setNoExtraLocationsMessage(View.GONE);
    else setNoExtraLocationsMessage(View.VISIBLE);
  }

  private void setNoExtraLocationsMessage(int visibility) {
    findViewById(R.id.no_locations_text).setVisibility(visibility);
  }

  ActivityResultLauncher<Intent> locationEditResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
          result -> { if (result.getResultCode() == Activity.RESULT_OK) extraLocationAdapter.notifyDataSetChanged(); });

  ActivityResultLauncher<Intent> locationSelectorResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
          result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
              Intent data = result.getData();
              dao.addExtraLocation(new Location(data.getStringExtra("name")).setCoordinate(new GCSCoordinate(data.getDoubleExtra("lat", 0.0), data.getDoubleExtra("lon", 0.0))));
              extraLocationAdapter.notifyItemInserted(getLocationsDao(this).extraLocationCount() - 1);
            }
          });

  public void addExtraLocation(View view) {
    Intent intent = new Intent(ManageLocationsActivity.this, LocationPickerActivity.class);
    locationSelectorResult.launch(intent);
  }

  @Override
  protected void onPause() {
    new UserSyncWorkScheduler(this).oneTimeSync();
    super.onPause();
  }
}