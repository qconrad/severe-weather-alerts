package com.severeweatheralerts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

public class AlertViewerActivity extends AppCompatActivity {
  Alert al;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alert_viewer);

    makeStatusBarTransparent();
    populateUIWithAlertData();
  }

  private void populateUIWithAlertData() {
    if (validAlert()) {
      setTitle();
      setTimes();
      setLargeHeadline();
      setSmallHeadline();
      setDescription();
      setInstruction();
    }
  }

  private void setTimes() {
    if (isCancellation())
      setCancelTime();
    else {
      setPastTime();
      setFutureTime();
    }
  }

  private void setCancelTime() {
    TextView cancelTime = findViewById(R.id.cancel_time);
    cancelTime.setVisibility(View.VISIBLE);
    cancelTime.setText("Cancelled " + new RelativeTimeFormatter(new Date(), al.getSentTime()).getFormattedString() + " ago");
  }

  private void setPastTime() {
    TextView pastTime = findViewById(R.id.past_time);
    pastTime.setVisibility(View.VISIBLE);
    String verb = "Posted ";
    if (al.getType() == Alert.Type.UPDATE) verb = "Updated ";
    pastTime.setText(verb + new RelativeTimeFormatter(new Date(), al.getSentTime()).getFormattedString() + " ago");
  }

  private void setFutureTime() {
    TextView futureTime = findViewById(R.id.future_time);
    futureTime.setVisibility(View.VISIBLE);
    if (startsInFuture())
      futureTime.setText("Active in " + new RelativeTimeFormatter(new Date(), al.getStartTime()).getFormattedString());
    else
      futureTime.setText("Active next " + new RelativeTimeFormatter(new Date(), al.getEndTime()).getFormattedString());
  }

  private boolean startsInFuture() {
    return al.isBeforeStart(new Date());
  }

  private boolean isCancellation() {
    return al.getType() == Alert.Type.CANCEL;
  }

  private boolean validAlert() {
    return al != null;
  }

  private void makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      Window w = getWindow();
      w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
  }

  private void setTitle() {
    TextView title = findViewById(R.id.title);
    title.setText(al.getName());
  }

  private void setLargeHeadline() {
    if (notNull(al.getLargeHeadline())) {
      LinearLayout largeHeadlineBox = findViewById(R.id.large_headline_box);
      TextView largeHeadline = findViewById(R.id.large_headline);
      largeHeadline.setText(al.getLargeHeadline());
      largeHeadlineBox.setVisibility(View.VISIBLE);
    }
  }

  private boolean notNull(String largeHeadline) {
    return largeHeadline != null;
  }

  private void setSmallHeadline() {
    if (notNull(al.getSmallHeadline())) {
      TextView smallHeadline = findViewById(R.id.small_headline);
      smallHeadline.setText(al.getSmallHeadline());
      smallHeadline.setVisibility(View.VISIBLE);
    }
  }

  private void setDescription() {
    if (notNull(al.getDescription())) {
      TextView description = findViewById(R.id.description);
      description.setText(al.getDescription());
      description.setVisibility(View.VISIBLE);
    }
  }

  private void setInstruction() {
    if (notNull(al.getInstruction())) {
      TextView instruction = findViewById(R.id.instruction);
      TextView instructionLabel = findViewById(R.id.instruction_label);
      instruction.setVisibility(View.VISIBLE);
      instructionLabel.setVisibility(View.VISIBLE);
      instruction.setText(al.getInstruction());
    }
  }
}