package sg.edu.np.cosign;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final String TAG = "MyDBHandler";

    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "accountDB.db";
    public static final String ACCOUNTS = "Accounts";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORD = "Password";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + ACCOUNTS + "(" + COLUMN_USERNAME + " TEXT," + COLUMN_EMAIL + " TEXT," + COLUMN_PASSWORD
                + " TEXT" + ")";
        db.execSQL(CREATE_ACCOUNTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //this drops any version before this new version
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS);
        onCreate(db);
    }

    public void createTestAccount(){
        ContentValues values= new ContentValues();
        values.put(COLUMN_USERNAME, "CoSign");
        values.put(COLUMN_EMAIL, "cosign@gmail.com");
        values.put(COLUMN_PASSWORD,"password123");
        SQLiteDatabase database= this.getWritableDatabase();
        Log.v(TAG,values.toString());
        database.insert(ACCOUNTS,null,values);
        database.close();
    }

    public void addUser(UserData userData)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userData.getMyUsername());
        values.put(COLUMN_EMAIL, userData.getMyEmail());
        values.put(COLUMN_PASSWORD, userData.getMyPassword());
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v(TAG, values.toString());
        db.insert(ACCOUNTS, null, values);
        db.close();
    }

    public UserData findUser(String username)
    {
        String query = "SELECT * " +
                "FROM " + ACCOUNTS +
                " WHERE " + COLUMN_USERNAME + "=\"" + username + "\"" +
                " OR " + COLUMN_EMAIL + "=\"" + username + "\"" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        UserData queryData = new UserData();

        if(cursor.moveToFirst())
        {
            queryData.setMyUsername(cursor.getString(0));
            queryData.setMyPassword(cursor.getString(2));
            queryData.setMyEmail(cursor.getString(1));
        }
        else
        {
            queryData = null;
        }
        db.close();
        return queryData;
    }

    public UserData getAllUsers()
    {
        String query = "SELECT * FROM " + ACCOUNTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                UserData userData = new UserData();
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                Log.d("DEBUG", "Email is : " + email);
            } while(cursor.moveToNext());
        }

        UserData queryData = new UserData();
        return queryData;
    }

}
