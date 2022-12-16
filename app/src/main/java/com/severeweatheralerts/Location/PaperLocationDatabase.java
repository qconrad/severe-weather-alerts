package com.severeweatheralerts.Location;

import android.content.Context;

import com.severeweatheralerts.PaperDB;

import java.util.ArrayList;

public class PaperLocationDatabase implements LocationDatabase {
  private final Context context;

  public PaperLocationDatabase(Context context) {
    this.context = context;
  }

  @Override
  public ArrayList<Location> get() {
    return PaperDB.getInstance(context).read("locations", new ArrayList<>());
  }

  @Override
  public void set(ArrayList<Location> locations) {
    PaperDB.getInstance(context).write("locations", locations);
  }
}
