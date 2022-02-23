package org.thoughtcrime.securesms.contacts.contactmanager;

import android.provider.ContactsContract;

public class PeepContactContract {
  public static final String RECIPIENT_ID = ContactsContract.Data.DATA1;
  public static final String TRUST_LEVEL = ContactsContract.Data.DATA2;
  public static final String ABOUT = ContactsContract.Data.DATA3;
  public static final String INTIMACY_LEVEL = ContactsContract.Data.DATA4;
  public static final String NOTES = ContactsContract.Data.DATA5;
  public static final String DATE_WE_MET = ContactsContract.Data.DATA6;
  public static final String TAGS = ContactsContract.Data.DATA7;

  public static final String CONTACT_MIME_TYPE = "vnd.android.cursor.item/peepline";
}
