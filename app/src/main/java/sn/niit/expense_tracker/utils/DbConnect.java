package sn.niit.expense_tracker.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import sn.niit.expense_tracker.domain.User;

public class DbConnect extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense_tracker.db";
    private static final int DATABASE_VERSION = 1;

    // User Table
    public static final String TABLE_USERS = "USERS";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_ACCESS_CODE = "access_code";
    public static final String COLUMN_CURRENT_BALANCE = "current_balance";

    // Transaction Table
    public static final String TABLE_TRANSACTIONS = "TRANSACTIONS";
    public static final String COLUMN_TRANSACTION_ID = "transaction_id";
    public static final String COLUMN_TRANSACTION_DATE = "date";
    public static final String COLUMN_TRANSACTION_CATEGORY = "category";
    public static final String COLUMN_TRANSACTION_DESCRIPTION = "description";
    public static final String COLUMN_TRANSACTION_TOTAL = "total";
    public static final String COLUMN_TRANSACTION_TYPE = "type";
    public static final String COLUMN_USER_ID_FK = "user_id";

    // SQL to create the USERS table
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " VARCHAR(64) NOT NULL, " +
            COLUMN_ACCESS_CODE + " VARCHAR(128) NOT NULL, " +
            COLUMN_CURRENT_BALANCE + " REAL" +
            ");";

    // SQL to create the TRANSACTIONS table
    private static final String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS + " (" +
            COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TRANSACTION_DATE + " TEXT NOT NULL, " +
            COLUMN_TRANSACTION_CATEGORY + " TEXT NOT NULL, " +
            COLUMN_TRANSACTION_DESCRIPTION + " TEXT, " +
            COLUMN_TRANSACTION_TOTAL + " REAL NOT NULL, " +
            COLUMN_TRANSACTION_TYPE + " TEXT NOT NULL, " +
            COLUMN_USER_ID_FK + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")" +
            ");";

    public DbConnect(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_TRANSACTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean addUser(String username, String accessCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_ACCESS_CODE, accessCode);
        values.put(COLUMN_CURRENT_BALANCE, 0.0);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1; // returns true if the insert was successful
    }

    // Verify user and return the User object if credentials match
    public User verifyUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String query = "SELECT * FROM USERS WHERE username = ? AND access_code = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String foundUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String foundPassword = cursor.getString(cursor.getColumnIndexOrThrow("access_code"));
            double currentBalance = cursor.getDouble(cursor.getColumnIndexOrThrow("current_balance"));

            user = new User(id, foundUsername, foundPassword, currentBalance,true);
        }
        cursor.close();
        db.close();

        return user;
    }
}

