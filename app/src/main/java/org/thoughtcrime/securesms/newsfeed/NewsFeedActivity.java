package org.thoughtcrime.securesms.newsfeed;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.TransportOptions;
import org.thoughtcrime.securesms.contactshare.Contact;
import org.thoughtcrime.securesms.database.MmsSmsDatabase;
import org.thoughtcrime.securesms.database.SignalDatabase;
import org.thoughtcrime.securesms.database.ThreadDatabase;
import org.thoughtcrime.securesms.database.model.Mention;
import org.thoughtcrime.securesms.dependencies.ApplicationDependencies;
import org.thoughtcrime.securesms.linkpreview.LinkPreview;
import org.thoughtcrime.securesms.mms.OutgoingMediaMessage;
import org.thoughtcrime.securesms.mms.OutgoingSecureMediaMessage;
import org.thoughtcrime.securesms.mms.QuoteModel;
import org.thoughtcrime.securesms.mms.SlideDeck;
import org.thoughtcrime.securesms.permissions.Permissions;
import org.thoughtcrime.securesms.recipients.Recipient;
import org.thoughtcrime.securesms.recipients.RecipientId;
import org.thoughtcrime.securesms.sms.MessageSender;
import org.thoughtcrime.securesms.util.MessageUtil;
import org.thoughtcrime.securesms.util.concurrent.SettableFuture;
import org.thoughtcrime.securesms.util.concurrent.SimpleTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class NewsFeedActivity extends AppCompatActivity {
  NewsFeedAdapter     dataAdapter = null;
  ArrayList<FeedItem> feedItems;
  ThreadDatabase       threadDatabase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.news_feed_activity);

    this.setupRecipientList();
  }

  private void setupRecipientList() {
//    MmsSmsDatabase mmsSmsDatabase = SignalDatabase.mmsSms();
//    mmsSmsDatabase.getSmsMms();

//    threadDatabase   = SignalDatabase.threads();
//    Set<RecipientId>   threadRecipients = threadDatabase.getAllThreadRecipients();
//
//    Recipient                recipient;
//    feedItems = new ArrayList();
//
//    for(RecipientId recipientId : threadRecipients){
//      recipient  = Recipient.live(recipientId).get();
//      feedItems.add(new FeedItem(recipient.getDisplayNameOrUsername(getApplicationContext()), recipient, false));
//    }
//
//    dataAdapter = new NewsFeedAdapter(this,
//                                      R.layout.news_feed_holder, feedItems);
//
//    ListView listView = (ListView) findViewById(R.id.news_feed_list);
//    listView.setAdapter(dataAdapter);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    super.onOptionsItemSelected(item);

    switch (item.getItemId()) {
      case android.R.id.home:   super.onBackPressed(); return true;
    }

    return false;
  }
}