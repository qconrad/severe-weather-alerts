package com.severeweatheralerts.Location;

import android.content.Context;

import com.severeweatheralerts.PaperDB;

import java.util.ArrayList;

public class PaperLocationDatabase implements LocationDatabase {
  private final Context context;
  private final String paperDBName;

  public PaperLocationDatabase(Context context, String paperDBName) {
    this.context = context;
    this.paperDBName = paperDBName;
  }

  @Override
  public ArrayList<Location> get() {
    return PaperDB.getInstance(context).read(paperDBName, new ArrayList<>());
  }

  @Override
  public void set(ArrayList<Location> locations) {
    PaperDB.getInstance(context).write(paperDBName, locations);
  }
}
