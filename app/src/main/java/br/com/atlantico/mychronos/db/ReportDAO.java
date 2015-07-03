package br.com.atlantico.mychronos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.atlantico.mychronos.model.Report;

/**
 * Created by pereira_ygor on 18/06/2015.
 */
public class ReportDAO {

    private static ReportDAO instance;

    private Context context;
    private ChronosDBHelper mDbHelper;

    private ReportDAO(Context ctx) {
        this.context = ctx;
        mDbHelper = new ChronosDBHelper(ctx);
    }

    public static ReportDAO getInstance(Context ctx) {
        if (instance == null) {
            instance = new ReportDAO(ctx);
        }
        return instance;
    }

    public long add(Report rep) {
        long res = 0;

        if (rep != null) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ReportEntry.COLUMN_TASK_ID, rep.getTask_id());
            values.put(ReportEntry.COLUMN_START_TIME, rep.getStartTime());
            values.put(ReportEntry.COLUMN_END_TIME, rep.getEndTime());

            res = db.insert(ReportEntry.TABLE_NAME, null, values);
        }

        return res;
    }

    private Report getReport(String where, boolean last){
        Report res = null;

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor c = db.query(
                ReportEntry.TABLE_NAME,
                ReportEntry.PROJECTION,
                where,
                null, null, null, null
        );

        if (last? c.moveToLast() : c.moveToFirst()) {
            long id = c.getLong(c.getColumnIndex(ReportEntry.COLUMN_ID));
            long task_id = c.getLong(c.getColumnIndex(ReportEntry.COLUMN_TASK_ID));
            long starTime =  c.getLong(c.getColumnIndex(ReportEntry.COLUMN_START_TIME));
            long endTime =  c.getLong(c.getColumnIndex(ReportEntry.COLUMN_END_TIME));
            res = new Report(id, task_id, starTime, endTime);
        }

        return res;
    }

    public Report getById(int id) {
        return getReport(ReportEntry.COLUMN_ID + " = " + id, false);
    }

    private ArrayList<Report> getAll(String where) {
        ArrayList<Report> reports = new ArrayList<Report>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor c = db.query(
                ReportEntry.TABLE_NAME,
                ReportEntry.PROJECTION,
                where, null, null, null, null
        );

        if (c.moveToFirst()) {
            long id = c.getLong(c.getColumnIndex(ReportEntry.COLUMN_ID));
            long task_id = c.getLong(c.getColumnIndex(ReportEntry.COLUMN_TASK_ID));
            long starTime =  c.getLong(c.getColumnIndex(ReportEntry.COLUMN_START_TIME));
            long endTime =  c.getLong(c.getColumnIndex(ReportEntry.COLUMN_END_TIME));
            Report rep = new Report(id, task_id, starTime, endTime);
            reports.add(rep);

            while (c.moveToNext()) {
                id = c.getLong(c.getColumnIndex(ReportEntry.COLUMN_ID));
                task_id = c.getLong(c.getColumnIndex(ReportEntry.COLUMN_TASK_ID));
                starTime =  c.getLong(c.getColumnIndex(ReportEntry.COLUMN_START_TIME));
                endTime =  c.getLong(c.getColumnIndex(ReportEntry.COLUMN_END_TIME));
                rep = new Report(id, task_id, starTime, endTime);
                reports.add(rep);
            }
        }

        return reports;
    }

    public ArrayList<Report> getAll() {
        return getAll(null);
    }

    /**
     * Get all Reports from the day in the fromat YYYY-MM-DD
     *
     * @param sqlDateFormat
     * @return
     */
    public ArrayList<Report> getAllFromStartedDate(String sqlDateFormat) {
        String where = "STRFTIME('%Y-%m-%d', datetime("+ReportEntry.COLUMN_START_TIME+"/1000, 'unixepoch')) LIKE '" + sqlDateFormat + "'";
        return getAll(where);
    }

    /**
     * Get all Reports from the day in the fromat YYYY-MM-DD for a specific Task
     *
     * @param sqlDateFormat
     * @return
     */
    public ArrayList<Report> getAllFromTaskAndDate(long task_id, String sqlDateFormat) {
        String where = "STRFTIME('%Y-%m-%d', datetime("+ReportEntry.COLUMN_START_TIME+"/1000, 'unixepoch')) LIKE '" + sqlDateFormat + "'"
                + " AND " + ReportEntry.COLUMN_TASK_ID + " = " + task_id;
        return getAll(where);
    }

    /**
     * Get the last report of the Task.
     *
     * @param task_id Task id.
     * @return Last report of the task.
     */
    public Report getLastReportFromTask(long task_id){
        return getReport(ReportEntry.COLUMN_TASK_ID + " = " + task_id, true);
    }

    /**
     * Get the last report recorded.
     *
     * @return Last report.
     */
    public Report getLastReport(){
        return getReport(ReportEntry.COLUMN_END_TIME + " = 0", true);
    }

    /**
     * Get all Reports from the task (id).
     *
     * @param task_id
     * @return
     */
    public ArrayList<Report> getAllFromTask(long task_id) {
        String where =  ReportEntry.COLUMN_TASK_ID + " = " + task_id;
        return getAll(where);
    }

    public boolean update(Report rep) {
        boolean res = false;

        if (rep != null && rep.getId() > 0) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ReportEntry.COLUMN_TASK_ID, rep.getTask_id());
            values.put(ReportEntry.COLUMN_START_TIME, rep.getStartTime());
            values.put(ReportEntry.COLUMN_END_TIME, rep.getEndTime());

            int count = db.update(ReportEntry.TABLE_NAME, values, ReportEntry.COLUMN_ID + " = " + rep.getId(), null);
            res = count > 0;
        }

        return res;
    }

    public boolean delete(Report rep) {
        boolean res = false;

        if (rep != null && rep.getId() > 0) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            int count = db.delete(ReportEntry.TABLE_NAME, ReportEntry.COLUMN_ID + " = " + rep.getId(), null);
            res = count > 0;
        }

        return res;
    }
}
