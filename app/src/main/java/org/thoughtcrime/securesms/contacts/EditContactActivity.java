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
  EditText bioTextField, notesTextField;
  Button   saveButton;
  TextView trustLevelHeading, intimacyLevelHeading;
  SeekBar  trustSeekBar, intimacySeekBar;

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
        trustLevelHeading.setText(getResources().getString(R.string.trust_level) + " " + String.format("%.1f", convertSeekbarValue(progress)));
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });
    intimacySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        intimacyLevelHeading.setText(getResources().getString(R.string.intimacy_level) + " " + String.format("%.1f", convertSeekbarValue(progress)));
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
    bioTextField         = findViewById(R.id.bio_text_field);
    saveButton           = findViewById(R.id.save_peep_details);
    trustLevelHeading    = findViewById(R.id.trust_level_heading);
    trustSeekBar         = findViewById(R.id.trustSeekBar);
    intimacyLevelHeading = findViewById(R.id.intimacy_level_heading);
    intimacySeekBar      = findViewById(R.id.intimacySeekBar);
    notesTextField       = findViewById(R.id.notes_text_field);
  }

  private void onSaveButton() {
    ContentValues contentValues = new ContentValues();
    contentValues.put(PeepContactContract.TRUST_LEVEL, convertSeekbarValue(trustSeekBar.getProgress()));
//    contentValues.put(PeepContactContract.BIO, bioTextField.getText().toString());
    contentValues.put(PeepContactContract.INTIMACY_LEVEL, convertSeekbarValue(intimacySeekBar.getProgress()));
//    contentValues.put(PeepContactContract.NOTES, notesTextField.getText().toString());
    contactAccessor.addOrUpdateContactData(this, getIntent().getIntExtra("recipient_id", 0), contentValues);
    onBackPressed();
  }

  private void updateViews() {
    bioTextField.setText(contactDetailModel.getPeepLocalData().getBio());

    String parsedTrustLevel = String.format("%.1f", contactDetailModel.getPeepLocalData().getTrust_level());
    int trust_value = (int) Math.round(contactDetailModel.getPeepLocalData().getTrust_level() * 10);
    trustLevelHeading.setText(getResources().getString(R.string.trust_level) + " " + parsedTrustLevel);
    trustSeekBar.setProgress(trust_value);

    String parsedIntimacyLevel = String.format("%.1f", contactDetailModel.getPeepLocalData().getIntimacy_level());
    int intimacy_value = (int) Math.round(contactDetailModel.getPeepLocalData().getIntimacy_level() * 10);
    intimacyLevelHeading.setText(getResources().getString(R.string.intimacy_level) + " " + parsedIntimacyLevel);
    intimacySeekBar.setProgress(intimacy_value);
  }

  private double convertSeekbarValue(int value){
    return value * 0.1f;
  }
}