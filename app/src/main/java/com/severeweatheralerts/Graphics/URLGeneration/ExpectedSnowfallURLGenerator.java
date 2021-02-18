package com.severeweatheralerts.Graphics.URLGeneration;

import android.content.Context;

import com.severeweatheralerts.Graphics.Bounds.Bound;
import com.severeweatheralerts.Graphics.GridData.Parameter;

public class ExpectedSnowfallURLGenerator extends MapTimeFetch {
  public ExpectedSnowfallURLGenerator(Context context, Bound bound, Parameter gridData) {
    super(context, bound, gridData);
  }

  @Override
  public void getURLS(String dateString) {
    urls.add(new URL().getTotalSnow(bound, "conus", dateString));
    urls.add(new URL().getTotalSnowPoints(bound, "conus", dateString));
    urls.add(new URL().getCountyMap(bound));
  }
}
