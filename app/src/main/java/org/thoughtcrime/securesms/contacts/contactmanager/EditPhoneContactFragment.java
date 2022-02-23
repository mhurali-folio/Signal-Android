package org.thoughtcrime.securesms.contacts.contactmanager;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.thoughtcrime.securesms.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPhoneContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPhoneContactFragment extends Fragment {

  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  static final String ARG_EMAILS = "emails";
  static final String ARG_ADDRESSES = "addresses";

  private final int EMAIL_TEXT_VIEW_BASE_ID = 101;
  private final int ADDRESS_TEXT_VIEW_BASE_ID = 1001;

  private LinearLayout email_address_layout, user_address_layout;
  private ArrayList<PeepEmail> mParamEmails;
  private ArrayList<PeepAddress> mParamAddresses;

  public EditPhoneContactFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param paramEmails Parameter emails arraylist.
   * @param paramAddresses Parameter addresses arraylist.
   * @return A new instance of fragment EditPhoneContactFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static EditPhoneContactFragment newInstance(ArrayList<PeepEmail> paramEmails, ArrayList<PeepAddress> paramAddresses) {
    EditPhoneContactFragment fragment = new EditPhoneContactFragment();
    Bundle                   args     = new Bundle();
    args.putSerializable(ARG_EMAILS, paramEmails);
    args.putSerializable(ARG_ADDRESSES, paramAddresses);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParamEmails    = (ArrayList<PeepEmail>) getArguments().getSerializable(ARG_EMAILS);
      mParamAddresses = (ArrayList<PeepAddress>) getArguments().getSerializable(ARG_EMAILS);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_edit_phone_contact, container, false);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mParamEmails         = (ArrayList<PeepEmail>) requireArguments().getSerializable(ARG_EMAILS);
    mParamAddresses      = (ArrayList<PeepAddress>) requireArguments().getSerializable(ARG_ADDRESSES);
    email_address_layout = view.findViewById(R.id.email_address_layout);
    user_address_layout  = view.findViewById(R.id.user_address_layout);

    addEmailsView();
    addAddressessView();
  }

  private EditText generateEditTextView () {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                           LinearLayout.LayoutParams.WRAP_CONTENT);
    layoutParams.setMargins(0, 20, 0, 0);

    EditText editTextView = new EditText(getContext());
    editTextView.setLayoutParams(layoutParams);
    editTextView.setBackgroundColor(getResources().getColor(R.color.text_input_background));
    editTextView.setPadding(10, 10, 10, 10);
    editTextView.setSingleLine(true);

    return editTextView;
  }

  private void addEmailsView() {
    for (int i = 0; i < mParamEmails.size(); i++) {
      EditText editTextView = generateEditTextView();
      editTextView.setText(mParamEmails.get(i).email);
      editTextView.setId(EMAIL_TEXT_VIEW_BASE_ID + i);
      email_address_layout.addView(editTextView);
    }
  }

  private void addAddressessView() {
    for (int i = 0; i < mParamAddresses.size(); i++) {
      EditText editTextView = generateEditTextView();
      editTextView.setText(mParamAddresses.get(i).address);
      editTextView.setId(ADDRESS_TEXT_VIEW_BASE_ID + i);
      user_address_layout.addView(editTextView);
    }
  }

  private ArrayList<PeepEmail> getUpdatedEmails () {
    for (int i = 0; i < mParamEmails.size(); i++) {
      EditText editTextView = getView().findViewById(EMAIL_TEXT_VIEW_BASE_ID + i);
      PeepEmail peepEmail = mParamEmails.get(i);
      peepEmail.setEmail(editTextView.getText().toString());
      mParamEmails.set(i, peepEmail);
    }

    return mParamEmails;
  }

  private ArrayList<PeepAddress> getUpdatedAddresses () {
    for (int i = 0; i < mParamAddresses.size(); i++) {
      EditText editTextView = getView().findViewById(ADDRESS_TEXT_VIEW_BASE_ID + i);
      PeepAddress peepAddress = mParamAddresses.get(i);
      peepAddress.setAddress(editTextView.getText().toString());
      mParamAddresses.set(i, peepAddress);
    }

    return mParamAddresses;
  }

  public ContactDetailModel getUpdatedInformation () {
    ContactDetailModel contactDetailModel = new ContactDetailModel();

    contactDetailModel.setPeepEmails(getUpdatedEmails());
    contactDetailModel.setPeepAddresses(getUpdatedAddresses());
    return contactDetailModel;
  }
}