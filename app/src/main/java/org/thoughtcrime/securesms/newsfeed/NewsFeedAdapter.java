package org.thoughtcrime.securesms.newsfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.signal.storageservice.protos.groups.Group;
import org.thoughtcrime.securesms.R;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedAdapter extends ArrayAdapter<FeedItem> {
  private ArrayList<FeedItem> recipientArrayList;
  private Context             ctx;

  public NewsFeedAdapter(@NonNull Context context, int resource, ArrayList<FeedItem> recipientArrayList) {
    super(context, resource, recipientArrayList);
    this.ctx = context;
    this.recipientArrayList = new ArrayList();
    this.recipientArrayList.addAll(recipientArrayList);
  }

  private class ViewHolder {
    TextView groupTitle;
    ListView    messageList;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder = null;
    FeedItemListAdapter     dataAdapter = null;

    if (convertView == null) {
      LayoutInflater vi = (LayoutInflater)this.ctx.getSystemService(
          Context.LAYOUT_INFLATER_SERVICE);
      convertView = vi.inflate(R.layout.news_feed_holder, null);

      holder = new ViewHolder();
      holder.groupTitle = (TextView) convertView.findViewById(R.id.groupTitle);
      holder.messageList = (ListView) convertView.findViewById(R.id.feed_item_list);
      convertView.setTag(holder);
    }
    else {
      holder = (ViewHolder) convertView.getTag();
    }

    FeedItem feedItem = recipientArrayList.get(position);
    holder.groupTitle.setText(feedItem.recipient.isGroup()
                              ? "Group: " + feedItem.recipient.getGroupName(ctx)
                              : feedItem.recipient.getDisplayNameOrUsername(ctx));
    dataAdapter = new FeedItemListAdapter(ctx,
                                          R.layout.news_feed_holder, feedItem.feeds);

    holder.messageList.setAdapter(dataAdapter);

    return convertView;
  }

  @Override public int getCount() {
    return super.getCount();
  }
}
