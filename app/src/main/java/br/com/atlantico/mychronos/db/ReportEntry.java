package br.com.atlantico.mychronos.db;

/**
 * Created by pereira_ygor on 15/06/2015.
 */
public abstract class ReportEntry {

    public static final String TABLE_NAME = "Reports";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK_ID = "task_id";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";

    public static final String[] PROJECTION = new String[]
            {
                    COLUMN_ID, COLUMN_TASK_ID, COLUMN_START_TIME, COLUMN_END_TIME
            };

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TASK_ID + " INTEGER, " +
                    COLUMN_START_TIME + " INTEGER, " +
                    COLUMN_END_TIME + " INTEGER" +
                    ");";

    public static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
