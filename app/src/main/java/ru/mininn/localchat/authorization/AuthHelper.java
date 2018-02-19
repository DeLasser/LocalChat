package ru.mininn.localchat.authorization;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import ru.mininn.localchat.R;
import ru.mininn.localchat.chat.model.User;

public class AuthHelper {
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_PASSWORD = "password";
    private SharedPreferences sharedPreferences;

    public AuthHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public void setCurrentUser(User user) {
        sharedPreferences.edit().putInt(KEY_USER_ID, user.getId()).apply();
        sharedPreferences.edit().putString(KEY_LOGIN, user.getLogin()).apply();
        sharedPreferences.edit().putString(KEY_PASSWORD, user.getLogin()).apply();
    }

    public void removeCurrentAccount() {
        sharedPreferences.edit().remove(KEY_USER_ID).apply();
        sharedPreferences.edit().remove(KEY_LOGIN).apply();
        sharedPreferences.edit().remove(KEY_PASSWORD).apply();
    }

    @Nullable
    public User getCurrentUser() {
        if (sharedPreferences.getString(KEY_LOGIN, null) != null) {
            return new User(sharedPreferences.getInt(KEY_USER_ID, 0),
                    sharedPreferences.getString(KEY_LOGIN, null),
                    sharedPreferences.getString(KEY_PASSWORD, null));
        }
        return null;
    }
}
