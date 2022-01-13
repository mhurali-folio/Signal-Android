package org.thoughtcrime.securesms.postcreation;

import org.thoughtcrime.securesms.recipients.Recipient;

public class PostRecipient {
  String title = null;
  Recipient recipient = null;
  boolean selected = false;

  public PostRecipient(String title, Recipient recipient, boolean selected) {
    super();
    this.title = title;
    this.recipient = recipient;
    this.selected = selected;
  }

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isSelected() {
    return selected;
  }
  public void setSelected(boolean selected) {
    this.selected = selected;
  }
}
