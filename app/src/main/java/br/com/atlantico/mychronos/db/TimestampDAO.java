package br.com.atlantico.mychronos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.atlantico.mychronos.model.Timestamp;

/**
 * Created by pereira_ygor on 18/06/2015.
 */
public class TimestampDAO {

    private static TimestampDAO instance;

    private Context context;
    private ChronosDBHelper mDbHelper;

    private TimestampDAO(Context ctx) {
        this.context = ctx;
        mDbHelper = new ChronosDBHelper(ctx);
    }

    public static TimestampDAO getInstance(Context ctx) {
        if (instance == null) {
            instance = new TimestampDAO(ctx);
        }

        return instance;
    }

    public long add(Timestamp ts) {
        long res = 0;

        if (ts != null) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(TimestampEntry.COLUMN_TIME, ts.getTime());

            res = db.insert(TimestampEntry.TABLE_NAME, null, values);
        }

        return res;
    }

    public Timestamp getById(int id) {
        Timestamp res = null;

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor c = db.query(
                TimestampEntry.TABLE_NAME,
                TimestampEntry.PROJECTION,
                TimestampEntry.COLUMN_ID + " = " + id,
                null, null, null, null
        );

        if (c.moveToFirst()) {
            res = new Timestamp(c.getLong(c.getColumnIndex(TimestampEntry.COLUMN_TIME)));
            res.setId(c.getLong(c.getColumnIndex(TimestampEntry.COLUMN_ID)));

        }

        return res;
    }

    private ArrayList<Timestamp> getAll(String where) {
        ArrayList<Timestamp> timestamps = new ArrayList<Timestamp>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor c = db.query(
                TimestampEntry.TABLE_NAME,
                TimestampEntry.PROJECTION,
                where, null, null, null, null
        );

        if (c.moveToFirst()) {
            Timestamp ts;
            ts = new Timestamp(c.getLong(c.getColumnIndex(TimestampEntry.COLUMN_TIME)));
            ts.setId(c.getLong(c.getColumnIndex(TimestampEntry.COLUMN_TIME)));
            timestamps.add(ts);

            while (c.moveToNext()) {
                ts = new Timestamp(c.getLong(c.getColumnIndex(TimestampEntry.COLUMN_TIME)));
                ts.setId(c.getLong(c.getColumnIndex(TimestampEntry.COLUMN_TIME)));
                timestamps.add(ts);
            }
        }

        return timestamps;
    }

    public ArrayList<Timestamp> getAll() {
        return getAll(null);
    }

    /**
     * Get all timestamps from the day in the fromat YYYY-MM-DD
     *
     * @param sqlDateFormat
     * @return
     */
    public ArrayList<Timestamp> getAllFromDate(String sqlDateFormat) {
        String where = "STRFTIME('%Y-%m-%d', datetime("+TimestampEntry.COLUMN_TIME+"/1000, 'unixepoch')) LIKE '" + sqlDateFormat + "'";
        return getAll(where);
    }

    public Timestamp getLast(){
        Timestamp res = null;

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor c = db.query(
                TimestampEntry.TABLE_NAME,
                TimestampEntry.PROJECTION,
                null, null, null, null, null
        );

        if (c.moveToLast()) {
            res = new Timestamp(c.getLong(c.getColumnIndex(TimestampEntry.COLUMN_TIME)));
            res.setId(c.getLong(c.getColumnIndex(TimestampEntry.COLUMN_TIME)));
        }

        return res;
    }

    public boolean update(Timestamp ts) {
        boolean res = false;

        if (ts != null && ts.getId() > 0) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(TimestampEntry.COLUMN_TIME, ts.getTime());

            int count = db.update(TimestampEntry.TABLE_NAME, values, TimestampEntry.COLUMN_ID + " = " + ts.getId(), null);
            res = count > 0;
        }

        return res;
    }

    public boolean delete(Timestamp ts) {
        boolean res = false;

        if (ts != null && ts.getId() > 0) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            int count = db.delete(TimestampEntry.TABLE_NAME, TimestampEntry.COLUMN_ID + " = " + ts.getId(), null);
            res = count > 0;
        }

        return res;
    }
}
