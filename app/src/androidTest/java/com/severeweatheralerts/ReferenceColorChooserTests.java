package com.severeweatheralerts;

import android.graphics.Color;

import com.severeweatheralerts.Alerts.Alert;
import com.severeweatheralerts.Alerts.DefaultAlert;
import com.severeweatheralerts.RecyclerViews.Reference.ReferenceColorChooser;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ReferenceColorChooserTests {
  int EXPIRED_COLOR = Color.parseColor("#515151");

  @Test
  public void getColor_CancelProvided_ExpiredColor() {
    DefaultAlert alert = new DefaultAlert();
    alert.setType(Alert.Type.CANCEL);
    DefaultAlert reference = new DefaultAlert();
    reference.setReplacedBy(alert);
    ReferenceColorChooser referenceColorChooser = new ReferenceColorChooser(reference);
    assertEquals(EXPIRED_COLOR, referenceColorChooser.getColorAt(new Date(0)));
  }

  @Test
  public void getColor_PostNotExpired_DefaultColor0() {
    DefaultAlert reference = new DefaultAlert();
    DefaultAlert alert = new DefaultAlert();
    alert.setEndTime(new Date(15));
    alert.setType(Alert.Type.POST);
    reference.setReplacedBy(alert);
    ReferenceColorChooser referenceColorChooser = new ReferenceColorChooser(reference);
    assertEquals(0, referenceColorChooser.getColorAt(new Date(0)));
  }
}
