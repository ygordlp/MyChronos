package br.com.atlantico.mychronos.db;

/**
 * Created by pereira_ygor on 15/06/2015.
 */
public abstract class TimestampEntry {

    public static final String TABLE_NAME = "Timestamps";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIME = "time";

    public static final String[] PROJECTION = new String[]
            {
                    COLUMN_ID, COLUMN_TIME
            };

    public static final String CREATE_TIMESTAMP_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TIME + " INTEGER" +
                    ");";

    public static final String DELETE_TIMESTAMP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
