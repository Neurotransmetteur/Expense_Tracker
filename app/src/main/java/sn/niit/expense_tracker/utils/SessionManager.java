package sn.niit.expense_tracker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SessionManager {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private static final String PREF_NAME = "login_pref";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_CURRENT_BALANCE = "current_balance";

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Set login state
    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    // Get login state
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Set username
    public void setUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    // Get username
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    // Set user ID
    public void setUserId(int userId) {
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    // Get user ID
    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    // Set current balance
    public void setCurrentBalance(double currentBalance) {
        editor.putFloat(KEY_CURRENT_BALANCE, (float) currentBalance);
        editor.apply();
    }

    // Get current balance
    public double getCurrentBalance() {
        return sharedPreferences.getFloat(KEY_CURRENT_BALANCE, 0.0f);
    }

    // Clear session data (useful for logout)
    public void clearSession() {
        editor.clear();
        editor.apply();
    }


}
