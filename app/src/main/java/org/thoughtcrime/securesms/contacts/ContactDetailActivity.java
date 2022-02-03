package org.thoughtcrime.securesms.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.thoughtcrime.securesms.R;

public class ContactDetailActivity extends AppCompatActivity {
  ContactDetailModel contactDetailModel;

  TextView nameView, organizationView;
  LinearLayout phoneLayout, emailLayout, addressLayout;

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
      createDynamicViews();
    }
  }

  private void initializeViews() {
    nameView = findViewById(R.id.peep_contact_detail_name);
    organizationView = findViewById(R.id.peep_contact_detail_organization);
    phoneLayout = findViewById(R.id.peep_contact_detail_phone_layout);
    emailLayout = findViewById(R.id.peep_contact_detail_email_layout);
    addressLayout = findViewById(R.id.peep_contact_detail_address_layout);
  }

  private void createDynamicViews() {
    addPhoneNumbersView();
    addEmailsView();
    addAddressView();
  }

  private void addPhoneNumbersView() {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                  LinearLayout.LayoutParams.WRAP_CONTENT);
    layoutParams.setMargins(0, 10, 0, 0);

    LinearLayout.LayoutParams titleLayoutParams = layoutParams;
    titleLayoutParams.setMargins(0, 20, 0, 0);

    TextView phoneTitleView = new TextView(this);
    phoneTitleView.setLayoutParams(titleLayoutParams);
    phoneTitleView.setText("Phone Numbers:");
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      phoneTitleView.setTextAppearance(R.style.TextAppearance_Signal_Body1_Bold);
    }
    phoneTitleView.setTextSize(20);
    phoneLayout.addView(phoneTitleView);

    for (PeepPhoneNumber peepPhoneNumber:
          contactDetailModel.getPeepPhoneNumbers()) {
          TextView textView = new TextView(this);
          textView.setLayoutParams(layoutParams);
          textView.setText(String.format("%s: %s",
                                         getResources().getString(ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(peepPhoneNumber.type)),
                                         peepPhoneNumber.number));
          phoneLayout.addView(textView);
    }
  }

  private void addEmailsView() {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                           LinearLayout.LayoutParams.WRAP_CONTENT);
    layoutParams.setMargins(0, 10, 0, 0);

    LinearLayout.LayoutParams titleLayoutParams = layoutParams;
    titleLayoutParams.setMargins(0, 20, 0, 0);

    TextView emailTitleView = new TextView(this);
    emailTitleView.setLayoutParams(titleLayoutParams);
    emailTitleView.setText("Email Addresses:");
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      emailTitleView.setTextAppearance(R.style.TextAppearance_Signal_Body1_Bold);
    }
    emailTitleView.setTextSize(20);
    emailLayout.addView(emailTitleView);

    for (PeepEmail peepEmail:
        contactDetailModel.getPeepEmails()) {
      TextView textView = new TextView(this);
      textView.setLayoutParams(layoutParams);
      textView.setText(String.format("%s: %s",
                                     getResources().getString(ContactsContract.CommonDataKinds.Email.getTypeLabelResource(peepEmail.type)),
                                     peepEmail.email));
      emailLayout.addView(textView);
    }
  }

  private void addAddressView() {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                           LinearLayout.LayoutParams.WRAP_CONTENT);
    layoutParams.setMargins(0, 10, 0, 0);

    LinearLayout.LayoutParams titleLayoutParams = layoutParams;
    titleLayoutParams.setMargins(0, 20, 0, 0);

    TextView addressTitleView = new TextView(this);
    addressTitleView.setLayoutParams(titleLayoutParams);
    addressTitleView.setText("Addresses:");
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      addressTitleView.setTextAppearance(R.style.TextAppearance_Signal_Body1_Bold);
    }
    addressTitleView.setTextSize(20);
    addressLayout.addView(addressTitleView);

    for (PeepAddress peepAddress:
        contactDetailModel.getPeepAddresses()) {
      TextView textView = new TextView(this);
      textView.setLayoutParams(layoutParams);
      textView.setText(String.format("%s: %s",
                                     getResources().getString(ContactsContract.CommonDataKinds.StructuredPostal.getTypeLabelResource(peepAddress.type)),
                                     peepAddress.address));
      addressLayout.addView(textView);
    }
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