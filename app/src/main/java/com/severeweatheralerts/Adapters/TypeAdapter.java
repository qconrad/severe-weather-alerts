package com.severeweatheralerts.Adapters;

import com.severeweatheralerts.Alert;

public class TypeAdapter {
  String type;

  public TypeAdapter(String type) {
    this.type = type;
  }

  public Alert.Type adaptType() {
    if (typeIsNull()) return null;
    if (typeIs("Cancel")) return Alert.Type.CANCEL;
    else if (typeIs("Update")) return Alert.Type.UPDATE;
    else return Alert.Type.POST;
  }

  private boolean typeIs(String typeString) {
    return type.equals(typeString);
  }

  private boolean typeIsNull() {
    return type == null;
  }
}
