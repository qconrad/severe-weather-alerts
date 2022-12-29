package com.severeweatheralerts.RecyclerViews;

import android.location.Address;

import androidx.recyclerview.widget.RecyclerView;

public interface AddressClickListener {
  void onAddressClick(Address alert, RecyclerView.ViewHolder holder);
}
