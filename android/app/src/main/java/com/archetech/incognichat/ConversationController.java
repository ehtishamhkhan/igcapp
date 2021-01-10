package com.archetech.incognichat;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;

public class ConversationController {

    public final String TAG = this.getClass().getSimpleName();
    Context ctx;
    Realm db;

    public ConversationController(Context _ctx)
    {
        ctx = _ctx;
        Realm.init(ctx);
        db = Realm.getDefaultInstance();
    }

    public void blockConversation(String _conversationId)
    {
        Conversation conversation = db.where(Conversation.class).equalTo("conversation_id",Long.valueOf(_conversationId)).findFirst();
        db.beginTransaction();
        if(conversation.getBlocked())
            conversation.setBlocked(false);
        else
            conversation.setBlocked(true);
        db.commitTransaction();
    }

    public void updateConversationNickName(String _conversationId,String _newName)
    {
        Conversation conversation = db.where(Conversation.class).equalTo("conversation_id",Long.valueOf(_conversationId)).findFirst();
        db.beginTransaction();
        conversation.setNick_name(_newName);
        db.commitTransaction();
    }

    public String getConversationNickname(String _conversationId)
    {
        return db.where(Conversation.class).equalTo("conversation_id",Long.valueOf(_conversationId)).findFirst().getNick_name();
    }

    public void updateConversationPriority(String _conversationId)
    {
        db.beginTransaction();
        db.where(Conversation.class).equalTo("conversation_id",Long.valueOf(_conversationId)).findFirst().setUpdated();
        db.commitTransaction();
    }

    public boolean conversationExist(String _conversationId)
    {
        if(db.where(Conversation.class).equalTo("conversation_id",Long.valueOf(_conversationId)).findFirst()!=null)
        {
            return true;
        }
        return false;
    }

    public String getConversationIdAgainstHAndle(String _handle)
    {
        Conversation conversation = db.where(Conversation.class).equalTo("handle",_handle).findFirst();
        return  String.valueOf(conversation.getConversation_id());

    }

    public boolean conversationAlreadyExistAgainstHandle(String _handle)
    {
        if(db.where(Conversation.class).equalTo("handle",_handle).findFirst()!=null)
        {
            return true;
        }
        return false;
    }

    public String getConversationPassword(String _conversationId)
    {
        return db.where(Conversation.class).equalTo("conversation_id",Long.valueOf(_conversationId)).findFirst().getPassword();
    }

    public String getConversationSalt(String _conversationId)
    {
        return db.where(Conversation.class).equalTo("conversation_id",Long.valueOf(_conversationId)).findFirst().getSalt();
    }

    public boolean createNewConversation(String _conversationId,String _password,String _salt,Boolean _received,String _handle)
    {
        try
        {
            db.beginTransaction();
            Conversation conversation = db.createObject(Conversation.class);
            conversation.setConversation_id(Long.parseLong(_conversationId));
            conversation.setHandle(_handle);
            conversation.setReceived(_received);
            conversation.setNick_name("Incognito "+_conversationId);
            conversation.setDraft("");
            conversation.setSalt(_salt);
            conversation.setPassword(_password);
            conversation.setAvatar("#000000");
            conversation.setCreated_at();
            conversation.setUpdated();
            conversation.setBlocked(false);
            db.commitTransaction();
            return true;
        }
        catch (Exception ex)
        {
            Log.d(TAG,"Error while creating new conversation: "+ex.toString());
            ex.printStackTrace();
            return false;
        }
    }
}
