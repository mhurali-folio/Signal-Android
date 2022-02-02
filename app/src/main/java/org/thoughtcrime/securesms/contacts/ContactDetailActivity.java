package org.thoughtcrime.securesms.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.thoughtcrime.securesms.R;

public class ContactDetailActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact_detail);

    ContactAccessor contactAccessor = ContactAccessor.getInstance();
    if(getIntent().hasExtra("recipient_id")) {
      contactAccessor.getLocalContactDetails(this, getIntent().getIntExtra("recipient_id", 0));
    }
  }
}