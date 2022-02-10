package org.thoughtcrime.securesms.contacts;

import android.provider.ContactsContract;

public class PeepContactContract {
  public static final String RECIPIENT_ID = ContactsContract.Data.DATA1;
  public static final String TRUST_LEVEL = ContactsContract.Data.DATA2;
  public static final String BIO = ContactsContract.Data.DATA3;
  public static final String INTIMACY_LEVEL = ContactsContract.Data.DATA4;
  public static final String NOTES = ContactsContract.Data.DATA5;

  public static final String CONTACT_MIME_TYPE = "vnd.android.cursor.item/peepline";
}
