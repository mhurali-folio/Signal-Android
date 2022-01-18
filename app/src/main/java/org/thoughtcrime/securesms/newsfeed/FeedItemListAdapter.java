package org.thoughtcrime.securesms.newsfeed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.database.model.MessageDataHolder;
import org.thoughtcrime.securesms.recipients.Recipient;

import java.util.ArrayList;

public class FeedItemListAdapter extends ArrayAdapter<MessageDataHolder> {
  private ArrayList<MessageDataHolder> feedList;
  private Context                      ctx;

  public FeedItemListAdapter(@NonNull Context context, int resource, ArrayList<MessageDataHolder> feedList) {
    super(context, resource, feedList);
    this.ctx = context;
    this.feedList = new ArrayList();
    this.feedList.addAll(feedList);
  }

  private class ViewHolder {
    TextView senderTitle;
    TextView message;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder = null;

    if (convertView == null) {
      LayoutInflater vi = (LayoutInflater)this.ctx.getSystemService(
          Context.LAYOUT_INFLATER_SERVICE);
      convertView = vi.inflate(R.layout.feed_item_holder, null);

      holder = new ViewHolder();
      holder.senderTitle = (TextView) convertView.findViewById(R.id.senderTitle);
      holder.message = (TextView) convertView.findViewById(R.id.message);
      convertView.setTag(holder);
    }
    else {
      holder = (ViewHolder) convertView.getTag();
    }

    MessageDataHolder feedItem = feedList.get(position);
    holder.senderTitle.setText( feedItem.isOutGoing ? "Me" : feedItem.userName);
    holder.message.setText(feedItem.messageContent);

    return convertView;
  }

  @Override public int getCount() {
    return super.getCount();
  }
}
