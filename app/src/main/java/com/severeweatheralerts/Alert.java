package com.severeweatheralerts;

import java.util.Date;

public class Alert {
  public enum Severity { UNKNOWN, MINOR, MODERATE, SEVERE, EXTREME }
  public enum Type { POST, UPDATE, CANCEL }
  private String name;
  private String smallHeadline;
  private String largeHeadline;
  private String description;
  private String instruction;
  private Type type;
  private Date sentTime;
  private Date beginTime;
  private Date endTime;
  private Date expectedUpdateTime;
  private Severity severity;

  public Alert() {}

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public Severity getSeverity() { return severity; }
  public void setSeverity(Severity severity) { this.severity = severity; }

  public String getLargeHeadline() { return largeHeadline; }
  public void setSmallHeadline(String smallHeadline) { this.smallHeadline = smallHeadline; }

  public void setLargeHeadline(String largeHeadline) { this.largeHeadline = largeHeadline; }
  public String getSmallHeadline() { return smallHeadline; }

  public String getInstruction() { return instruction; }
  public void setInstruction(String instruction) { this.instruction = instruction; }

  public Type getType() { return type; }
  public void setType(Type type) { this.type = type; }

  public Date getSentTime() { return sentTime; }
  public void setSentTime(Date sentTime) { this.sentTime = sentTime; }

  public Date getStartTime() { return beginTime; }
  public void setStartTime(Date beginTime) { this.beginTime = beginTime; }

  public Date getEndTime() { return endTime; }
  public void setEndTime(Date endTime) { this.endTime = endTime; }

  public Date getExpectedUpdateTime() { return expectedUpdateTime; }
  public void setExpectedUpdateTime(Date expectedUpdateTime) { this.expectedUpdateTime = expectedUpdateTime; }

  public boolean isLikelyLastUpdate() {
    if (expectedUpdateTime == null) return true;
    return expectedUpdateTime.getTime() >= endTime.getTime();
  }
}
