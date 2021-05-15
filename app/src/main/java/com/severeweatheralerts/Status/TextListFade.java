package com.severeweatheralerts.Status;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;


import com.severeweatheralerts.Constants;
import com.severeweatheralerts.IntervalRun;
import com.severeweatheralerts.R;

import java.util.ArrayList;

public class TextListFade {
  private final ArrayList<String> textList;
  private final TextSwitcher textSwitcher;
  private final IntervalRun intervalRun;
  private int curSubtext = 0;

  public TextListFade(Context context, ArrayList<String> textList, TextSwitcher textSwitcher) {
    this.textSwitcher = textSwitcher;
    this.textList = textList;
    setupTextSwitcher(context);
    intervalRun = new IntervalRun(Constants.STATUS_SUBTEXT_TRANSITION_TIME, this::nextSubtext);
  }

  protected void setupTextSwitcher(Context context) {
    textSwitcher.setFactory(() -> inflateTextView(context));
    textSwitcher.setInAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
    textSwitcher.setOutAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));
  }

  public void beginFade() {
    if (multipleSubtexts()) fadeText();
    else if (singleSubtext()) setText();
    else hideSubtext();
  }

  public void nextSubtext() {
    if (multipleSubtexts()) nextText();
  }

  private boolean singleSubtext() {
    return textList.size() > 0;
  }

  private boolean multipleSubtexts() {
    return textList.size() > 1;
  }

  private void hideSubtext() {
    textSwitcher.setVisibility(View.GONE);
  }

  private void setText() {
    textSwitcher.setText(textList.get(0));
  }

  private void fadeText() {
    intervalRun.startImmediately();
  }

  private void nextText() {
    textSwitcher.setText(textList.get(curSubtext));
    curSubtext = ++curSubtext % textList.size();
    intervalRun.reset();
  }

  private View inflateTextView(Context context) {
    LayoutInflater inflater = LayoutInflater.from(context);
    return inflater.inflate(R.layout.status_subtext_layout, null);
  }
}
