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

public class ContactDetailModel {
  ArrayList<PeepPhoneNumber> peepPhoneNumbers;
  ArrayList<PeepAddress>     peepAddresses;
  ArrayList<PeepEmail>       peepEmails;
  PeepStructuredName         peepStructuredName;
  PeepWorkInfo               peepWorkInfo;

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
    return peepPhoneNumbers;
  }

  public ArrayList<PeepEmail> getPeepEmails() {
    return peepEmails;
  }

  public ArrayList<PeepAddress> getPeepAddresses() {
    return peepAddresses;
  }
}
