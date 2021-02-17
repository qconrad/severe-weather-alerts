package com.severeweatheralerts.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.Adapters.GCSCoordinate;
import com.severeweatheralerts.AlertListTools.AlertFinder;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.GraphicGeneration.GraphicFactory;
import com.severeweatheralerts.Graphics.Polygon.GCSToMercatorCoordinateAdapter;
import com.severeweatheralerts.Graphics.Graphic;
import com.severeweatheralerts.Graphics.Types.GraphicType;
import com.severeweatheralerts.Graphics.Polygon.MercatorCoordinate;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.Reference.ReferenceRecyclerViewAdapter;
import com.severeweatheralerts.TextGeneraters.NextUpdate;
import com.severeweatheralerts.TextGeneraters.Time.AlertTime;
import com.severeweatheralerts.TextGeneraters.Time.TimeGenerator;
import com.severeweatheralerts.TextUtils.KeywordEmphasizer;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

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
    int locIndex = bundle.getInt("locIndex");
    String alertIndex = bundle.getString("alertID");
    al = new AlertFinder(getAlerts(locIndex)).findAlertByID(alertIndex);
  }

  private ArrayList<Alert> getAlerts(int locIndex) {
    return getLocation(locIndex).getAlerts();
  }

  private Location getLocation(int locIndex) {
    return LocationsDao.getLocationList().get(locIndex);
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
      setSender();
      setNextUpdate();
      setTitleCardColor();
      setBackgroundColor();
      populateReferences();
      generateGraphics();
    }
  }

  private void generateGraphics() {
    MercatorCoordinate location = new GCSToMercatorCoordinateAdapter(new GCSCoordinate(LocationsDao.getLocation(0).getLatitude(), LocationsDao.getLocation(0).getLongitude())).getCoordinate();
    ArrayList<GraphicType> types = new GraphicFactory(al).getTypes();
    for (GraphicType type : types) {
      View graphicView = createGraphicView();
      displayGraphicTitleAndProgressBar(type, graphicView);
      generateGraphic(location, type, graphicView);
    }
  }

  private void generateGraphic(MercatorCoordinate location, GraphicType type, View graphicView) {
    type.getGenerator(this, al, location).generate(graphic -> {
      if (graphic == null) displaySubtext(graphicView, "Error while generating this graphic");
      else {
        if (graphic.hasSubtext()) displaySubtext(graphicView, graphic.getSubtext());
        displayImage(graphicView, graphic);
      }
      hideProgressBar(graphicView);
    });
  }

  private void displayImage(View graphicView, Graphic graphic) {
    ImageView iv = graphicView.findViewById(R.id.gfx_image);
    RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(Resources.getSystem(), graphic.getImage());
    dr.setCornerRadius(20.0f);
    iv.setImageDrawable(dr);
  }

  private void displaySubtext(View graphicView, String text) {
    TextView subTextTv = graphicView.findViewById(R.id.gfx_subtext);
    subTextTv.setVisibility(View.VISIBLE);
    subTextTv.setText(text);
  }

  private void hideProgressBar(View graphicView) {
    graphicView.findViewById(R.id.progress).setVisibility(View.GONE);
  }

  private void displayGraphicTitleAndProgressBar(GraphicType graphicType, View graphicView) {
    setTitle(graphicView, graphicType.getTitle());
    setProgressBarColor(graphicView, al.getColorAt(new Date()));
    addGraphicToLayout(graphicView);
  }

  private void setProgressBarColor(View graphicView, int color) {
    ProgressBar pb = graphicView.findViewById(R.id.progress);
    pb.getIndeterminateDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);
  }

  private void addGraphicToLayout(View graphicView) {
    LinearLayout graphicStack = findViewById(R.id.graphics);
    graphicStack.addView(graphicView);
  }

  private View createGraphicView() {
    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    return inflater.inflate(R.layout.graphic, null);
  }

  private void setTitle(View graphicView, String text) {
    TextView titleTv = graphicView.findViewById(R.id.gfx_title);
    titleTv.setText(text);
  }

  private void populateReferences() {
    boolean hasReferences = al.getReferences().size() > 0;
    if (hasReferences) {
      View referencesLabel = findViewById(R.id.references_label);
      referencesLabel.setVisibility(View.VISIBLE);
      RecyclerView recyclerView = findViewById(R.id.references);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      ReferenceRecyclerViewAdapter referenceRVAdapter = new ReferenceRecyclerViewAdapter(al.getReferences());
      referenceRVAdapter.setClickListener((position, holder) -> {
        displayReference(al.getReference(position));
      });
      recyclerView.setAdapter(referenceRVAdapter);
    }
  }

  private void displayReference(Alert reference) {
    Intent alertIntent = new Intent(AlertViewerActivity.this, ReferenceViewerActivity.class);
    alertIntent.putExtra("locIndex", 0);
    alertIntent.putExtra("alertID", reference.getNwsId());
    startActivity(alertIntent);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
    TimeGenerator generator = getTimeGenerator();
    setLeftTime(generator);
    setCenter(generator);
    setRightTime(generator);
  }

  protected TimeGenerator getTimeGenerator() {
    return new AlertTime(al, new Date());
  }

  private void setLeftTime(TimeGenerator gen) {
    if (gen.hasLeftTime()) {
      TextView leftTime = findViewById(R.id.left_time);
      leftTime.setVisibility(View.VISIBLE);
      leftTime.setText(gen.getLeftTime());
    }
  }

  private void setCenter(TimeGenerator gen) {
    if (gen.hasCenterTime()) {
      TextView centerTime = findViewById(R.id.center_time);
      centerTime.setVisibility(View.VISIBLE);
      centerTime.setText(gen.getCenterTime());
    }
  }

  private void setRightTime(TimeGenerator gen) {
    if (gen.hasRightTime()) {
      TextView rightTime = findViewById(R.id.right_time);
      rightTime.setVisibility(View.VISIBLE);
      rightTime.setText(gen.getRightTime());
    }
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
      description.setLinkTextColor(al.getColorAt(new Date()));
      description.setText(Html.fromHtml(new KeywordEmphasizer().emphasize(al.getDescription())));
      description.setVisibility(View.VISIBLE);
    }
  }

  private void setSender() {
    TextView sender = findViewById(R.id.sender);
    sender.setText(Html.fromHtml("<a href=https://www.weather.gov/" + al.getSenderCode().toLowerCase() + ">" + al.getSender()));
    sender.setMovementMethod(LinkMovementMethod.getInstance());
  }

  protected void setNextUpdate() {
    TextView nextUpdate = findViewById(R.id.next_update);
    NextUpdate nextUpdateGen = new NextUpdate(al, TimeZone.getDefault());
    if (nextUpdateGen.hasText()) {
      nextUpdate.setVisibility(View.VISIBLE);
      nextUpdate.setText(nextUpdateGen.getText(new Date()));
    }
  }

  private void setInstruction() {
    if (notNull(al.getInstruction())) {
      TextView instruction = findViewById(R.id.instruction);
      TextView instructionLabel = findViewById(R.id.instruction_label);
      instruction.setLinkTextColor(al.getColorAt(new Date()));
      instruction.setVisibility(View.VISIBLE);
      instructionLabel.setVisibility(View.VISIBLE);
      instruction.setText(al.getInstruction());
    }
  }
}