package com.archetech.incognichat;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;

public class Application extends RealmObject {

    private String nickname;
    private String access_token;
    private String version;
    private String created_at;
    private String synced_at;
    private String handle;
    private String public_key;
    private String private_key;
    private String recovery_pin;
    private long reputation;
    private long views;
    private long badges_created;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at() {
        this.created_at = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getRecovery_pin() {
        return recovery_pin;
    }

    public void setRecovery_pin(String recovery_pin) {
        this.recovery_pin = recovery_pin;
    }

    public long getReputation() {
        return reputation;
    }

    public void setReputation(long reputation) {
        this.reputation = reputation;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getBadges_created() {
        return badges_created;
    }

    public void setBadges_created(long badges_created) {
        this.badges_created = badges_created;
    }

    public String getSynced_at() {
        return synced_at;
    }

    public void setSynced_at() {
        this.synced_at =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
