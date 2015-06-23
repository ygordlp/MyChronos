package br.com.atlantico.mychronos.db;

/**
 * Created by pereira_ygor on 15/06/2015.
 */
public abstract class TaskEntry {

    public static final String TABLE_NAME = "Tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    public static final String[] PROJECTION = new String[]
            {
                    COLUMN_ID, COLUMN_NAME
            };

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT" +
                    ");";

    public static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_LIMBO =
            "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ") VALUES ('Limbo');";
}
