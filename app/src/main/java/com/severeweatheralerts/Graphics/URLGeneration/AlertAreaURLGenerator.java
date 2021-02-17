package com.severeweatheralerts.Graphics.URLGeneration;

import com.severeweatheralerts.Graphics.Bounds.Bound;

import java.util.ArrayList;

public class AlertAreaURLGenerator implements URLGenerator {
  private final Bound bound;
  public AlertAreaURLGenerator(Bound bound) {
    this.bound = bound;
  }

  @Override
  public void generate(URLGenCompleteListener completeListener) {
    ArrayList<String> urls = new ArrayList<>();
    urls.add(new URL().getCountyMap(bound));
    completeListener.onComplete(urls);
  }
}
