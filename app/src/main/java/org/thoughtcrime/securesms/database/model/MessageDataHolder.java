package org.thoughtcrime.securesms.database.model;

import org.thoughtcrime.securesms.groups.GroupId;
import org.thoughtcrime.securesms.recipients.RecipientId;

public class MessageDataHolder {
  public RecipientId recipientId;
  public Boolean     isGroup;
  public String      groupTitle;
  public String      userName;
  public String      messageContent;
  public GroupId      groupId;

  public MessageDataHolder(RecipientId recipientId, Boolean isGroup, String groupTitle,
                           String userName, String messageContent, GroupId groupId) {
    this.recipientId    = recipientId;
    this.isGroup        = isGroup;
    this.groupTitle     = groupTitle;
    this.userName       = userName;
    this.messageContent = messageContent;
    this.groupId        = groupId;
  }
}
