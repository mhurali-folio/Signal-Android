package org.thoughtcrime.securesms.newsfeed;

import android.os.Bundle;
import android.view.MenuItem;

import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.database.MmsSmsDatabase;
import org.thoughtcrime.securesms.database.SignalDatabase;
import org.thoughtcrime.securesms.database.ThreadDatabase;
import org.thoughtcrime.securesms.database.model.RecipientModel;
import org.thoughtcrime.securesms.groups.GroupId;
import org.thoughtcrime.securesms.recipients.Recipient;

import java.util.ArrayList;
import java.util.Set;

public class NewsFeedActivity extends AppCompatActivity {
  NewsFeedAdapter     dataAdapter = null;
  ArrayList<FeedItem> feedItems;
  ThreadDatabase       threadDatabase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.news_feed_activity);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    this.getSupportActionBar().setTitle(R.string.NewsFeedActivity__heading);

    this.setupRecipientList();
  }

  private void setupRecipientList() {
    threadDatabase   = SignalDatabase.threads();
    Set<RecipientModel>                          threadRecipients = threadDatabase.getAllThreadRecipientsData();

    Recipient                                 recipient;
    feedItems = new ArrayList();

    for(RecipientModel recipientModel : threadRecipients){
      recipient  = Recipient.live(recipientModel.recipientId).get();
      MmsSmsDatabase mmsSmsDatabase = SignalDatabase.mmsSms();

      feedItems.add(new FeedItem(recipient, mmsSmsDatabase.getSmsMms(recipientModel.threadId, recipientModel.recipientId)));
    }

    dataAdapter = new NewsFeedAdapter(this,
                                      R.layout.news_feed_holder, feedItems);

    ListView listView = (ListView) findViewById(R.id.news_feed_list);
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
}