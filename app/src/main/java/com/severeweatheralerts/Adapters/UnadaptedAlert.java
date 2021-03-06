package com.severeweatheralerts.Adapters;

import java.util.ArrayList;

public class UnadaptedAlert {
  private String name;
  private String id;
  private String type;
  private String description;
  private String instruction;
  private String sent;
  private String onset;
  private String expires;
  private String ends;
  private String nwsHeadline;
  private String severity;
  private String certainty;
  private String urgency;
  private String sender;
  private String senderCode;
  private String replacedBy;
  private String replacedAt;
  private GeoJSONPolygon polygon;
  private final ArrayList<String> references = new ArrayList<>();
  private final ArrayList<String> zoneLinks = new ArrayList<>();
  private String eventMotionDescription;

  public void setName(String name) { this.name = name; }
  public String getName() { return name; }

  public void setDescription(String description) { this.description = description; }
  public String getDescription() { return description; }

  public void setInstruction(String instruction) { this.instruction = instruction; }
  public String getInstruction() { return instruction; }

  public void setSent(String sent) { this.sent = sent; }
  public String getSent() { return sent; }

  public void setOnset(String onset) { this.onset = onset; }
  public String getOnset() { return onset; }

  public void setExpires(String expires) { this.expires = expires; }
  public String getExpires() { return expires; }

  public void setEnds(String ends) { this.ends = ends; }
  public String getEnds() { return ends; }

  public void setNwsHeadline(String nwsHeadline) { this.nwsHeadline = nwsHeadline; }
  public String getNwsHeadline() { return nwsHeadline; }

  public String getSeverity() { return severity; }
  public void setSeverity(String severity) { this.severity = severity; }

  public String getUrgency() { return urgency; }
  public void setUrgency(String urgency) { this.urgency = urgency; }

  public void setCertainty(String certainty) { this.certainty = certainty; }
  public String getCertainty() { return certainty; }

  public void setType(String type) { this.type = type; }
  public String getType() { return type; }

  public void setId(String id) { this.id = id; }
  public String getId() { return id; }

  public void addReferenceId(String referenceId) { references.add(referenceId); }
  public ArrayList<String> getReferences() { return references; }
  public String getReference(int index) { return references.get(index); }
  public int getReferenceCount() { return references.size(); }

  public void setSender(String sender) { this.sender = sender; }
  public String getSender() { return sender; }

  public void setSenderCode(String senderCode) { this.senderCode = senderCode; }
  public String getSenderCode() { return senderCode; }

  public boolean hasGeometry() {
    return polygon != null;
  }

  public void setPolygon(GeoJSONPolygon geoJSONPolygon) { polygon = geoJSONPolygon; }
  public GeoJSONPolygon getPolygon() { return polygon; }

  public int getZoneLinkCount() { return zoneLinks.size(); }
  public String getZoneLink(int i) { return zoneLinks.get(i); }
  public void addZoneLink(String zoneLink) { zoneLinks.add(zoneLink); }

  public String getReplacedBy() { return replacedBy; }
  public void setReplacedBy(String replacedBy) { this.replacedBy = replacedBy; }

  public String getReplacedAt() { return replacedAt; }
  public void setReplacedAt(String replacedAt) { this.replacedAt = replacedAt; }

  public String getMotionDescription() { return eventMotionDescription; }
  public void setMotionDescription(String eventMotionDescription) { this.eventMotionDescription = eventMotionDescription; }
}
