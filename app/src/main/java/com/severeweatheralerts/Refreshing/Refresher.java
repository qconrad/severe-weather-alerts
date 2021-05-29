package com.severeweatheralerts.Refreshing;

import com.severeweatheralerts.Refreshing.IntervalRun;

import java.util.ArrayList;

public class Refresher {
  private final ArrayList<IntervalRun> refreshers;

  public Refresher() { refreshers = new ArrayList<>(); }

  public void add(IntervalRun intervalRun) {
    refreshers.add(intervalRun);
  }

  public void stop() {
    for (IntervalRun refresher : refreshers) refresher.stop();
  }

  public void start() {
    for (IntervalRun refresher : refreshers) refresher.startNextInterval();
  }

  public void startAndRefresh() {
    for (IntervalRun refresher : refreshers) refresher.startImmediately();
  }
}
