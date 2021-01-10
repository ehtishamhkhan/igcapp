package com.archetech.incognichat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import io.realm.RealmResults;

public class ConversationsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private RealmResults<Conversation> conversationsList;
    Context ctx;

    public ConversationsAdapter(Context _ctx, RealmResults<Conversation> _conversationsList)
    {
        this.ctx=_ctx;
        inflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.conversationsList = _conversationsList;
    }

    @Override
    public int getCount() {
        return conversationsList.size();
    }

    @Override
    public Object getItem(int position) {
        return conversationsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return conversationsList.get(position).getConversation_id();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ConversationViewHolder conversationViewHolder;
        if(view==null) {
            conversationViewHolder = new ConversationViewHolder();
            view = inflater.inflate(R.layout.item_conversation, parent, false);

            conversationViewHolder.Avatar=view.findViewById(R.id.avatar);
            conversationViewHolder.NickName = view.findViewById(R.id.txt_name);
            conversationViewHolder.MessagePreview=view.findViewById(R.id.txt_text);
            conversationViewHolder.BadgeButton=view.findViewById(R.id.badge);
            conversationViewHolder.MessagesCounter=view.findViewById(R.id.counter);
            view.setTag(conversationViewHolder);
        }
        else{
            conversationViewHolder = (ConversationViewHolder) view.getTag();
        }

        conversationViewHolder.Avatar.setColorFilter(Color.parseColor(conversationsList.get(position).getAvatar()));
        conversationViewHolder.NickName.setText((conversationsList.get(position).getNick_name()));
        conversationViewHolder.MessagePreview.setText(new MessageController(ctx).getLastMessagePreview(String.valueOf(conversationsList.get(position).getConversation_id())));
        if(conversationsList.get(position).isReceived())
        {
            conversationViewHolder.Avatar.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_incoming, ctx.getTheme()));
        }
        else
        {
            conversationViewHolder.Avatar.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_outgoing, ctx.getTheme()));
        }

        Integer count = new MessageController(ctx).getUnreadMessages(String.valueOf(conversationsList.get(position).getConversation_id()));
        if(count>0)
        {
            conversationViewHolder.MessagesCounter.setText(String.valueOf(count)+" unread message");
            conversationViewHolder.MessagesCounter.setVisibility(View.VISIBLE);
        }
        else
        {
            conversationViewHolder.MessagesCounter.setVisibility(View.INVISIBLE);
        }


        return view;
    }

}
