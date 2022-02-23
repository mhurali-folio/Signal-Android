package org.thoughtcrime.securesms.postcreation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.TransportOptions;
import org.thoughtcrime.securesms.contacts.ContactAccessor;
import org.thoughtcrime.securesms.contacts.ContactsDatabase;
import org.thoughtcrime.securesms.contactshare.Contact;
import org.thoughtcrime.securesms.database.RecipientDatabase;
import org.thoughtcrime.securesms.database.SignalDatabase;
import org.thoughtcrime.securesms.database.ThreadDatabase;
import org.thoughtcrime.securesms.database.model.Mention;
import org.thoughtcrime.securesms.database.model.RecipientModel;
import org.thoughtcrime.securesms.dependencies.ApplicationDependencies;
import org.thoughtcrime.securesms.linkpreview.LinkPreview;
import org.thoughtcrime.securesms.mms.OutgoingMediaMessage;
import org.thoughtcrime.securesms.mms.OutgoingSecureMediaMessage;
import org.thoughtcrime.securesms.mms.QuoteModel;
import org.thoughtcrime.securesms.mms.SlideDeck;
import org.thoughtcrime.securesms.newsfeed.NewsFeedActivity;
import org.thoughtcrime.securesms.permissions.Permissions;
import org.thoughtcrime.securesms.recipients.LiveRecipient;
import org.thoughtcrime.securesms.recipients.Recipient;
import org.thoughtcrime.securesms.recipients.RecipientId;
import org.thoughtcrime.securesms.sms.MessageSender;
import org.thoughtcrime.securesms.sms.OutgoingEncryptedMessage;
import org.thoughtcrime.securesms.sms.OutgoingTextMessage;
import org.thoughtcrime.securesms.util.MessageUtil;
import org.thoughtcrime.securesms.util.concurrent.SettableFuture;
import org.thoughtcrime.securesms.util.concurrent.SimpleTask;
import org.whispersystems.signalservice.api.storage.SignalContactRecord;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class PostCreationActivity extends AppCompatActivity {
  PostRecipientAdapter dataAdapter = null;
  ArrayList<PostRecipient> postRecipients;
  ThreadDatabase threadDatabase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.post_creation_activity);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    this.getSupportActionBar().setTitle(R.string.PostCreationActivity__new_message);

    this.setupRecipientList();
    EditText messageBox = findViewById(R.id.message_box);
    FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.send_message_fab);

    Context context = this;
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(messageBox.getText().toString().equals("")) {
          Toast.makeText(context, R.string.PostCreationActivity__enter_message_toast, Toast.LENGTH_SHORT).show();
        }
        else if(!postRecipients.stream().anyMatch(pR -> pR.isSelected())) {
          Toast.makeText(context, R.string.PostCreationActivity__select_peeps_toast, Toast.LENGTH_SHORT).show();
        }
        else {
          for (PostRecipient postRecipient : postRecipients) {
            SlideDeck slideDeck = new SlideDeck();

            if(postRecipient.isSelected()) {
              if(postRecipient.recipient.isGroup()) {
                sendMediaMessage(postRecipient.recipient.getId(), false, messageBox.getText().toString(), slideDeck, null,
                                 Collections.emptyList(),
                                 Collections.emptyList(),
                                 Collections.emptyList(),
                                 0,
                                 false,
                                 -1,
                                 null);
              } else {
                sendTextMessage(postRecipient.recipient.getId(), false, messageBox.getText().toString(), 0, -1, null);
              }
              postRecipient.setSelected(false);
            }
          }
          dataAdapter.notifyDataSetChanged();
          messageBox.setText("");
          startActivity(new Intent(context, NewsFeedActivity.class));
        }
      }
    });
  }

  private void setupRecipientList() {
    threadDatabase   = SignalDatabase.threads();
    Set<RecipientModel> threadRecipients = threadDatabase.getAllThreadRecipientsData();

    Recipient recipient;
    postRecipients = new ArrayList();

    for(RecipientModel recipientModel : threadRecipients){
      recipient = Recipient.live(recipientModel.recipientId).get();
      if(recipient.isGroup()) {
        postRecipients.add(new PostRecipient(recipient.getDisplayNameOrUsername(getApplicationContext()), recipient, false));
      }
    }

    RecipientDatabase recipientDatabase = SignalDatabase.recipients();
    recipientDatabase.getRegistered();

    for(RecipientId recipientId : recipientDatabase.getRegistered()){
      recipient = Recipient.live(recipientId).get();

      if(!recipient.isGroup() && !Recipient.self().getId().equals(recipientId)) {
        postRecipients.add(new PostRecipient(recipient.getDisplayNameOrUsername(getApplicationContext()), recipient, false));
      }
    }

    dataAdapter = new PostRecipientAdapter(this,
                                           R.layout.post_recipient_holder, postRecipients);

    ListView listView = (ListView) findViewById(R.id.listView1);
    listView.setAdapter(dataAdapter);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    super.onOptionsItemSelected(item);

    switch (item.getItemId()) {
      case android.R.id.home:   super.onBackPressed(); return true;
    }

    return false;
  }

  private void sendMediaMessage(@NonNull RecipientId recipientId,
                                                  final boolean forceSms,
                                                  @NonNull String body,
                                                  SlideDeck slideDeck,
                                                  QuoteModel quote,
                                                  List<Contact> contacts,
                                                  List<LinkPreview> previews,
                                                  List<Mention> mentions,
                                                  final long expiresIn,
                                                  final boolean viewOnce,
                                                  final int subscriptionId,
                                                  final @Nullable String metricId)
  {
    final boolean sendPush = true;

    if (sendPush) {
      TransportOptions    transportOptions = new TransportOptions(this, false);
      MessageUtil.SplitResult splitMessage     = MessageUtil.getSplitMessage(this, body, transportOptions.getSelectedTransport().calculateCharacters(body).maxPrimaryMessageSize);
      body = splitMessage.getBody();

      if (splitMessage.getTextSlide().isPresent()) {
        slideDeck.addSlide(splitMessage.getTextSlide().get());
      }
    }

    Recipient recipient = Recipient.live(recipientId).get();
    final long thread   = threadDatabase.getOrCreateThreadIdFor(recipient);

    OutgoingMediaMessage outgoingMessageCandidate = new OutgoingMediaMessage(Recipient.resolved(recipientId), slideDeck, body, System.currentTimeMillis(), subscriptionId,
                                                                             expiresIn, viewOnce, recipient.isGroup() ? 1 : 2, quote, contacts, previews, mentions);

    final SettableFuture<Void> future  = new SettableFuture<>();
    final Context              context = getApplicationContext();

    final OutgoingMediaMessage outgoingMessage;

    if (sendPush) {
      outgoingMessage = new OutgoingSecureMediaMessage(outgoingMessageCandidate);
      ApplicationDependencies.getTypingStatusSender().onTypingStopped(thread);
    } else {
      outgoingMessage = outgoingMessageCandidate;
    }

    Permissions.with(this)
               .request(Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS)
               .ifNecessary(!sendPush)
               .withPermanentDenialDialog(getString(R.string.ConversationActivity_signal_needs_sms_permission_in_order_to_send_an_sms))
               .onAllGranted(() -> {
                 SimpleTask.run(() -> {
                   return MessageSender.send(context, outgoingMessage, thread, forceSms, metricId, null);
                 }, result -> {
                   future.set(null);
                 });
               })
               .onAnyDenied(() -> future.set(null))
               .execute();
  }

  private void sendTextMessage(@NonNull RecipientId recipientId,
                               final boolean forceSms,
                               @NonNull String body,
                               final long expiresIn,
                               final int subscriptionId,
                               final @Nullable String metricId)
  {
    final Context context     = getApplicationContext();
    final String  messageBody = body;
    final boolean sendPush    = true;

    OutgoingTextMessage message;

    LiveRecipient recipient = Recipient.live(recipientId);
    final long    thread    = threadDatabase.getOrCreateThreadIdFor(recipient.get());


    if (sendPush) {
      message = new OutgoingEncryptedMessage(recipient.get(), messageBody, expiresIn);
      ApplicationDependencies.getTypingStatusSender().onTypingStopped(thread);
    } else {
      message = new OutgoingTextMessage(recipient.get(), messageBody, expiresIn, subscriptionId);
    }

    Permissions.with(this)
               .request(Manifest.permission.SEND_SMS)
               .ifNecessary(!sendPush)
               .withPermanentDenialDialog(getString(R.string.ConversationActivity_signal_needs_sms_permission_in_order_to_send_an_sms))
               .onAllGranted(() -> {
                 final long id = new SecureRandom().nextLong();
                 SimpleTask.run(() -> {
                   return MessageSender.send(context, message, thread, forceSms, metricId, null);
                 },  result -> {
                 });
               })
               .execute();
  }
}