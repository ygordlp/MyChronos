package br.com.atlantico.mychronos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pereira_ygor on 15/06/2015.
 */
public class ChronosDBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;

    public static final String DB_NAME = "MyChronos.db";

    public ChronosDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TimestampEntry.CREATE_TIMESTAMP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TimestampEntry.DELETE_TIMESTAMP_TABLE);
        onCreate(db);
    }
}

