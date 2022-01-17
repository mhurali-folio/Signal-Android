package org.thoughtcrime.securesms.database.model;

import org.thoughtcrime.securesms.recipients.RecipientId;

public class RecipientModel {
  public RecipientId recipientId;
  public String threadId;

  public RecipientModel(RecipientId recipientId, String threadId) {
    this.recipientId = recipientId;
    this.threadId = threadId;
  }
}
