package com.severeweatheralerts.RecyclerViews.LocationResult;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.AddressClickListener;

import java.util.ArrayList;

public class LocationResultAdapter extends RecyclerView.Adapter<LocationResultHolder> {
  private final ArrayList<Address> addresses;
  private AddressClickListener clickListener;
  private int selectedPosition = -1;

  public LocationResultAdapter(ArrayList<Address> addresses) {
    this.addresses = addresses;
  }

  public void setClickListener(AddressClickListener clickListener) {
    this.clickListener = clickListener;
  }

  public void clearSelection() {
    int oldSelectedPosition = selectedPosition;
    selectedPosition = -1;
    notifyItemChanged(oldSelectedPosition);
  }

  @NonNull
  @Override
  public LocationResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View locationResultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_search_result, parent, false);
    return new LocationResultHolder(locationResultView);
  }

  @Override
  public void onBindViewHolder(@NonNull LocationResultHolder holder, int position) {
    Address address = addresses.get(position);

    // Add spacing in between cards
    if (position != 0) addTopSpacing(holder, 20);

    holder.address.setText(address.getAddressLine(0));
    holder.coordinates.setText(getCoordinates(address));
    holder.card.setOnClickListener(v -> addressClicked(holder, address));
    setBackgroundColor(holder, address);
  }

  private void addTopSpacing(LocationResultHolder holder, int spacingPixels) {
    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.card.getLayoutParams();
    params.topMargin = spacingPixels;
    holder.card.setLayoutParams(params);
  }

  private void addressClicked(LocationResultHolder holder, Address address) {
    if (!address.getCountryCode().equals("US")) {
      showNonUSAddressMessage(holder.itemView.getContext());
      return;
    }

    // Inform the parent that the address was clicked if the click listener is set
    if (clickListener != null) clickListener.onAddressClick(address, holder);

    updateSelectionInRecyclerView(holder);
  }

  private void updateSelectionInRecyclerView(LocationResultHolder holder) {
    int oldSelectedPosition = selectedPosition;
    selectedPosition = holder.getAbsoluteAdapterPosition();
    notifyItemChanged(oldSelectedPosition);
    notifyItemChanged(selectedPosition);
  }

  private void showNonUSAddressMessage(Context context) {
    new AlertDialog.Builder(context)
        .setMessage("Sorry, because data is sourced from the National Weather Service, only locations in the United States are supported.")
        .setPositiveButton("OK", (dialog, id) -> dialog.dismiss())
        .create().show();
  }

  private void setBackgroundColor(LocationResultHolder holder, Address address) {
    Resources resources = holder.itemView.getContext().getResources();
    if (!address.getCountryCode().equals("US"))
      holder.card.setBackgroundColor(resources.getColor(R.color.address_non_us));
    else if (selectedPosition == holder.getAbsoluteAdapterPosition())
      holder.card.setBackgroundColor(resources.getColor(R.color.address_selected));
    else
      holder.card.setBackgroundColor(resources.getColor(R.color.address_unselected));
  }

  private String getCoordinates(Address address) {
    return String.format("%s, %s", address.getLatitude(), address.getLongitude());
  }

  @Override
  public int getItemCount() {
    return addresses.size();
  }
}
