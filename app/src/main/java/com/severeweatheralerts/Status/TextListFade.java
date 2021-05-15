package com.severeweatheralerts.Status;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;

import com.severeweatheralerts.R;

import java.util.ArrayList;

public class TextListFade {
  private final ArrayList<String> textList;
  private final TextSwitcher textSwitcher;
  private int curSubtext = 1;

  public TextListFade(Context context, ArrayList<String> textList, TextSwitcher textSwitcher) {
    this.textSwitcher = textSwitcher;
    this.textList = textList;
    setupTextSwitcher(context);
    if (textListEmpty()) hideSubtext();
    else setText();
  }

  private boolean textListEmpty() {
    return textList.size() < 1;
  }

  protected void setupTextSwitcher(Context context) {
    textSwitcher.setFactory(() -> inflateTextView(context));
    textSwitcher.setInAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
    textSwitcher.setOutAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));
  }

  public void nextText() {
    textSwitcher.setText(textList.get(curSubtext));
    curSubtext = ++curSubtext % textList.size();
  }

  private void hideSubtext() {
    textSwitcher.setVisibility(View.GONE);
  }

  private void setText() {
    textSwitcher.setText(textList.get(0));
  }

  private View inflateTextView(Context context) {
    LayoutInflater inflater = LayoutInflater.from(context);
    return inflater.inflate(R.layout.status_subtext_layout, null);
  }
}
