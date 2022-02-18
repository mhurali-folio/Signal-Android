package org.thoughtcrime.securesms.contacts.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.contacts.ContactAccessor;

import java.util.ArrayList;
import java.util.Calendar;

public class EditContactActivity extends AppCompatActivity {
  EditText bioTextField, notesTextField, tagsTextField;
  Button   saveButton, dateWeMetButton;
  TextView trustLevelHeading, intimacyLevelHeading;
  SeekBar  trustSeekBar, intimacySeekBar;
  ChipGroup tagsChipGroup;

  EditPhoneContactFragment editPhoneContactFragment;

  DatePickerDialog datePickerDialog;

  ContactDetailModel contactDetailModel;
  ContactAccessor    contactAccessor;

  private String tags = "";
  private int recipient_id;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_contact);

    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    this.getSupportActionBar().setTitle(R.string.EditContactActivity__edit_details);

    this.initializeViews();
    this.initializeDatePicker();

    contactAccessor = ContactAccessor.getInstance();

    if(getIntent().hasExtra("recipient_id")) {
      recipient_id = getIntent().getIntExtra("recipient_id", 0);
      contactDetailModel = contactAccessor.getLocalContactDetails(this, recipient_id);
      updateViews();

      if(tags != "") {
        updateTagsChips();
      }
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

    dateWeMetButton.setOnClickListener(l -> datePickerDialog.show());

    tagsTextField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        String text = v.getText().toString().trim();
        if (actionId == EditorInfo.IME_ACTION_DONE && text != "") {
          tagsChipGroup.setVisibility(View.VISIBLE);
          tagsChipGroup.addView(createChip(text));
          if(tags == "") {
            tags = text;
          } else {
            tags = String.format("%s,%s",tags, text);
          }
          v.setText("");
          hideKeyboard();
          return true;
        }
        return false;
      }
    });

    if (savedInstanceState == null) {
      Bundle bundle = new Bundle();
      bundle.putSerializable("emails", contactDetailModel.getPeepEmails());

      editPhoneContactFragment = EditPhoneContactFragment.newInstance(contactDetailModel.getPeepEmails());
      getSupportFragmentManager().beginTransaction()
                                 .replace(R.id.phone_contact_fragment_view, editPhoneContactFragment)
                                 .commit();
    }
  }

  private void hideKeyboard() {
    InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.toggleSoftInput(0, 0);
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
    dateWeMetButton      = findViewById(R.id.date_we_met_button);
    tagsTextField        = findViewById(R.id.tags_text_field);
    tagsChipGroup        = findViewById(R.id.tags_chips_group);
  }

  private void onSaveButton() {

    ContentValues contentValues = new ContentValues();
    contentValues.put(PeepContactContract.TRUST_LEVEL, convertSeekbarValue(trustSeekBar.getProgress()));
    contentValues.put(PeepContactContract.ABOUT, bioTextField.getText().toString());
    contentValues.put(PeepContactContract.INTIMACY_LEVEL, convertSeekbarValue(intimacySeekBar.getProgress()));
    contentValues.put(PeepContactContract.NOTES, notesTextField.getText().toString());
    contentValues.put(PeepContactContract.DATE_WE_MET, dateWeMetButton.getText().toString());
    contentValues.put(PeepContactContract.TAGS, tags);

    contactAccessor.addOrUpdateContactData(this, recipient_id, contentValues);

    ContactDetailModel phoneContactDetails = editPhoneContactFragment.getUpdatedInformation();
    if(phoneContactDetails != null) {
      contactAccessor.updatePhoneBookContact(this, recipient_id, phoneContactDetails);
    }

    onBackPressed();
  }

  private void initializeDatePicker() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      final Calendar c = Calendar.getInstance();
      int   mYear      = c.get(Calendar.YEAR);
      int   mMonth     = c.get(Calendar.MONTH);
      int   mDay       = c.get(Calendar.DAY_OF_MONTH);

      datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
        @Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateWeMetButton.setText(String.format("%s/%s/%s", dayOfMonth, (month + 1), year));
        }
      }, mYear, mMonth, mDay);
    }
  }

  private void updateViews() {
    bioTextField.setText(contactDetailModel.getPeepLocalData().getBio());

    if(contactDetailModel.getPeepLocalData().getTrust_level() != null) {
      String parsedTrustLevel = String.format("%.1f", contactDetailModel.getPeepLocalData().getTrust_level());
      int trust_value = (int) Math.round(contactDetailModel.getPeepLocalData().getTrust_level() * 10);
      trustLevelHeading.setText(getResources().getString(R.string.trust_level) + " " + parsedTrustLevel);
      trustSeekBar.setProgress(trust_value);
    }

    if(contactDetailModel.getPeepLocalData().getIntimacy_level() != null) {
      String parsedIntimacyLevel = String.format("%.1f", contactDetailModel.getPeepLocalData().getIntimacy_level());
      int intimacy_value = (int) Math.round(contactDetailModel.getPeepLocalData().getIntimacy_level() * 10);
      intimacyLevelHeading.setText(getResources().getString(R.string.intimacy_level) + " " + parsedIntimacyLevel);
      intimacySeekBar.setProgress(intimacy_value);
    }

    notesTextField.setText(contactDetailModel.getPeepLocalData().getNotes());

    if(contactDetailModel.peepLocalData.getDateWeMet() != null) {
      dateWeMetButton.setText(contactDetailModel.peepLocalData.getDateWeMet());
    }

    if(contactDetailModel.peepLocalData.getTags() != null) {
      tags = contactDetailModel.peepLocalData.getTags();
    }
  }

  private void updateTagsChips() {
    String[] chips = tags.split(",");
    for (String _chip: chips) {
      tagsChipGroup.setVisibility(View.VISIBLE);
      tagsChipGroup.addView(createChip(_chip));
    }
  }

  private double convertSeekbarValue(int value){
    return value * 0.1f;
  }

  private Chip createChip(String text) {
    Chip chip = new Chip(this);
    chip.setText(text);
    chip.setCheckable(false);
    chip.setClickable(false);
    chip.setCloseIconVisible(false);
    return chip;
  }
}