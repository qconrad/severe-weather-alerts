package com.severeweatheralerts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.RecyclerViews.Reference.ReferenceRecyclerViewAdapter;

import java.util.Date;

public class AlertViewerActivity extends AppCompatActivity {
  protected Alert al;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alert_viewer);

    makeStatusBarTransparent();
    getAlertFromExtras(getIntent().getExtras());
    populateUIWithAlertData();
  }

  protected void getAlertFromExtras(Bundle bundle) {
    if (bundle != null) {
      int locIndex = bundle.getInt("locIndex");
      int alertIndex = bundle.getInt("alertIndex");
      al = LocationsDao.getLocationList().get(locIndex).getAlerts().get(alertIndex);
    }
  }

  private void populateUIWithAlertData() {
    if (validAlert()) {
      removeNullErrorMessage();
      setTitle();
      setIcon();
      setTimes();
      setLargeHeadline();
      setSmallHeadline();
      setDescription();
      setInstruction();
      setTitleCardColor();
      setBackgroundColor();
      populateReferences();
    }
  }

  private void populateReferences() {
    RecyclerView recyclerView = findViewById(R.id.references);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    ReferenceRecyclerViewAdapter referenceRVAdapter = new ReferenceRecyclerViewAdapter(al.getReferences());
    referenceRVAdapter.setClickListener((position, holder) -> {
      displayReference(al.getReference(position));
    });
    recyclerView.setAdapter(referenceRVAdapter);
  }

  private void displayReference(Alert reference) {
    Intent alertIntent = new Intent(AlertViewerActivity.this, ReferenceViewerActivity.class);
    alertIntent.putExtra("locIndex", 0);
    alertIntent.putExtra("alertID", reference.getNwsId());
    startActivity(alertIntent);
  }

  private void removeNullErrorMessage() {
    TextView notFoundMsg = findViewById(R.id.not_found_message);
    notFoundMsg.setVisibility(View.GONE);
  }

  private void setBackgroundColor() {
    int alertColor = al.getColorAt(new Date());
    View titleCard = findViewById(R.id.alert_viewer);
    int topGradientStep = ColorBrightnessChanger.changeBrightness(alertColor, 0.5f);
    int bottomGradientStep = ColorBrightnessChanger.changeBrightness(alertColor, 0.1f);
    GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {topGradientStep, bottomGradientStep});
    titleCard.setBackground(gd);
  }

  private void setTitleCardColor() {
    CardView titleCard = findViewById(R.id.title_card);
    titleCard.setCardBackgroundColor(al.getColorAt(new Date()));
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
    else if (expired())
      futureTime.setText("Expired " + new RelativeTimeFormatter(new Date(), al.getEndTime()).getFormattedString() + " ago");
    else
      futureTime.setText("Active next " + new RelativeTimeFormatter(new Date(), al.getEndTime()).getFormattedString());
  }

  private boolean startsInFuture() {
    return al.startsBefore(new Date());
  }

  private boolean expired() {
    return !al.activeAt(new Date());
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
    TextView title = findViewById(R.id.card_title);
    title.setText(al.getName());
  }

  private void setIcon() {
    ImageView iv = findViewById(R.id.card_icon);
    iv.setImageResource(al.getIcon());
  }

  private void setLargeHeadline() {
    if (notNull(al.getLargeHeadline())) {
      LinearLayout largeHeadlineBox = findViewById(R.id.large_headline_box);
      TextView largeHeadline = findViewById(R.id.large_headline);
      largeHeadline.setText(al.getLargeHeadline());
      largeHeadlineBox.setVisibility(View.VISIBLE);
    }
  }

  private boolean notNull(String text) {
    return text != null;
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
      description.setText(Html.fromHtml(new KeywordEmphasizer().emphasize(al.getDescription())));
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