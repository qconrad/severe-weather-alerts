package com.severeweatheralerts.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.android.volley.VolleyError;
import com.severeweatheralerts.Adapters.GeoJSONPolygon;
import com.severeweatheralerts.Adapters.PolygonAdapter;
import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.AlertListTools.AlertFinder;
import com.severeweatheralerts.BoundAspectRatio;
import com.severeweatheralerts.Constants;
import com.severeweatheralerts.Graphics.AlertArea;
import com.severeweatheralerts.Graphics.BoundMargin;
import com.severeweatheralerts.Graphics.BitmapCombiner;
import com.severeweatheralerts.Graphics.Bounds;
import com.severeweatheralerts.Graphics.Graphic;
import com.severeweatheralerts.Graphics.GraphicGenerator;
import com.severeweatheralerts.Graphics.GraphicType;
import com.severeweatheralerts.Graphics.ZoneDrawer;
import com.severeweatheralerts.PolygonListBoundCalculator;
import com.severeweatheralerts.Graphics.URLGenerator;
import com.severeweatheralerts.JSONParsing.GeometryParser;
import com.severeweatheralerts.Location.Location;
import com.severeweatheralerts.Location.LocationsDao;
import com.severeweatheralerts.Networking.FetchServices.FetchCallback;
import com.severeweatheralerts.Networking.FetchServices.ImageFetchService;
import com.severeweatheralerts.Networking.FetchServices.ListFetch;
import com.severeweatheralerts.R;
import com.severeweatheralerts.RecyclerViews.Reference.ReferenceRecyclerViewAdapter;
import com.severeweatheralerts.TextGeneraters.NextUpdate;
import com.severeweatheralerts.TextGeneraters.Time.AlertTime;
import com.severeweatheralerts.TextGeneraters.Time.TimeGenerator;
import com.severeweatheralerts.TextUtils.KeywordEmphasizer;

import org.json.JSONException;
import org.json.JSONObject;

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
    GraphicType graphicType = new AlertArea();
    View graphicView = createGraphicView();
    displayGraphicTypeAndLoading(graphicType, graphicView);
    new GraphicGenerator(graphicType).generate(graphic -> {
      displaySubtext(graphicView, graphic);
      hideProgressBar(graphicView);
    });
  }

  private void displaySubtext(View graphicView, Graphic graphic) {
    if (graphic.hasSubtext()) {
      TextView subTextTv = graphicView.findViewById(R.id.gfx_subtext);
      subTextTv.setVisibility(View.VISIBLE);
      subTextTv.setText(graphic.getSubtext());
    }
  }

  private void hideProgressBar(View graphicView) {
    graphicView.findViewById(R.id.progress).setVisibility(View.GONE);
  }

  private void displayGraphicTypeAndLoading(GraphicType graphicType, View graphicView) {
    setTitle(graphicView, graphicType.getTitle());
    setProgressBarColor(graphicView, al.getColor());
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

  private void fetchZones(View graphicView) {
    ListFetch fetchService = new ListFetch(this, al.getZones());
    fetchService.setUserAgent(Constants.USER_AGENT);
    fetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        parseZones((ArrayList<String>) response);
        displayGraphic(graphicView);
      }

      @Override public void error(VolleyError error) { }
    });
  }

  private void parseZones(ArrayList<String> response) {
    for (String zone : response) {
      ArrayList<GeoJSONPolygon> geometry = null;
      try { geometry = new GeometryParser(new JSONObject(zone).getJSONObject("geometry")).parseGeometry(); }
      catch (JSONException e) { e.printStackTrace(); }
      for (int i = 0; i < geometry.size(); i++)
        al.addPolygon(PolygonAdapter.toMercatorPolygon(geometry.get(i)));
    }
  }

  private void displayGraphic(View graphicView) {
    Bounds bounds = new BoundMargin(new BoundAspectRatio(new PolygonListBoundCalculator(al.getPolygons()).getBounds()).equalize(), Constants.DEFAULT_GRAPHIC_MARGIN).getBounds();
    String url = new URLGenerator().getCountyMap(bounds);
    ImageFetchService imageFetchService = new ImageFetchService(this, url);
    imageFetchService.setUserAgent(Constants.USER_AGENT);
    imageFetchService.fetch(new FetchCallback() {
      @Override
      public void success(Object response) {
        Bitmap baseCountyMap = (Bitmap) response;
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(baseCountyMap);
        Bitmap zonePolygons = new ZoneDrawer(al.getPolygons(), al.getColor(), bounds).getBitmap();
        bitmaps.add(zonePolygons);
        ImageView iv = graphicView.findViewById(R.id.gfx_image);
        iv.setImageBitmap(new BitmapCombiner(bitmaps).combine());

        graphicView.findViewById(R.id.progress).setVisibility(View.GONE);
      }

      @Override
      public void error(VolleyError error) {
        System.out.println(error.getMessage());
      }
    });
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