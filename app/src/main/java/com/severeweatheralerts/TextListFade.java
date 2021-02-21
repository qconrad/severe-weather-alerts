package com.severeweatheralerts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;


import java.util.ArrayList;

public class TextListFade {
  private final ArrayList<String> textList;
  private final Context context;
  private final TextSwitcher textSwitcher;
  TextFadeInterval textFadeInterval;

  public TextListFade(Context context, ArrayList<String> textList, TextSwitcher textSwitcher) {
    this.textSwitcher = textSwitcher;
    this.context = context;
    this.textList = textList;
    setupTextSwitcher();
  }

  protected void setupTextSwitcher() {
    textSwitcher.setFactory(this::inflateTextView);
    textSwitcher.setInAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
    textSwitcher.setOutAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));
  }

  public void beginFade() {
    if (multipleSubtexts()) fadeText();
    if (singleSubtext()) setText();
    else hideSubtext();
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
    textFadeInterval = new TextFadeInterval(textSwitcher, textList, Constants.STATUS_SUBTEXT_TRANSITION_TIME);
  }

  private View inflateTextView() {
    LayoutInflater inflater = LayoutInflater.from(context);
    return inflater.inflate(R.layout.status_subtext_layout, null);
  }

  public void nextSubtext() {
    if (multipleSubtexts()) textFadeInterval.nextText();
  }
}
