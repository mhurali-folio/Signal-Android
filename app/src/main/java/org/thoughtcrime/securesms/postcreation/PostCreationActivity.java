package org.thoughtcrime.securesms.postcreation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.database.SignalDatabase;
import org.thoughtcrime.securesms.database.ThreadDatabase;
import org.thoughtcrime.securesms.recipients.Recipient;
import org.thoughtcrime.securesms.recipients.RecipientId;

import java.util.ArrayList;
import java.util.Set;

public class PostCreationActivity extends AppCompatActivity {
  PostRecipientAdapter dataAdapter = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.post_creation_activity);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    this.getSupportActionBar().setTitle(R.string.PostCreationActivity__new_message);

    this.setupRecipientList();
  }

  private void setupRecipientList() {
    ThreadDatabase threadDatabase   = SignalDatabase.threads();
    Set<RecipientId>   threadRecipients = threadDatabase.getAllThreadRecipients();

    Recipient                recipient;
    ArrayList<PostRecipient> postRecipients = new ArrayList();

    for(RecipientId recipientId : threadRecipients){
      recipient  = Recipient.live(recipientId).get();
      postRecipients.add(new PostRecipient(recipient.getDisplayNameOrUsername(getApplicationContext()), recipientId.toString(), false));
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
}