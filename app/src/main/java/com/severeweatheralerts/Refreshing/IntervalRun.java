package com.severeweatheralerts.Refreshing;

import android.os.Handler;

public class IntervalRun {
  private final int intervalMs;
  private final IntervalCallback intervalCallback;
  private final Handler handler = new Handler();
  private final Runnable runnable;

  public IntervalRun(int intervalMs, IntervalCallback intervalCallback) {
    this.intervalMs = intervalMs;
    this.intervalCallback = intervalCallback;
    runnable = this::callback;
  }

  public void startImmediately() {
    handler.post(runnable);
  }

  public void startNextInterval() {
    handler.postDelayed(runnable, intervalMs);
  }

  public void stop() {
    handler.removeCallbacks(runnable);
  }

  private void callback() {
    intervalCallback.onInterval();
    handler.removeCallbacks(runnable);
    handler.postDelayed(runnable, intervalMs);
  }

  public void reset() {
    handler.removeCallbacks(runnable);
    handler.postDelayed(runnable, intervalMs);
  }

  public void execAndReset() {
    intervalCallback.onInterval();
    reset();
  }
}
