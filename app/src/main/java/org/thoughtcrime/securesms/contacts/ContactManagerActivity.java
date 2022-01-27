package org.thoughtcrime.securesms.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.thoughtcrime.securesms.ContactSelectionActivity;
import org.thoughtcrime.securesms.ContactSelectionListFragment;
import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.groups.ui.creategroup.CreateGroupActivity;
import org.thoughtcrime.securesms.util.FeatureFlags;
import org.thoughtcrime.securesms.util.Util;


public class ContactManagerActivity extends ContactSelectionActivity {

  private static final short REQUEST_CODE_ADD_DETAILS = 17275;
  public static String IS_CONTACT_MANAGER = "is_contact_manager";

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
//    Stopwatch                              stopwatch         = new Stopwatch("Recipient Refresh");
//    SimpleProgressDialog.DismissibleDialog dismissibleDialog = SimpleProgressDialog.showDelayed(this);
//
//    SimpleTask.run(getLifecycle(), () -> {
//      List<RecipientId> ids = Stream.of(contactsFragment.getSelectedContacts())
//                                    .map(selectedContact -> selectedContact.getOrCreateRecipientId(this))
//                                    .toList();
//
//      List<Recipient> resolved = Recipient.resolvedList(ids);
//
//      stopwatch.split("resolve");
//
//      List<Recipient> registeredChecks = Stream.of(resolved)
//                                               .filter(r -> r.getRegistered() == RecipientDatabase.RegisteredState.UNKNOWN)
//                                               .toList();
//
//      Log.i(TAG, "Need to do " + registeredChecks.size() + " registration checks.");
//
//      for (Recipient recipient : registeredChecks) {
//        try {
//          DirectoryHelper.refreshDirectoryFor(this, recipient, false);
//        } catch (IOException e) {
//          Log.w(TAG, "Failed to refresh registered status for " + recipient.getId(), e);
//        }
//      }
//
//      stopwatch.split("registered");
//
//      List<Recipient> recipientsAndSelf = new ArrayList<>(resolved);
//      recipientsAndSelf.add(Recipient.self().resolve());
//
//      if (!SignalStore.internalValues().gv2DoNotCreateGv2Groups()) {
//        try {
//          GroupsV2CapabilityChecker.refreshCapabilitiesIfNecessary(recipientsAndSelf);
//        } catch (IOException e) {
//          Log.w(TAG, "Failed to refresh all recipient capabilities.", e);
//        }
//      }
//
//      stopwatch.split("capabilities");
//
//      resolved = Recipient.resolvedList(ids);
//
//      Pair<Boolean, List<RecipientId>> result;
//
//      boolean gv2 = Stream.of(recipientsAndSelf).allMatch(r -> r.getGroupsV2Capability() == Recipient.Capability.SUPPORTED);
//      if (!gv2 && Stream.of(resolved).anyMatch(r -> !r.hasE164()))
//      {
//        Log.w(TAG, "Invalid GV1 group...");
//        ids = Collections.emptyList();
//        result = Pair.create(false, ids);
//      } else {
//        result = Pair.create(true, ids);
//      }
//
//      stopwatch.split("gv1-check");
//
//      return result;
//    }, result -> {
//      dismissibleDialog.dismiss();
//
//      stopwatch.stop(TAG);
//
//      if (result.first) {
//        startActivityForResult(AddGroupDetailsActivity.newIntent(this, result.second), REQUEST_CODE_ADD_DETAILS);
//      } else {
//        new AlertDialog.Builder(this)
//            .setMessage(R.string.CreateGroupActivity_some_contacts_cannot_be_in_legacy_groups)
//            .setPositiveButton(android.R.string.ok, (d, w) -> d.dismiss())
//            .show();
//      }
//    });
  }
}
