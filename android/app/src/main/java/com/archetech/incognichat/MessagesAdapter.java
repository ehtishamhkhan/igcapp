package com.archetech.incognichat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import io.realm.RealmResults;

public class MessagesAdapter extends BaseAdapter {

    public final String TAG = this.getClass().getSimpleName() +" ---> ";

    private LayoutInflater inflater;
    private RealmResults<Message> messagesList;
    Context ctx;
    TimeFormatter timeFormatter;

    public MessagesAdapter(Context _ctx, RealmResults<Message> _messagesList)
    {
        this.ctx=_ctx;
        inflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        timeFormatter=new TimeFormatter();
        messagesList=_messagesList;
    }

    @Override
    public int getCount() {
        return messagesList.size();
    }

    @Override
    public Object getItem(int position) {
        return messagesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return messagesList.get(position).getMessage_id();
    }

    public long getItemPosition(int id)
    {
        for (int position=0; position<messagesList.size(); position++)
            if (messagesList.get(position).getMessage_id() == id)
                return position;
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(messagesList.get(position).getReceived())
        {
            view = inflater.inflate(R.layout.item_received_message, parent, false);
            ReceivedMessageViewHolder receivedMessageViewHolder = new ReceivedMessageViewHolder(view);
            receivedMessageViewHolder.MessageBody=view.findViewById(R.id.message_body);
            receivedMessageViewHolder.MessageTime =view.findViewById(R.id.message_time);
            view.setTag(receivedMessageViewHolder);
            receivedMessageViewHolder.MessageBody.setText(messagesList.get(position).getText());
            receivedMessageViewHolder.MessageTime.setText(timeFormatter.FormatTimestamp(messagesList.get(position).getCreated_at(),true, TimeFormatter.FormatType.ChatMessageShortTimestamp));
        }
        else
        {
            view = inflater.inflate(R.layout.item_sent_message, parent, false);
            SentMessageViewHolder sentMessageViewHolder = new SentMessageViewHolder(view);
            sentMessageViewHolder.MessageBody=view.findViewById(R.id.message_body);
            sentMessageViewHolder.MessageTime =view.findViewById(R.id.message_time);
            view.setTag(sentMessageViewHolder);
            sentMessageViewHolder.MessageBody.setText(messagesList.get(position).getText());
            sentMessageViewHolder.MessageTime.setText(timeFormatter.FormatTimestamp(messagesList.get(position).getCreated_at(),true, TimeFormatter.FormatType.ChatMessageShortTimestamp));
            if(messagesList.get(position).getStatus()==Long.valueOf(2))
            {
                sentMessageViewHolder.MessageStatus.setVisibility(View.VISIBLE);
            }
            else
            {
                if(messagesList.get(position).getStatus()==Long.valueOf(3))
                {
                    sentMessageViewHolder.MessageStatus.setColorFilter(ActivityCompat.getColor(view.getContext(),R.color.blue));
                }
                else
                {
                    sentMessageViewHolder.MessageStatus.setVisibility(View.INVISIBLE);
                }
            }

            if(messagesList.get(position).getStatus()== Long.valueOf(0))
            {
                sentMessageViewHolder.MessageBody.setVisibility(View.GONE);
                sentMessageViewHolder.SpinKitWaitingSent.setVisibility(View.VISIBLE);
            }

        }
        return view;
    }
}
