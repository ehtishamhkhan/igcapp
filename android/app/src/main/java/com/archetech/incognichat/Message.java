package com.archetech.incognichat;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;

public class Message extends RealmObject {

    private long message_id;
    private String text;
    private boolean is_system;
    private boolean received;
    private long reply_id;
    private String created_at;
    private long status;
    private long conversation_id;
    private Date updated;


    public long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(long message_id) {
        this.message_id = message_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getReceived() {
        return received;
    }

    public void setReceived(Boolean received) {
        this.received = received;
    }

    public long getReply_id() {
        return reply_id;
    }

    public void setReply_id(long reply_id) {
        this.reply_id = reply_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at() {
        this.created_at = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(long conversation_id) {
        this.conversation_id = conversation_id;
    }

    public boolean getIs_system() {
        return is_system;
    }

    public void setIs_system(boolean is_system) {
        this.is_system = is_system;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated() {
        this.updated = new Date();
    }
}
