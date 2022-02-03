package org.thoughtcrime.securesms.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.thoughtcrime.securesms.R;

public class ContactDetailActivity extends AppCompatActivity {
  ContactDetailModel contactDetailModel;

  TextView nameView, organizationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact_detail);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    this.getSupportActionBar().setTitle(R.string.ContactDetaiilActivity__heading);

    this.initializeViews();

    ContactAccessor contactAccessor = ContactAccessor.getInstance();
    if(getIntent().hasExtra("recipient_id")) {
      contactDetailModel = contactAccessor.getLocalContactDetails(this, getIntent().getIntExtra("recipient_id", 0));
      if(contactDetailModel.getPeepStructuredName() != null) {
        nameView.setText(contactDetailModel.getPeepStructuredName().name);
        nameView.setVisibility(View.VISIBLE);
      }
      if(contactDetailModel.getPeepWorkInfo() != null) {
        organizationView.setText(String.format("%s\n%s", contactDetailModel.getPeepWorkInfo().company, contactDetailModel.getPeepWorkInfo().title));
        organizationView.setVisibility(View.VISIBLE);
      }
    }
  }

  private void initializeViews() {
    nameView = findViewById(R.id.peep_contact_detail_name);
    organizationView = findViewById(R.id.peep_contact_detail_organization);
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