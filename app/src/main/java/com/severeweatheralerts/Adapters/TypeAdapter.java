package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.Alerts.Alert;

import java.util.Date;

public class TypeAdapter {
  String type;
  private final Date sentTime;
  private final Date endTime;

  public TypeAdapter(String type, Date sentTime, Date endTime) {
    this.type = type;
    this.sentTime = sentTime;
    this.endTime = endTime;
  }

  public Alert.Type adaptType() {
    if (typeIsNull()) return null;
    if (timesNotNull() && sentTime.after(endTime)) return Alert.Type.UPDATE;
    if (typeIs("Cancel")) return Alert.Type.CANCEL;
    else if (typeIs("Update")) return Alert.Type.UPDATE;
    else return Alert.Type.POST;
  }

  private boolean timesNotNull() {
    return sentTime != null && endTime != null;
  }

  private boolean typeIs(String typeString) {
    return type.equals(typeString);
  }

  private boolean typeIsNull() {
    return type == null;
  }
}
