package ru.mininn.localchat.chat.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.mininn.localchat.chat.model.Message;
import ru.mininn.localchat.chat.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "chat.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_MESSAGES = "messages";
    //similar
    private static final String KEY_ID = "_id";
    //users
    private static final String KEY_LOGIN = "login";
    private static final String KEY_PASSWORD = "password";
    //messages
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_AUTHOR_ID = "author";
    private static final String KEY_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_LOGIN + " TEXT not null,"
                + KEY_PASSWORD + " TEXT not null"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_MESSAGE + " TEXT not null,"
                + KEY_AUTHOR_ID + " INTEGER not null,"
                + KEY_DATE + " INTEGER not null"
                + ")";
        db.execSQL(CREATE_MESSAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LOGIN, user.getLogin());
        values.put(KEY_PASSWORD, user.getPassword());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void addMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, message.getMessage());
        values.put(KEY_AUTHOR_ID, message.getAuthor().getId());
        values.put(KEY_DATE, message.getTimestamp());
        db.insert(TABLE_MESSAGES, null, values);
        Log.d("dfsdfsf", "added");
        db.close();
    }

    public User getUserByAuthData(String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, KEY_LOGIN + "=? and " + KEY_PASSWORD + "=?",
                new String[]{login, password}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return new User(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2));
            }
        }
        return null;
    }

    public User getUserByLogin(String login) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, KEY_LOGIN + "=?",
                new String[]{login}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return new User(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2));
            }
        }
        return null;
    }

    public void deleteAccount(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + "= ?", new String[]{String.valueOf(user.getId())});
    }

    public List<Message> getAllMessages() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Message> messages = new ArrayList<Message>();

        String table = TABLE_MESSAGES + " inner join " + TABLE_USERS
                + " on " + TABLE_MESSAGES + "." + KEY_AUTHOR_ID + " = " + TABLE_USERS + "." + KEY_ID;
        String columns[] = {
                TABLE_USERS + "." + KEY_ID + " as author_id",
                TABLE_USERS + "." + KEY_LOGIN + " as login",
                TABLE_MESSAGES + "." + KEY_ID + " as id",
                TABLE_MESSAGES + "." + KEY_MESSAGE + " as message",
                TABLE_MESSAGES + "." + KEY_DATE + " as date",
        };
        Cursor cursor = db.query(table, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User(cursor.getInt(0), cursor.getString(1), "");
                messages.add(new Message(cursor.getInt(2), user, cursor.getString(3), cursor.getLong(4)));
            } while (cursor.moveToNext());
        }
        Log.d("asdasd", messages.size() + "");
        return messages;
    }
}