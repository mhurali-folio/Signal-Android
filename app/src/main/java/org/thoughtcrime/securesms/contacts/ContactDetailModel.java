package org.thoughtcrime.securesms.contacts;

import java.util.ArrayList;

class PeepBaseData {
  int type;
}

class PeepPhoneNumber extends PeepBaseData {
  String number;
}

class PeepAddress extends PeepBaseData {
  String address;
}

class PeepEmail extends PeepBaseData {
  String email;
}

class PeepStructuredName {
  String name;
}

class PeepWorkInfo {
  String company;
  String title;
}

class PeepLocalData {
  private Double trust_level = Double.valueOf(0f);
  private Double intimacy_level = Double.valueOf(0f);
  private String bio = "", notes = "", dateWeMet;

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
}

public class ContactDetailModel {
  ArrayList<PeepPhoneNumber> peepPhoneNumbers;
  ArrayList<PeepAddress>     peepAddresses;
  ArrayList<PeepEmail>       peepEmails;
  PeepStructuredName         peepStructuredName;
  PeepWorkInfo               peepWorkInfo;
  PeepLocalData              peepLocalData;

  public void addPeepPhoneNumber(PeepPhoneNumber phoneNumber) {
    if (peepPhoneNumbers == null) {
      peepPhoneNumbers = new ArrayList<>();
    }
    peepPhoneNumbers.add(phoneNumber);
  }

  public void addPeepAddress(PeepAddress address) {
    if (peepAddresses == null) {
      peepAddresses = new ArrayList<>();
    }
    peepAddresses.add(address);
  }

  public void addPeepEmails(PeepEmail email) {
    if (peepEmails == null) {
      peepEmails = new ArrayList<>();
    }
    peepEmails.add(email);
  }

  public void setPeepStructuredName(PeepStructuredName peepStructuredName) {
    this.peepStructuredName = peepStructuredName;
  }

  public void setPeepWorkInfo(PeepWorkInfo peepWorkInfo) {
    this.peepWorkInfo = peepWorkInfo;
  }

  public PeepStructuredName getPeepStructuredName() {
    return peepStructuredName;
  }

  public PeepWorkInfo getPeepWorkInfo() {
    return peepWorkInfo;
  }

  public ArrayList<PeepPhoneNumber> getPeepPhoneNumbers() {
    if(peepPhoneNumbers == null) {
      peepPhoneNumbers = new ArrayList<>();
    }
    return peepPhoneNumbers;
  }

  public ArrayList<PeepEmail> getPeepEmails() {
    if(peepEmails == null) {
      peepEmails = new ArrayList<>();
    }
    return peepEmails;
  }

  public ArrayList<PeepAddress> getPeepAddresses() {
    if(peepAddresses == null) {
      peepAddresses = new ArrayList<>();
    }
    return peepAddresses;
  }

  public PeepLocalData getPeepLocalData() {
    return peepLocalData;
  }
}
