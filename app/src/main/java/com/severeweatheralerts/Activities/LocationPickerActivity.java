package com.severeweatheralerts.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.severeweatheralerts.JSONParsing.GeocodeParser;
import com.severeweatheralerts.Networking.FetchServices.RequestCallback;
import com.severeweatheralerts.Networking.FetchServices.StringFetchService;
import com.severeweatheralerts.R;

public class LocationPickerActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location_picker);
  }

  public void searchClick(View view) {
    TextView textView = findViewById(R.id.location_search_ev);
    String text = textView.getText().toString();
    if (text.equals("")) return;
    view.setEnabled(false);
    ProgressBar progressBar = findViewById(R.id.location_search_progress);
    progressBar.setVisibility(View.VISIBLE);
    String search = text.replace(" ", "+");
    Button searchBtn = findViewById(R.id.location_search_button);
    new StringFetchService(this, "https://geocode.xyz/" + search + "?json=1&region=US").request(new RequestCallback() {
      @Override
      public void success(Object response) {
        GeocodeParser geocodeParser = new GeocodeParser(response.toString());
        searchBtn.setEnabled(false);
        Button clear = findViewById(R.id.clear_button);
        clear.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        TextView textView1 = findViewById(R.id.location_text);
        if (geocodeParser.hasError()) {
          textView1.setText(geocodeParser.getError());
        }
        else {
          String location = "";
          if (geocodeParser.getAddress() != null && !geocodeParser.getAddress().equals(geocodeParser.getAddress().toUpperCase())) location += geocodeParser.getAddress() + "\n";
          if (geocodeParser.getCity() != null) location += geocodeParser.getCity() + "\n";
          if (geocodeParser.getPostal() != null) location += geocodeParser.getPostal();
          textView1.setText(location.trim());
          lat = geocodeParser.getLatitude();
          lon = geocodeParser.getLongitude();
          Button useThisLocation = findViewById(R.id.use_location);
          useThisLocation.setEnabled(true);
        }
      }

      @Override
      public void error(VolleyError error) {
        Toast.makeText(LocationPickerActivity.this, "An error occurred while looking up location", Toast.LENGTH_SHORT).show();
        searchBtn.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
      }
    });
  }

  public void cancel(View view) {
    finish();
  }

  public void clearClick(View view) {
    Button useThisLocation = findViewById(R.id.use_location);
    useThisLocation.setEnabled(true);
    Button search = findViewById(R.id.location_search_button);
    search.setEnabled(true);
    TextView textView1 = findViewById(R.id.location_text);
    textView1.setText("");
    Button clear = findViewById(R.id.clear_button);
    clear.setVisibility(View.INVISIBLE);
  }

  double lat;
  double lon;

  public void useLocation(View view) {
    TextView nameEt = findViewById(R.id.location_name_et);
    String name = nameEt.getText().toString();
    if (name.equals("")) {
      Toast.makeText(this, "Enter a name for this location", Toast.LENGTH_SHORT).show();
      return;
    }
    else if (name.length() > 20) {
      Toast.makeText(this, "This name is too long. Try picking a shorter one.", Toast.LENGTH_SHORT).show();
      return;
    }
    Intent intent = new Intent().putExtra("name", name).putExtra("lat", lat).putExtra("lon", lon);
    setResult(Activity.RESULT_OK, intent);
    finish();
  }
}