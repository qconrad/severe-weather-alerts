package com.severeweatheralerts.Alerts;

import android.graphics.Color;

import com.severeweatheralerts.Graphics.Polygon.Polygon;

import java.util.ArrayList;
import java.util.Date;

public abstract class Alert implements Comparable<Alert> {
  public enum Severity { UNKNOWN, MINOR, MODERATE, SEVERE, EXTREME }
  public enum Type { POST, UPDATE, CANCEL }
  private String name;
  private String nwsId;
  private String smallHeadline;
  private String largeHeadline;
  private String description;
  private String instruction;
  private Type type;
  private String sender;
  private String senderCode;
  private Date sentTime;
  private Date startTime;
  private Date endTime;
  private Date expectedUpdateTime;
  private Severity severity;
  private Alert replacedBy;
  private final ArrayList<Alert> references = new ArrayList<>();
  private final ArrayList<Polygon> polygons = new ArrayList<>();
  private final ArrayList<String> zoneLinks = new ArrayList<>();

  public Alert() {}

  public abstract int getColor();
  public abstract int getIcon();

  public int getColorAt(Date date) {
    if (!activeAt(date)) return getExpiredColor();
    return getColor();
  }

  // Sort by send time
  @Override
  public int compareTo(Alert alert) {
    if (alert.getSentTime() == null) return 0;
    return alert.getSentTime().compareTo(getSentTime());
  }

  protected int getExpiredColor() {
    return Color.parseColor("#515151");
  }

  public boolean activeAt(Date date) {
    if (isCancel() || isReplaced()) return false;
    else return endsBefore(date);
  }

  private boolean isCancel() {
    return type.equals(Type.CANCEL);
  }

  public int getReferenceCount() { return references.size(); }
  public void addReference(Alert reference) { references.add(reference); }
  public Alert getReference(int index) { return references.get(index); }
  public ArrayList<Alert> getReferences() { return references; }

  public void addPolygon(Polygon polygon) { polygons.add(polygon); }
  public boolean hasGeometry() { return polygons.size() > 0; }
  public Polygon getPolygon(int i) { return polygons.get(i); }
  public ArrayList<Polygon> getPolygons() { return polygons; }
  public int getPolygonCount() { return polygons.size(); }

  public int getZoneLinkCount() { return zoneLinks.size(); }
  public void addZoneLink(String link) { zoneLinks.add(link); }
  public String getZone(int i) { return zoneLinks.get(i); }
  public ArrayList<String> getZones() { return zoneLinks; }

  public void setReplacedBy(Alert replacedBy) {
    this.replacedBy = replacedBy;
  }

  public Alert getReplacedBy() {
    return replacedBy;
  }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public void setNwsId(String nwsId) { this.nwsId = nwsId; }
  public String getNwsId() { return nwsId; }

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

  public Date getStartTime() { return startTime; }
  public void setStartTime(Date startTime) { this.startTime = startTime; }

  public Date getEndTime() { return endTime; }
  public void setEndTime(Date endTime) { this.endTime = endTime; }

  public boolean isReplaced() { return hasReplacement(); }

  private boolean hasReplacement() {
    return replacedBy != null;
  }

  public Date getExpectedUpdateTime() { return expectedUpdateTime; }
  public void setExpectedUpdateTime(Date expectedUpdateTime) { this.expectedUpdateTime = expectedUpdateTime; }

  public String getSender() { return sender; }
  public void setSender(String sender) { this.sender = sender; }

  public String getSenderCode() { return senderCode; }
  public void setSenderCode(String senderCode) { this.senderCode = senderCode; }

  public boolean isLikelyLastUpdate() {
    if (expectedUpdateTime == null) return true;
    return expectedUpdateTime.getTime() >= endTime.getTime();
  }

  public boolean startsBefore(Date date) {
    return date.getTime() < startTime.getTime();
  }

  public boolean endsBefore(Date date) {
    return date.getTime() < endTime.getTime();
  }
}
