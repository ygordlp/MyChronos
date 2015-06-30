package br.com.atlantico.mychronos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.atlantico.mychronos.model.Task;

/**
 * Created by Ygor Duarte on 18/06/2015.
 */
public class TaskDAO {

    private static TaskDAO instance;

    private ChronosDBHelper mDbHelper;

    private TaskDAO(Context ctx) {
        mDbHelper = new ChronosDBHelper(ctx);
    }

    public static TaskDAO getInstance(Context ctx) {
        if (instance == null) {
            instance = new TaskDAO(ctx);
        }

        return instance;
    }

    public long add(Task task) {
        long res = 0;

        if (task != null) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(TaskEntry.COLUMN_NAME, task.getName());

            res = db.insert(TaskEntry.TABLE_NAME, null, values);
        }

        return res;
    }

    public Task getById(int id) {
        Task res = null;

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor c = db.query(
                TaskEntry.TABLE_NAME,
                TaskEntry.PROJECTION,
                TaskEntry.COLUMN_ID + " = " + id,
                null, null, null, null
        );

        if (c.moveToFirst()) {
            res = new Task(id, c.getString(c.getColumnIndex(TaskEntry.COLUMN_NAME)));
        }

        c.close();

        return res;
    }

    public Task getByName(String name) {
        Task res = null;

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor c = db.query(
                TaskEntry.TABLE_NAME,
                TaskEntry.PROJECTION,
                TaskEntry.COLUMN_NAME+ " LIKE '" + name + "'",
                null, null, null, null
        );

        if (c.moveToFirst()) {
            res = new Task(c.getLong(c.getColumnIndex(TaskEntry.COLUMN_ID)), name);
        }

        return res;
    }

    private ArrayList<Task> getAll(String where, String orderBy) {
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor c = db.query(
                TaskEntry.TABLE_NAME,
                TaskEntry.PROJECTION,
                where, null, null, null, orderBy
        );

        if (c.moveToFirst()) {
            Task task;
            long id = c.getLong(c.getColumnIndex(TaskEntry.COLUMN_ID));
            String name = c.getString(c.getColumnIndex(TaskEntry.COLUMN_NAME));
            task = new Task(id, name);

            tasks.add(task);

            while (c.moveToNext()) {
                id = c.getLong(c.getColumnIndex(TaskEntry.COLUMN_ID));
                name = c.getString(c.getColumnIndex(TaskEntry.COLUMN_NAME));
                task = new Task(id, name);
                tasks.add(task);
            }
        }

        c.close();

        return tasks;
    }

    public ArrayList<Task> getAll() {
        return getAll(null, null);
    }

    public ArrayList<Task> getAllOrderBy(String orderBy) {
        return getAll(null, orderBy);
    }

    public boolean update(Task task) {
        boolean res = false;

        if (task != null && task.getId() > 0) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(TaskEntry.COLUMN_NAME, task.getName());

            int count = db.update(TaskEntry.TABLE_NAME, values, TaskEntry.COLUMN_ID + " = " + task.getId(), null);
            res = count > 0;
        }

        return res;
    }

    public boolean delete(Task task) {
        boolean res = false;

        //Can not delete the first task Limbo
        if (task != null && task.getId() > 1) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            int count = db.delete(TaskEntry.TABLE_NAME, TaskEntry.COLUMN_ID + " = " + task.getId(), null);
            res = count > 0;

            db.delete(ReportEntry.TABLE_NAME, ReportEntry.COLUMN_TASK_ID + " = " + task.getId(), null);
        }

        return res;
    }
}
