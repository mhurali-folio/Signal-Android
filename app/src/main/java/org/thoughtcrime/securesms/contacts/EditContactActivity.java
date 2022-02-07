package org.thoughtcrime.securesms.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.recipients.RecipientId;

public class EditContactActivity extends AppCompatActivity {
  EditText bioTextField;
  Button   saveButton;

  ContactDetailModel contactDetailModel;
  ContactAccessor contactAccessor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_contact);

    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    this.getSupportActionBar().setTitle(R.string.EditContactActivity__edit_details);

    this.initializeViews();
    contactAccessor = ContactAccessor.getInstance();

    if(getIntent().hasExtra("recipient_id")) {
      contactDetailModel = contactAccessor.getLocalContactDetails(this, getIntent().getIntExtra("recipient_id", 0));
    }

    saveButton.setOnClickListener(l -> onSaveButton());
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void initializeViews() {
      bioTextField = findViewById(R.id.bio_text_field);
      saveButton   = findViewById(R.id.save_peep_details);
  }

  private void onSaveButton() {
    ContentValues contentValues = new ContentValues();
    contentValues.put(ContactsContract.Data.DATA3, bioTextField.getText().toString());
    Log.d("handlePeepLocalData", "onSaveButton: '" + bioTextField.getText().toString());
    contactAccessor.addOrUpdateContactData(this, getIntent().getIntExtra("recipient_id", 0), contentValues);
    onBackPressed();
  }
}