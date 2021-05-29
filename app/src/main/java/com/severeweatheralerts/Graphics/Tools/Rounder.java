package com.severeweatheralerts.Graphics.Tools;

public class Rounder {
  private final int roundAmount;
  private final double number;

  public Rounder(double number, int roundAmount) {
    this.number = number;
    this.roundAmount = roundAmount;
  }

  public double getRounded() {
    double amount = Math.pow(10, roundAmount);
    return Math.round(number * amount) / amount;
  }
}
