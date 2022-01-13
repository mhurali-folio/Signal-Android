package org.thoughtcrime.securesms.postcreation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.thoughtcrime.securesms.R;

import java.util.ArrayList;

public class PostRecipientAdapter extends ArrayAdapter<PostRecipient> {
  private ArrayList<PostRecipient> recipientArrayList;
  private Context ctx;

  public PostRecipientAdapter(@NonNull Context context, int resource, ArrayList<PostRecipient> recipientArrayList) {
    super(context, resource, recipientArrayList);
    this.ctx = context;
    this.recipientArrayList = new ArrayList();
    this.recipientArrayList.addAll(recipientArrayList);
  }

  private class ViewHolder {
    TextView title;
    CheckBox name;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder = null;

    if (convertView == null) {
      LayoutInflater vi = (LayoutInflater)this.ctx.getSystemService(
          Context.LAYOUT_INFLATER_SERVICE);
      convertView = vi.inflate(R.layout.post_recipient_holder, null);

      holder = new ViewHolder();
      holder.title = (TextView) convertView.findViewById(R.id.code);
      holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
      convertView.setTag(holder);

      holder.name.setOnClickListener( new View.OnClickListener() {
        public void onClick(View v) {
          CheckBox cb = (CheckBox) v ;
          PostRecipient postRecipient = (PostRecipient) cb.getTag();
          postRecipient.setSelected(cb.isChecked());
        }
      });
    }
    else {
      holder = (ViewHolder) convertView.getTag();
    }

    PostRecipient recipient = recipientArrayList.get(position);
    holder.title.setText(recipient.getTitle());
    holder.name.setText("");
    holder.name.setChecked(recipient.isSelected());
    holder.name.setTag(recipient);

    return convertView;
  }

  @Override public int getCount() {
    return super.getCount();
  }
}
