package br.com.atlantico.mychronos.adapters;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.atlantico.mychronos.db.ReportDAO;
import br.com.atlantico.mychronos.model.Report;
import br.com.atlantico.mychronos.utils.TimeUtils;

/**
 * Created by Ygor Duarte on 06/07/2015.
 */
public class TaskTimeCalc extends AsyncTask<Wrapper, Void, Long> {

    private Wrapper wrapper;

    private ReportDAO reportDao;

    private Calendar date = Calendar.getInstance();

    public TaskTimeCalc(Context context, Calendar date){
        reportDao = ReportDAO.getInstance(context);
        this.date = date;
    }

    @Override
    protected Long doInBackground(Wrapper... params) {
        wrapper = params[0];
        String sqlDate = TimeUtils.getSQLDate(date);

        ArrayList<Report> reports = reportDao.getAllFromTaskAndDate(wrapper.task.getId(), sqlDate);
        long totalTime = TimeUtils.getReportsTotalTime(reports);

        return totalTime;
    }

    @Override
    protected void onPostExecute(Long time) {
        wrapper.textView.setText(TimeUtils.TimeDHMtoString(time));
    }
}