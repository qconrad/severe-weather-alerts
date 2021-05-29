package com.severeweatheralerts.Graphics.ViewInflaters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.severeweatheralerts.Graphics.ViewInflaters.ImageGraphic;
import com.severeweatheralerts.R;

public class OneHourPrecipitationGraphic extends ImageGraphic {
  public OneHourPrecipitationGraphic(Context context, Bitmap image, String subtext) {
    super(context, image, subtext);
  }

  @Override
  public View getView() {
    LinearLayout linearLayout = new LinearLayout(context);
    linearLayout.setOrientation(LinearLayout.VERTICAL);
    linearLayout.addView(LayoutInflater.from(context).inflate(R.layout.one_hour_graph_labels, null));
    linearLayout.addView(getImageView(5.0f));
    return linearLayout;
  }
}
