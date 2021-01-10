package com.archetech.incognichat;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MessageController {

    public final String TAG = this.getClass().getSimpleName() +" ---> ";

    Context ctx;
    Realm db;

    public MessageController(Context _ctx)
    {
        ctx = _ctx;
        Realm.init(ctx);
        db = Realm.getDefaultInstance();
    }

    public Integer getUnreadMessages(String _conversationId)
    {
        RealmResults<Message> messages = db.where(Message.class).equalTo("conversation_id",Long.valueOf(_conversationId)).lessThan("status",Long.valueOf(3)).equalTo("received",true).findAll();
        return messages.size();
    }

    public Boolean setSeen(String _conversationId)
    {
        RealmResults<Message> messages = db.where(Message.class).equalTo("conversation_id",Long.valueOf(_conversationId)).lessThan("status",Long.valueOf(3)).equalTo("received",false).findAll();
        db.beginTransaction();
        for (Message msg:messages){
            msg.setStatus(3);
            msg.setUpdated();
        }
        db.commitTransaction();
        return true;
    }

    public Boolean setReceivedSeen(String _conversationId)
    {
        RealmResults<Message> messages = db.where(Message.class).equalTo("conversation_id",Long.valueOf(_conversationId)).lessThan("status",Long.valueOf(3)).equalTo("received",true).findAll();
        db.beginTransaction();
        for (Message msg:messages){
            msg.setStatus(3);
            msg.setUpdated();
        }
        db.commitTransaction();
        return true;
    }

    public Boolean setMessageReceived(String _messageId)
    {
        Message message = db.where(Message.class).equalTo("message_id",Long.valueOf(_messageId)).findFirst();
        if(message!=null)
        {
            db.beginTransaction();
            message.setStatus(2);
            message.setUpdated();
            db.commitTransaction();
            return true;
        }
       return false;
    }

    public String getLastMessagePreview(String _conversationId)
    {
        RealmResults<Message> message=db.where(Message.class).equalTo("conversation_id",Long.valueOf(_conversationId)).findAll().sort("updated", Sort.ASCENDING);
        if(message.size()>0)
        {
            String text = message.last().getText();
            if(text.length()>20)
                text=text.substring(0,20);
            text+="...";
            return text;
        }
        return "No new message";
    }






    public Boolean setMessageSent(String _messageId)
    {
        Message message = db.where(Message.class).equalTo("message_id",Long.valueOf(_messageId)).findFirst();
        if(message!=null)
        {
            db.beginTransaction();
            message.setStatus(1);
            message.setUpdated();
            db.commitTransaction();
            return true;
        }
        return false;
    }


    public Boolean createNewSentMessage(String _messageId,Long _conversationId,String _text,String _replyId)
    {
        try
        {
            db.beginTransaction();
            Message message = db.createObject(Message.class);
            message.setMessage_id(Long.parseLong(_messageId));
            message.setText(_text);
            message.setReply_id(Long.parseLong(_replyId));
            message.setIs_system(false);
            message.setCreated_at();
            message.setStatus(0);
            message.setReceived(false);
            message.setConversation_id(_conversationId);
            message.setUpdated();
            db.commitTransaction();
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public Boolean createNewReceivedMessage(String _messageId,String _conversationId,String _text,String _replyId)
    {
        Log.d(TAG,"msgid: "+_messageId+" -> convoId: "+_conversationId+" -> text: "+_text+" -> reply: "+_replyId);
        try
        {
            db.beginTransaction();
            Message message = db.createObject(Message.class);
            message.setMessage_id(Long.parseLong(_messageId));
            message.setText(_text);
            message.setReply_id(Long.parseLong(_replyId));
            message.setIs_system(false);
            message.setCreated_at();
            message.setStatus(1);
            message.setReceived(true);
            message.setConversation_id(Long.parseLong(_conversationId));
            message.setUpdated();
            db.commitTransaction();
            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.d(TAG,"Error: "+ex.toString());
            return false;
        }
    }

}
