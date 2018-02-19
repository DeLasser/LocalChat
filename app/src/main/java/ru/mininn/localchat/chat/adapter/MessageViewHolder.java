package ru.mininn.localchat.chat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import ru.mininn.localchat.R;
import ru.mininn.localchat.chat.model.Message;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    private TextView name;
    private TextView message;
    private TextView date;

    public MessageViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        message = itemView.findViewById(R.id.message);
        date = itemView.findViewById(R.id.date);
    }

    public void bind(Message m) {
        name.setText(m.getAuthor().getLogin());
        message.setText(m.getMessage());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm");
        date.setText("" + dateFormat.format(m.getDate()));
    }
}
