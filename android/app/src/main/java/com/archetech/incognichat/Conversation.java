package com.archetech.incognichat;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;

public class Conversation extends RealmObject {
    private long conversation_id;
    private boolean received;
    private String handle;
    private String nick_name;
    private String draft;
    private String created_at;
    private String password;
    private Boolean blocked;
    private String salt;
    private String avatar;
    private Date updated;

    public long getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(Long conversation_id) {
        this.conversation_id = conversation_id;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getDraft() {
        return draft;
    }

    public void setDraft(String draft) {
        this.draft = draft;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at() {
        this.created_at = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated() {
        this.updated = new Date();
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }
}
