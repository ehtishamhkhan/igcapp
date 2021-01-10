package com.archetech.incognichat;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;

public class SentMessageViewHolder extends RecyclerView.ViewHolder {

    public final String TAG = this.getClass().getSimpleName() +" ---> ";

    TextView MessageBody;
    TextView MessageTime;
    ImageView MessageStatus;
    SpinKitView SpinKitWaitingSent;

    SentMessageViewHolder(View itemView) {
        super(itemView);
        MessageBody=itemView.findViewById(R.id.message_body);
        MessageTime =itemView.findViewById(R.id.message_time);
        MessageStatus=itemView.findViewById(R.id.message_status);
        SpinKitWaitingSent = itemView.findViewById(R.id.waiting_sent);
    }

    void bind(Message message) {
        MessageBody.setText(message.getText());
        MessageTime.setText(message.getCreated_at());
    }
}
