package com.severeweatheralerts.Graphics.GraphicGeneration;

import com.severeweatheralerts.Graphics.GridData.Parameter;

public class SumCalculator {
  private double sum = 0.0;

  public SumCalculator(Parameter parameter) {
    computeSum(parameter);
  }

  private void computeSum(Parameter parameter) {
    for (int i = 0; i < parameter.getCount(); i++)
      sum += parameter.get(i).getValue();
  }

  public double getSum() {
    return sum;
  }
}
