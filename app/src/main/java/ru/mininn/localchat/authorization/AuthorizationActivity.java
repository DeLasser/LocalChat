package ru.mininn.localchat.authorization;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ru.mininn.localchat.ChatActivity;
import ru.mininn.localchat.R;
import ru.mininn.localchat.authorization.fragment.FragmentLogin;

public class AuthorizationActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        checkCurrentUser();
    }

    private void checkCurrentUser() {
        AuthHelper authHelper = new AuthHelper(this);
        if (authHelper.getCurrentUser() == null) {
            initLoginFragment();
        } else {
            startActivity(new Intent(this, ChatActivity.class));
            this.finish();
        }
    }

    private void initLoginFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new FragmentLogin())
                .commit();
    }
}
