package ru.mininn.localchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ru.mininn.localchat.authorization.AuthHelper;
import ru.mininn.localchat.authorization.AuthorizationActivity;
import ru.mininn.localchat.chat.database.DatabaseHelper;
import ru.mininn.localchat.chat.fragment.ChatFragment;

public class ChatActivity extends AppCompatActivity {
    private AuthHelper authHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        authHelper = new AuthHelper(this);
        initChatFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.delete :
                deleteAccount();
            case R.id.logout :
                logout();
                break;

        }
        return true;
    }

    private void deleteAccount() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.deleteAccount(authHelper.getCurrentUser());
    }

    private void logout() {
        authHelper.removeCurrentAccount();
        startActivity(new Intent(this, AuthorizationActivity.class));
        this.finish();
    }

    private void initChatFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new ChatFragment())
                .commit();
    }

}
