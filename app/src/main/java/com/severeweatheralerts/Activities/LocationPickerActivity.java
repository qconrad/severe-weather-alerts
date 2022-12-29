package com.severeweatheralerts.Activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.LocationResult.LocationResultAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class LocationPickerActivity extends AppCompatActivity {
  private Address selectedAddress;
  private final ArrayList<Address> addressList = new ArrayList<>();
  LocationResultAdapter locationResultAdapter;
  RecyclerView locationResultRecyclerView;
  TextView noResultsTextView;
  EditText searchEditText;
  Geocoder geocoder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location_picker);
    initializeSearchResultsRecyclerView();
    noResultsTextView = findViewById(R.id.no_results_text);
    searchEditText = findViewById(R.id.search_edit_text);
    geocoder = new Geocoder(this);
  }

  private void clearPreviousSearches() {
    selectedAddress = null;
    addressList.clear();
    locationResultAdapter.clearSelection();
    setUseLocationEnabled(false);
  }

  private void initializeSearchResultsRecyclerView() {
    locationResultRecyclerView = findViewById(R.id.address_search_results_rv);
    locationResultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    locationResultAdapter = new LocationResultAdapter(addressList);
    locationResultAdapter.setClickListener((address, holder) -> selectAddress(address));
    locationResultRecyclerView.setAdapter(locationResultAdapter);
  }

  private void hideKeyboard() {
    InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
    View view = this.getCurrentFocus();
    if (view == null) view = new View(this);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

  public void searchClick(View view) {
    hideKeyboard();
    clearPreviousSearches();

    String searchText = getSearchTerm();
    if (searchText.equals("")) {
      Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show();
      return;
    }

    try {
      lookupLocation(searchText);
    } catch (IOException e) {
      Toast.makeText(this, "Error looking up location", Toast.LENGTH_SHORT).show();
      return;
    }

    setNoResultsVisible(addressList.size() == 0);
    locationResultRecyclerView.getAdapter().notifyDataSetChanged();
  }

  private void lookupLocation(String searchText) throws IOException {
    addressList.addAll(geocoder.getFromLocationName(searchText, 4));
  }

  private String getSearchTerm() {
    return searchEditText.getText().toString();
  }

  private void selectAddress(Address address) {
    selectedAddress = address;
    setUseLocationEnabled(true);
  }

  private void setUseLocationEnabled(boolean useLocation) {
    findViewById(R.id.use_location).setEnabled(useLocation);
  }

  private void setNoResultsVisible(boolean visible) {
    if (visible) noResultsTextView.setVisibility(View.VISIBLE);
    else noResultsTextView.setVisibility(View.INVISIBLE);
  }

  public void cancel(View view) {
    finish();
  }

  public void useLocation(View view) {
    Intent intent = new Intent()
            .putExtra("name", selectedAddress.getLocality())
            .putExtra("lat", selectedAddress.getLatitude())
            .putExtra("lon", selectedAddress.getLongitude());
    setResult(Activity.RESULT_OK, intent);
    finish();
  }
}