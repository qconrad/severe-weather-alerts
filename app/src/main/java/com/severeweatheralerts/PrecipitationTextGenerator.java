package com.severeweatheralerts;

import com.severeweatheralerts.Graphics.GridData.ForecastTime;
import com.severeweatheralerts.TimeFormatters.RelativeTimeFormatter;

import java.util.ArrayList;
import java.util.Date;

public class PrecipitationTextGenerator {
  private final ArrayList<ForecastTime> forecast;
  private final Date date;

  public PrecipitationTextGenerator(ArrayList<ForecastTime> forecast, Date date) {
    this.forecast = forecast;
    this.date = date;
  }

  public String getText() {
    if (forecast.size() <= 0) return "No forecast found";
    String text = "";
    if (forecast.get(0).getValue() >= 2.5) {
      for (int i = 0; i < forecast.size(); i++) {
        if (forecast.get(i).getValue() < 2.5) {
          String formattedString = new RelativeTimeFormatter(date, forecast.get(i).getDate()).getFormattedString();
          text += "Heavy rain for " + formattedString;
          break;
        }
      }
    } else {
      for (int i = 0; i < forecast.size(); i++) {
        if (forecast.get(i).getValue() >= 2.5) {
          String formattedString = new RelativeTimeFormatter(date, forecast.get(i).getDate()).getFormattedString();
          text += "Heavy rain in " + formattedString;
          break;
        }
      }
    }
    if (forecast.get(0).getValue() >= 3.5) {
      for (int i = 0; i < forecast.size(); i++) {
        if (forecast.get(i).getValue() < 3.5) {
          String formattedString = new RelativeTimeFormatter(date, forecast.get(i).getDate()).getFormattedString();
          text += ", hail for " + formattedString;
          break;
        }
      }
    } else {
      for (int i = 0; i < forecast.size(); i++) {
        if (forecast.get(i).getValue() >= 3.5) {
          String formattedString = new RelativeTimeFormatter(date, forecast.get(i).getDate()).getFormattedString();
          text += ", hail in " + formattedString;
          break;
        }
      }
    }
    if (text.equals("")) {
      for (int i = 0; i < forecast.size(); i++) {
        if (forecast.get(i).getValue() < 1.5) {
          String formattedString = new RelativeTimeFormatter(date, forecast.get(i).getDate()).getFormattedString();
          return "Light to moderate rain for " + formattedString;
        }
      }
      return "Light to moderate rain";
    }
    return text;
  }
}
