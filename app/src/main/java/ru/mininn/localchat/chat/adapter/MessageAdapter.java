package ru.mininn.localchat.chat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.mininn.localchat.R;
import ru.mininn.localchat.chat.model.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    public void update(List<Message> messages) {
        this.messages = messages;
        this.notifyDataSetChanged();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
