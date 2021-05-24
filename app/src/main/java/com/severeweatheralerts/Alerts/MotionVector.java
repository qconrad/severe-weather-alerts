package com.severeweatheralerts.Alerts;

public class MotionVector {
  private final int heading;
  private final int velocity;

  public MotionVector(int heading, int velocity) {
    this.heading = heading;
    this.velocity = velocity;
  }

  public int getHeading() {
    return heading;
  }

  public int getVelocity() {
    return velocity;
  }
}
