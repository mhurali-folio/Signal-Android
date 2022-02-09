package org.thoughtcrime.securesms.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.thoughtcrime.securesms.R;

public class EditContactActivity extends AppCompatActivity {
  EditText bioTextField;
  Button   saveButton;
  TextView trustLevelHeading;
  SeekBar  trustSeekBar;

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
      updateViews();
    }

    saveButton.setOnClickListener(l -> onSaveButton());
    trustSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        trustLevelHeading.setText(getResources().getString(R.string.ContactManagerActivity__trust_level_heading) + " " + String.format("%.1f", convertSeekbarValue(progress)));
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });
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
    trustLevelHeading = findViewById(R.id.trust_level_heading);
    trustSeekBar = findViewById(R.id.trustSeekBar);
  }

  private void onSaveButton() {
    ContentValues contentValues = new ContentValues();
    contentValues.put(ContactsContract.Data.DATA2, convertSeekbarValue(trustSeekBar.getProgress()));
    contentValues.put(ContactsContract.Data.DATA3, bioTextField.getText().toString());
    contactAccessor.addOrUpdateContactData(this, getIntent().getIntExtra("recipient_id", 0), contentValues);
    onBackPressed();
  }

  private void updateViews() {
    bioTextField.setText(contactDetailModel.getPeepLocalData().getBio());

    String parsedTrustLevel = String.format("%.1f", contactDetailModel.getPeepLocalData().getTrust_level());
    int value = (int) Math.round(contactDetailModel.getPeepLocalData().getTrust_level() * 10);
    trustLevelHeading.setText(getResources().getString(R.string.trust_level) + " " + parsedTrustLevel);
    trustSeekBar.setProgress(value);
  }

  private double convertSeekbarValue(int value){
    return value * 0.1f;
  }
}