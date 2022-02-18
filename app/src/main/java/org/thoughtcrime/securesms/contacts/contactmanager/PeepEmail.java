package org.thoughtcrime.securesms.contacts.contactmanager;

import java.io.Serializable;

public class PeepEmail extends PeepBaseData implements Serializable {
  String email;

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
}
