package org.thoughtcrime.securesms.contacts.contactmanager;

public class PeepLocalData {
  private Double trust_level;
  private Double intimacy_level;
  private String bio = "", notes = "", dateWeMet = "", tags = "";

  public Double getTrust_level() {
    return trust_level;
  }

  public String getBio() {
    return bio;
  }

  public Double getIntimacy_level() {
    return intimacy_level;
  }

  public String getNotes() {
    return notes;
  }

  public String getDateWeMet() {
    return dateWeMet;
  }

  public String getTags() {
    return tags;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public void setTrust_level(Double trust_level) {
    this.trust_level = trust_level;
  }

  public void setIntimacy_level(Double intimacy_level) {
    this.intimacy_level = intimacy_level;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setDateWeMet(String dateWeMet) {
    this.dateWeMet = dateWeMet;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }
}

