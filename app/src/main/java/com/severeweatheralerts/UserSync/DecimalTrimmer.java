package com.severeweatheralerts.UserSync;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalTrimmer {
  private double num;
  public DecimalTrimmer(double num) {
    this.num = num;
  }

  public double trim(int digits) {
    return setRoundingMode(digits, getBigDecimal()).doubleValue();
  }

  private BigDecimal getBigDecimal() {
    return BigDecimal.valueOf(num);
  }

  private BigDecimal setRoundingMode(int digits, BigDecimal bDecimal) {
    return bDecimal.setScale(digits, getRoundingMode());
  }

  protected RoundingMode getRoundingMode() {
    return RoundingMode.HALF_UP;
  }
}
