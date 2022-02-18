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
  private static final String ARG_EMAILS = "emails";

  private LinearLayout email_address_layout;
  private ArrayList<PeepEmail> mParamEmails;

  public EditPhoneContactFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param paramEmails Parameter emails arraylist.
   * @return A new instance of fragment EditPhoneContactFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static EditPhoneContactFragment newInstance(ArrayList<PeepEmail> paramEmails) {
    EditPhoneContactFragment fragment = new EditPhoneContactFragment();
    Bundle                   args     = new Bundle();
    args.putSerializable(ARG_EMAILS, paramEmails);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParamEmails = (ArrayList<PeepEmail>) getArguments().getSerializable(ARG_EMAILS);
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
    mParamEmails = (ArrayList<PeepEmail>) requireArguments().getSerializable(ARG_EMAILS);

    email_address_layout = view.findViewById(R.id.email_address_layout);

    addEmailsView();
  }

  private void addEmailsView() {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                           LinearLayout.LayoutParams.WRAP_CONTENT);
    layoutParams.setMargins(0, 20, 0, 0);

    for (int i = 0; i < mParamEmails.size(); i++) {
      EditText editTextView = new EditText(getContext());
      editTextView.setLayoutParams(layoutParams);
      editTextView.setText(mParamEmails.get(i).email);
      editTextView.setBackgroundColor(getResources().getColor(R.color.text_input_background));
      editTextView.setPadding(10, 10, 10, 10);
      editTextView.setSingleLine(true);
      editTextView.setId(i);
      email_address_layout.addView(editTextView);
    }
  }

  private ArrayList<PeepEmail> getUpdatedEmails () {
    for (int i = 0; i < mParamEmails.size(); i++) {
      EditText editTextView = getView().findViewById(i);
      PeepEmail peepEmail = mParamEmails.get(i);
      peepEmail.setEmail(editTextView.getText().toString());
      mParamEmails.set(i, peepEmail);
    }

    return mParamEmails;
  }

  public ContactDetailModel getUpdatedInformation () {
    ContactDetailModel contactDetailModel = new ContactDetailModel();

    contactDetailModel.setPeepEmails(getUpdatedEmails());
    return contactDetailModel;
  }
}