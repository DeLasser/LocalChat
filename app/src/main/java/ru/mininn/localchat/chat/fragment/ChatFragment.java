package ru.mininn.localchat.chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Date;

import ru.mininn.localchat.R;
import ru.mininn.localchat.authorization.AuthHelper;
import ru.mininn.localchat.chat.adapter.MessageAdapter;
import ru.mininn.localchat.chat.database.DatabaseHelper;
import ru.mininn.localchat.chat.model.Message;

public class ChatFragment extends Fragment {
    private EditText message;
    private ImageView sendButton;
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private MessageAdapter messageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        databaseHelper = new DatabaseHelper(getActivity());
        initView(view);
        initRecyclerView();
        return view;
    }

    private void initView(View view) {
        message = view.findViewById(R.id.message);
        sendButton = view.findViewById(R.id.send_button);
        recyclerView = view.findViewById(R.id.recycler_view);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void initRecyclerView() {
        messageAdapter = new MessageAdapter(databaseHelper.getAllMessages());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(messageAdapter);
    }

    private void sendMessage() {
        if (message.getText().length() > 0) {
            AuthHelper authHelper = new AuthHelper(getActivity());
            databaseHelper.addMessage(new Message(authHelper.getCurrentUser(), message.getText().toString(), new Date()));
            messageAdapter.update(databaseHelper.getAllMessages());
            message.setText("");
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        }

    }
}
