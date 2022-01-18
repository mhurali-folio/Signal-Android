package org.thoughtcrime.securesms.newsfeed;

import org.thoughtcrime.securesms.database.model.MessageDataHolder;
import org.thoughtcrime.securesms.groups.GroupId;
import org.thoughtcrime.securesms.recipients.Recipient;
import org.thoughtcrime.securesms.recipients.RecipientId;

import java.util.ArrayList;

public class FeedItem {
  Recipient recipient = null;
  ArrayList<MessageDataHolder> feeds;

  public FeedItem(Recipient recipient, ArrayList<MessageDataHolder> feeds) {
    this.recipient = recipient;
    this.feeds = feeds;
  }
}
