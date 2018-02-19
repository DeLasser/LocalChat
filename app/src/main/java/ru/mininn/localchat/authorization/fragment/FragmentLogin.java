package ru.mininn.localchat.authorization.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.mininn.localchat.ChatActivity;
import ru.mininn.localchat.R;
import ru.mininn.localchat.authorization.AuthHelper;
import ru.mininn.localchat.chat.database.DatabaseHelper;
import ru.mininn.localchat.chat.model.User;

public class FragmentLogin extends Fragment implements View.OnClickListener {
    private EditText loginET;
    private EditText passwordET;
    private Button loginBtn;
    private Button signupBtn;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viwe = inflater.inflate(R.layout.fragment_login, container, false);
        databaseHelper = new DatabaseHelper(getActivity());
        initView(viwe);
        return viwe;
    }

    private void initView(View view) {
        loginET = view.findViewById(R.id.login);
        passwordET = view.findViewById(R.id.password);
        loginBtn = view.findViewById(R.id.login_button);
        signupBtn = view.findViewById(R.id.signup_button);
        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button :
                login();
                break;
            case R.id.signup_button :
                signup();
                break;
        }
    }

    private void signup() {
        if (loginET.getText().length() > 0
                && passwordET.getText().toString().length() > 0) {
            if (databaseHelper.getUserByLogin( loginET.getText().toString()) == null) {
                databaseHelper.addUser(new User(loginET.getText().toString(), passwordET.getText().toString()));
                Toast.makeText(getActivity(), R.string.account_created, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), R.string.account_exist, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), R.string.short_login_data, Toast.LENGTH_SHORT).show();
        }
    }

    private void login() {
        User user = databaseHelper.getUserByAuthData(loginET.getText().toString(), passwordET.getText().toString());
        if (user != null) {
            AuthHelper authHelper = new AuthHelper(getActivity());
            authHelper.setCurrentUser(user);
            startActivity(new Intent(getActivity(),ChatActivity.class));
            getActivity().finish();
        }
    }
}
