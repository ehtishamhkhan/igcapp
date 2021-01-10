package com.archetech.incognichat;

import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeFormatter {

    public final String TAG = this.getClass().getSimpleName() +" ---> ";

    public static enum FormatType {
        WelcomeMessageTimestamp(0),
        ChatMessageShortTimestamp(1),
        ChatMessageLongTimestamp(2),
        ConversationShortTimestamp(3),
        ConversationLongTimestamp(4);

        private final int code;

        FormatType(int code)
        {
            this.code = code;
        }
        public int getCode()
        {
            return this.code;
        }
    }

    private Boolean isToday;
    private Calendar cal;
    private Date today;
    Date given;
    String pattern;
    SimpleDateFormat sdf;

    public TimeFormatter()
    {
        cal = Calendar.getInstance();
        today = new Date();
        isToday=IsToday();
        pattern="";
    }

    private Date GetTimestampObj(String _timestamp)
    {
        Log.d(TAG,_timestamp+" --->");
        cal.setTimeInMillis(Timestamp.valueOf(_timestamp).getTime());
        return  cal.getTime();
    }

    private  Boolean IsToday()
    {
        if(((int) ((today.getTime() - today.getTime()) / (1000 * 60 * 60 * 24)))<2)
            return true;
        return false;
    }

    public  String FormatTimestamp(String _timestamp,Boolean _ampm,FormatType _formatType)
    {
        given = GetTimestampObj(_timestamp);
        pattern="";

        switch (_formatType)
        {
            case WelcomeMessageTimestamp:
            {
                if(_ampm)
                    pattern="EEE MMM dd, YYYY hh:mm a";
                pattern="EEE MMM dd, YYYY - HH:mm";
            }break;
            case ChatMessageShortTimestamp:
            {
                if(!isToday)
                    pattern="MMM dd - ";
                if(_ampm)
                {
                    pattern=pattern+"hh:mm a";
                }
                else
                {
                    pattern=pattern+"HH:mm";
                }
            }break;
            case ChatMessageLongTimestamp:
            {
                if(_ampm)
                    pattern="MMM dd, yy - hh:mm::ss a";
                pattern="MMM dd, yy HH:mm:ss";
            }break;
            case ConversationShortTimestamp:
            {
                if(!isToday)
                {
                    if(!isToday)
                        pattern="MMM dd - ";
                }
                if(_ampm)
                {
                    pattern=pattern+"hh:mm a";
                }
                else
                {
                    pattern=pattern+"HH:mm";
                }
            }break;
            case ConversationLongTimestamp:
            {
                if(_ampm)
                    pattern="EEE MMM dd, yy - hh:mm:ss a";
                pattern="EEE MMM dd, yy - HH:mm:ss";
            }break;
        }

        sdf=new SimpleDateFormat(pattern);
        return sdf.format(given);
    }

}
