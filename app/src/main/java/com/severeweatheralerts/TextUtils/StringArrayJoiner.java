package com.severeweatheralerts.TextUtils;

public class StringArrayJoiner {
  private final String[] arr;

  public StringArrayJoiner(String[] arr) {
    this.arr = arr;
  }

  public String join() {
    StringBuilder joinedString = new StringBuilder();
    for (int i = 0; i < arr.length; i++) joinItem(joinedString, i);
    return joinedString.toString();
  }

  private void joinItem(StringBuilder joinedString, int i) {
    joinedString.append(arr[i]);
    if (notLastItem(i)) joinedString.append(" ");
  }

  private boolean notLastItem(int i) {
    return i < arr.length - 1;
  }
}
