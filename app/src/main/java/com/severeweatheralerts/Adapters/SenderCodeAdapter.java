package com.severeweatheralerts.Adapters;

public class SenderCodeAdapter {
  private final String senderCode;

  public SenderCodeAdapter(String senderCode) {
    this.senderCode = senderCode;
  }

  public String adapterSenderCode() {
    if (senderCode == null) return null;
    return senderCode.toLowerCase();
  }
}
