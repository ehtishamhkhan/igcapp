package com.archetech.incognichat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ChatsFragment extends Fragment {

    public final String TAG = this.getClass().getSimpleName()+"--->";
    Realm db;
    ListView conversationsListView;
    ConversationsAdapter conversationsAdapter;
    BroadcastReceiver broadcastReceiver;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Realm.init(getActivity());
        db=Realm.getDefaultInstance();
        initializeBroadcastReceiver();
        return inflater.inflate(R.layout.fragment_chats,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        conversationsListView=getActivity().findViewById(R.id.conversations_list);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoadConversations();
            }
        },10);
    }


    private void initializeBroadcastReceiver()
    {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.archetech.incognichat.newMessage");
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                conversationsAdapter.notifyDataSetChanged();
                Log.d(TAG,"Broadcast Received");
            }
        };
        getActivity().registerReceiver(broadcastReceiver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(conversationsAdapter!=null)
        {
            conversationsAdapter.notifyDataSetChanged();
        }
    }

    public void LoadConversations()
    {
        try
        {
            RealmResults<Conversation> conversations = db.where(Conversation.class).findAll().sort("updated",Sort.DESCENDING);
            conversationsAdapter=new ConversationsAdapter(getActivity().getApplicationContext(),conversations);
            conversationsListView.setAdapter(conversationsAdapter);
            conversationsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    if(conversations.get(position).getBlocked())
                    {
                        Toasty.error(getActivity(), "This Conversation is blocked", Toast.LENGTH_SHORT, true).show();
                    }
                    else
                    {
                        startChatDetailsActivity(String.valueOf(conversationsAdapter.getItemId(position)));
                    }
                    return true;
                }
            });
            conversationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startChatActivity(String.valueOf(conversationsAdapter.getItemId(position)));
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.d(TAG,ex.toString());
        }
    }


    public void startChatDetailsActivity(String _conversationId)
    {
        Intent nextActivityIntent = new Intent(getActivity(), ChatDetailsActivity.class);
        nextActivityIntent.putExtra("conversation_id",_conversationId);
        this.startActivity(nextActivityIntent);
    }

    public void startChatActivity(String _conversationId)
    {
        Intent nextActivityIntent = new Intent(getActivity(), ChatActivity.class);
        nextActivityIntent.putExtra("conversation_id",_conversationId);
        this.startActivity(nextActivityIntent);
    }
}
