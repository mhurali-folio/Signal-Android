package org.thoughtcrime.securesms.contacts.contactmanager;

import java.util.ArrayList;

public class ContactDetailModel {
  private ArrayList<PeepPhoneNumber> peepPhoneNumbers;
  private ArrayList<PeepAddress>     peepAddresses;
  private ArrayList<PeepEmail>       peepEmails;
  private PeepStructuredName         peepStructuredName;
  private PeepWorkInfo               peepWorkInfo;
  public  PeepLocalData              peepLocalData;

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

  public void setPeepEmails(ArrayList<PeepEmail> peepEmails) {
    this.peepEmails = peepEmails;
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
