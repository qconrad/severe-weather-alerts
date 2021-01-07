package com.severeweatheralerts;

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
  private ArrayList<String> references = new ArrayList<>();

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

  public void setType(String type) { this.type = type; }
  public String getType() { return type; }

  public void setId(String id) { this.id = id; }
  public String getId() { return id; }

  public void addReferenceId(String referenceId) { references.add(referenceId); }
  public String getReference(int index) { return references.get(index); }

  public int getReferenceCount() { return references.size(); }
}
