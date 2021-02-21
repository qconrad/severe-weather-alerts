package com.severeweatheralerts;

import android.os.Handler;
import android.widget.TextSwitcher;

import java.util.ArrayList;

public class TextFadeInterval {
  private final Handler switchHandler = new Handler();
  private final TextSwitcher textSwitcher;
  private final ArrayList<String> textList;
  private final int interval;
  private Runnable switchRunnable;
  private int curSubtext = 0;

  public TextFadeInterval(TextSwitcher textSwitcher, ArrayList<String> textList, int interval) {
    this.textSwitcher = textSwitcher;
    this.textList = textList;
    this.interval = interval;
    setupSwitchingSchedule();
  }

  private void setupSwitchingSchedule() {
    switchRunnable = this::nextText;
    switchHandler.post(switchRunnable);
  }

  public void nextText() {
    textSwitcher.setText(textList.get(curSubtext));
    curSubtext = ++curSubtext % textList.size();
    switchHandler.removeCallbacks(switchRunnable);
    switchHandler.postDelayed(switchRunnable, interval);
  }
}
