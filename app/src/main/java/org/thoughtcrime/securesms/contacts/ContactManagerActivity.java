package org.thoughtcrime.securesms.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.annimon.stream.Stream;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.thoughtcrime.securesms.ContactSelectionActivity;
import org.thoughtcrime.securesms.ContactSelectionListFragment;
import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.recipients.RecipientId;
import org.thoughtcrime.securesms.util.FeatureFlags;
import org.thoughtcrime.securesms.util.Util;

import java.util.List;


public class ContactManagerActivity extends ContactSelectionActivity {

  private static final short REQUEST_CODE_ADD_DETAILS = 17275;
  public static String IS_CONTACT_MANAGER = "is_contact_manager";

  private ExtendedFloatingActionButton next;

  public static Intent newIntent(@NonNull Context context) {
    Intent intent = new Intent(context, ContactManagerActivity.class);

    intent.putExtra(ContactSelectionListFragment.REFRESHABLE, false);
    intent.putExtra(ContactSelectionActivity.EXTRA_LAYOUT_RES_ID, R.layout.contact_manager_activity);

    int displayMode = Util.isDefaultSmsProvider(context) ? ContactsCursorLoader.DisplayMode.FLAG_SMS | ContactsCursorLoader.DisplayMode.FLAG_PUSH
                                                         : ContactsCursorLoader.DisplayMode.FLAG_PUSH;

    intent.putExtra(ContactSelectionListFragment.DISPLAY_MODE, displayMode);
    intent.putExtra(ContactSelectionListFragment.SELECTION_LIMITS, FeatureFlags.groupLimits().excludingSelf());
    intent.putExtra(IS_CONTACT_MANAGER, true);

    return intent;
  }

  @Override
  public void onCreate(Bundle bundle, boolean ready) {
    super.onCreate(bundle, ready);
    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    next = findViewById(R.id.next);

    next.setOnClickListener(v -> handleNextPressed());
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if (requestCode == REQUEST_CODE_ADD_DETAILS && resultCode == RESULT_OK) {
      finish();
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }

  @Override
  public void onSelectionChanged() {
  }

  private void handleNextPressed() {
    ContactAccessor contactAccessor = ContactAccessor.getInstance();

    List<RecipientId> ids = Stream.of(contactsFragment.getSelectedContacts())
                                  .map(selectedContact -> selectedContact.getOrCreateRecipientId(this))
                                  .toList();

    /**
     * TODO: Instead of looping and calling method, we should provide array to the database query.
     * TODO: We should use simpleTask run, see the implementation in CreateGroupActivity.java
     */
    for (RecipientId recipient_id : ids) {
      contactAccessor.addOrUpdateContactData(this, (int) recipient_id.toLong(), Math.random());
    }

    this.onBackPressed();
  }
}
