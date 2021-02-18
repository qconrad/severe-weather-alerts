package com.severeweatheralerts.Graphics.URLGeneration;

import android.content.Context;

import com.severeweatheralerts.Graphics.Bounds.Bound;
import com.severeweatheralerts.Graphics.GridData.Parameter;

import java.util.ArrayList;

public class ExpectedSnowfallURLGenerator extends MapTimeFetch {
  public ExpectedSnowfallURLGenerator(Context context, Bound bound, Parameter gridData) {
    super(context, bound, gridData);
  }

  @Override
  public void getURLS(ArrayList<String> dateStrings) {
    String LatestTime = dateStrings.get(dateStrings.size()-1);
    urls.add(new URL().getTotalSnow(bound, "conus", LatestTime));
    urls.add(new URL().getTotalSnowPoints(bound, "conus", LatestTime));
    urls.add(new URL().getCountyMap(bound));
  }
}
