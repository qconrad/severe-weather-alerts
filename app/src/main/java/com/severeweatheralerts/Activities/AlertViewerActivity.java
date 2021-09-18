package com.severeweatheralerts.Activities;

import static com.severeweatheralerts.FileDBs.getLocationsDao;

import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.severeweatheralerts.AlertListTools.AlertFinder;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Graphics.Generators.GraphicCompleteListener;
import com.severeweatheralerts.Graphics.Generators.GraphicGenerator;
import com.severeweatheralerts.Graphics.Types.GraphicType;
import com.severeweatheralerts.Graphics.Types.TypeFactory;
import com.severeweatheralerts.Graphics.ViewInflaters.Graphic;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.Reference.ReferenceRecyclerViewAdapter;
import com.severeweatheralerts.Refreshing.IntervalRun;
import com.severeweatheralerts.Refreshing.Refresher;
import com.severeweatheralerts.TextGeneraters.NextUpdate;
import com.severeweatheralerts.TextGeneraters.Time.AlertTime;
import com.severeweatheralerts.TextGeneraters.Time.TimeGenerator;
import com.severeweatheralerts.TextUtils.KeywordEmphasizer;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class AlertViewerActivity extends AppCompatActivity {
  private final Refresher refresher = new Refresher();
  private Location location;
  protected Alert al;
  private boolean isActive;
  protected int locationIndex;
  protected LocationsDao locationsDao;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alert_viewer);

    makeStatusBarTransparent();
    getAlertFromExtras(getIntent().getExtras());
    if (!validAlert()) return;
    populateUIWithAlertData();

    isActive = al.activeAt(new Date());
    refresher.add(new IntervalRun(5000, this::refresh));
    refresher.add(new IntervalRun(60000, this::populateReferences));
  }

  private void refresh() {
    setTimes();
    if (noLongerActive()) {
      isActive = false;
      setColors();
      populateReferences();
      clearGraphics();
      generateGraphics();
    }
  }

  private void clearGraphics() {
    LinearLayout graphicStack = findViewById(R.id.graphics);
    graphicStack.removeAllViews();
  }

  private boolean noLongerActive() {
    return isActive && !al.activeAt(new Date());
  }

  private void setColors() {
    setBackgroundColor();
    setTitleCardColor();
  }

  protected void getAlertFromExtras(Bundle bundle) {
    locationIndex = bundle.getInt("locIndex");
    location = getLocationsDao(this).getLocation(locationIndex);
    String alertIndex = bundle.getString("alertID");
    al = new AlertFinder(getAlerts()).findAlertByID(alertIndex);
  }

  private ArrayList<Alert> getAlerts() {
    return location.getAlerts();
  }

  private void populateUIWithAlertData() {
    removeNullErrorMessage();
    setColors();
    setTitle();
    setIcon();
    setTimes();
    setLargeHeadline();
    setSmallHeadline();
    setDescription();
    setInstruction();
    setSender();
    setNextUpdate();
    populateReferences();
    generateGraphics();
  }

  protected void generateGraphics() {
    for (GraphicType type : getTypes()) generateAndDisplayGraphic(type);
  }

  private ArrayList<GraphicType> getTypes() {
    return new TypeFactory(al, new Date()).getTypes();
  }

  protected void generateAndDisplayGraphic(GraphicType type) {
    View graphicView = createGraphicView();
    displayGraphicTitleAndProgressBar(type, graphicView);
    GraphicGenerator generator = type.getGenerator(this, al, location.getCoordinate());
    generateGraphic(generator, graphicView);
    startUpdates(type, generator, graphicView);
  }

  private void startUpdates(GraphicType type, GraphicGenerator generator, View graphicView) {
    if (type.getValidDuration() <= 0) return;
    refresher.add(new IntervalRun(type.getValidDuration(), () -> generateGraphic(generator, graphicView)));
  }

  private void generateGraphic(GraphicGenerator generator, View graphicView) {
    GraphicCompleteListener graphicCompleteListener = new GraphicCompleteListener() {
      @Override
      public void onComplete(Graphic graphic) {
        runOnUiThread(() -> {
          if (graphic.hasSubtext()) displaySubtext(graphicView, graphic.getSubtext());
          else hideSubtext(graphicView);
          displayImage(graphicView, graphic);
          hideProgressBar(graphicView);
        });
      }

      @Override
      public void error(String message) {
        displaySubtext(graphicView, message);
        hideProgressBar(graphicView);
      }
    };
    new Thread(() -> generator.generate(graphicCompleteListener)).start();
  }

  private void hideSubtext(View graphicView) {
    TextView subTextTv = graphicView.findViewById(R.id.gfx_subtext);
    subTextTv.setVisibility(View.GONE);
  }

  private void displayImage(View graphicView, Graphic graphic) {
    LinearLayout ll = graphicView.findViewById(R.id.gfx_view);
    ll.removeAllViews();
    ll.addView(graphic.getView());
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

  protected void populateReferences() {
    boolean hasReferences = al.getReferences().size() > 0;
    if (hasReferences) {
      View referencesLabel = findViewById(R.id.references_label);
      referencesLabel.setVisibility(View.VISIBLE);
      RecyclerView recyclerView = findViewById(R.id.references);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      ReferenceRecyclerViewAdapter referenceRVAdapter = new ReferenceRecyclerViewAdapter(al.getReferences());
      referenceRVAdapter.setClickListener((reference, holder) -> displayReference(reference));
      recyclerView.setAdapter(referenceRVAdapter);
    }
  }

  protected void displayReference(Alert reference) {
    Intent alertIntent = new Intent(this, ReferenceViewerActivity.class);
    alertIntent.putExtra("locIndex", locationIndex);
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
    sender.setText(Html.fromHtml("<a href=https://www.weather.gov/" + al.getSenderCode() + ">" + al.getSender()));
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

  private boolean stopped;
  @Override
  protected void onStop() {
    super.onStop();
    stopped = true;
    refresher.stop();
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (stopped) refresher.startAndRefresh();
    else refresher.start();
  }
}