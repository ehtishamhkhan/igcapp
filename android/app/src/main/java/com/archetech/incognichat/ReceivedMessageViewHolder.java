package com.archetech.incognichat;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {

    public final String TAG = this.getClass().getSimpleName() +" ---> ";

    TextView MessageBody;
    TextView MessageTime;

    ReceivedMessageViewHolder(View itemView) {
        super(itemView);
        MessageBody=itemView.findViewById(R.id.message_body);
        MessageTime =itemView.findViewById(R.id.message_time);
    }

    void bind(Message message) {
        MessageBody.setText(message.getText());
        MessageTime.setText(message.getCreated_at());
    }
}
