package com.archetech.incognichat;

public class API
{
    public static final String API = "http://192.168.0.24:8000/api/";
    public static final String URL_REGISTER = API+"register";
    public static final String URL_GET_PUBLIC_KEY = API+"pubkey";
    public static final String URL_SEARCH = API+"find";
    public static final String URL_COMPONSE_MESSAGE = API+"compose";
    public static final String URL_SEND_MESSAGE = API+"send";
    public static final String URL_GET_CONVERSATION_CREDENTIALS = API+"cpass";
    public static final String URL_GET_PROFILE_STATS = API+"stats";
    public static final String URL_SEND_MESSAGE_RECEIVERD_ACKNOWLEDGEMENT = API+"received";
    public static final String URL_CONVERSATION_SEEN_ACKNOWLEDGEMENT = API+"seen";
    public static final String URL_CONVERSATION_BLOCK = API+"block";
    public static final String URL_SEND_SELF = API+"self";
}
