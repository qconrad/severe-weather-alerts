package com.severeweatheralerts.Activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.severeweatheralerts.R;

import java.io.IOException;
import java.util.List;

public class LocationPickerActivity extends AppCompatActivity {
  private double lat;
  private double lon;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location_picker);
  }

  public void searchClick(View view) {
    TextView noResultsText = findViewById(R.id.no_results_text);
    noResultsText.setVisibility(View.INVISIBLE);

    TextView textView = findViewById(R.id.location_search_ev);
    String searchText = textView.getText().toString();
    if (searchText.equals("")) return;

    Geocoder geocoder = new Geocoder(this);
    List<Address> addresses = null;
    try {
      addresses = geocoder.getFromLocationName(searchText, 5);
    } catch (IOException e) {
      e.printStackTrace();
      Toast.makeText(this, "Error looking up location", Toast.LENGTH_SHORT).show();
      return;
    }
    if (addresses.size() == 0) {
      noResultsText.setVisibility(View.VISIBLE);
      return;
    }
    // Take the first location for now
    lat = addresses.get(0).getLatitude();
    lon = addresses.get(0).getLongitude();
  }

  public void cancel(View view) {
    finish();
  }

  public void useLocation(View view) {
    TextView nameEt = findViewById(R.id.location_name_et);
    String name = nameEt.getText().toString();
    if (name.equals("")) {
      Toast.makeText(this, "Enter a name for this location", Toast.LENGTH_SHORT).show();
      return;
    }
    Intent intent = new Intent().putExtra("name", name).putExtra("lat", lat).putExtra("lon", lon);
    setResult(Activity.RESULT_OK, intent);
    finish();
  }
}