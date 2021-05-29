package com.severeweatheralerts;

import android.graphics.Color;

import com.severeweatheralerts.Graphics.Tools.ColorMap;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ColorMapTests {
  @Test
  public void TwoColors_Value0ReturnsFirstColor() {
    ArrayList<Integer> colors = new ArrayList<>();
    colors.add(Color.GREEN);
    colors.add(Color.RED);
    ColorMap colorMap = new ColorMap(colors, 1.0);
    assertEquals(Color.GREEN, colorMap.getValue(0.0));
  }

  @Test
  public void colorsSwitched_StillFirst() {
    ArrayList<Integer> colors = new ArrayList<>();
    colors.add(Color.RED);
    colors.add(Color.GREEN);
    ColorMap colorMap = new ColorMap(colors, 1.0);
    assertEquals(Color.RED, colorMap.getValue(0.0));
  }

  @Test
  public void valueOne_secondColor() {
    ArrayList<Integer> colors = new ArrayList<>();
    colors.add(Color.RED);
    colors.add(Color.GREEN);
    ColorMap colorMap = new ColorMap(colors, 1.0);
    assertEquals(Color.GREEN, colorMap.getValue(1.0));
  }

  @Test
  public void anotherColor_ReturnsLast() {
    ArrayList<Integer> colors = new ArrayList<>();
    colors.add(Color.RED);
    colors.add(Color.GREEN);
    colors.add(Color.BLUE);
    ColorMap colorMap = new ColorMap(colors, 1.0);
    assertEquals(Color.BLUE, colorMap.getValue(1.0));
  }

  @Test
  public void ZeroPointFive_ReturnsMiddleColor() {
    ArrayList<Integer> colors = new ArrayList<>();
    colors.add(Color.RED);
    colors.add(Color.GREEN);
    colors.add(Color.BLUE);
    ColorMap colorMap = new ColorMap(colors, 1.0);
    assertEquals(Color.GREEN, colorMap.getValue(0.5));
  }

  @Test
  public void colorInterpolated() {
    ArrayList<Integer> colors = new ArrayList<>();
    colors.add(Color.RED);
    colors.add(Color.GREEN);
    ColorMap colorMap = new ColorMap(colors, 1.0);
    assertEquals(Color.argb(255, 128, 128, 0), colorMap.getValue(0.5));
  }

  @Test
  public void differentColorInterpolated() {
    ArrayList<Integer> colors = new ArrayList<>();
    colors.add(Color.BLUE);
    colors.add(Color.GREEN);
    ColorMap colorMap = new ColorMap(colors, 1.0);
    assertEquals(Color.argb(255, 0, 128, 128), colorMap.getValue(0.5));
  }

  @Test
  public void differentMaxValue() {
    ArrayList<Integer> colors = new ArrayList<>();
    colors.add(Color.BLUE);
    colors.add(Color.GREEN);
    ColorMap colorMap = new ColorMap(colors, 2.0);
    assertEquals(Color.GREEN, colorMap.getValue(2.0));
  }

  @Test
  public void differentIndexForInterpolation() {
    ArrayList<Integer> colors = new ArrayList<>();
    colors.add(Color.BLUE);
    colors.add(Color.BLUE);
    colors.add(Color.GREEN);
    ColorMap colorMap = new ColorMap(colors, 1.0);
    assertEquals(Color.argb(255, 0, 128, 128), colorMap.getValue(0.75));
  }

  @Test
  public void differentInterpolationPoint() {
    ArrayList<Integer> colors = new ArrayList<>();
    colors.add(Color.BLUE);
    colors.add(Color.GREEN);
    ColorMap colorMap = new ColorMap(colors, 1.0);
    assertEquals(Color.argb(255, 0, 64, 191), colorMap.getValue(0.25));
  }

  @Test
  public void interpolateTransparent() {
    ArrayList<Integer> colors = new ArrayList<>();
    colors.add(Color.argb(0, 0, 0, 0));
    colors.add(Color.argb(255, 0, 0, 0));
    ColorMap colorMap = new ColorMap(colors, 1.0);
    assertEquals(Color.argb(128, 0, 0, 0), colorMap.getValue(0.5));
  }
}
