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

    public TaskTimeCalc(Context context){
        reportDao = ReportDAO.getInstance(context);
    }

    @Override
    protected Long doInBackground(Wrapper... params) {
        Calendar now = Calendar.getInstance();

        wrapper = params[0];
        String date = TimeUtils.getSQLDate(now);
        ArrayList<Report> reports = reportDao.getAllFromTaskAndDate(wrapper.task.getId(), date);
        long totalTime = 0;
        for (Report r : reports) {
            if (r.getEndTime() > 0) {
                totalTime += r.getTotalTime();
            } else {
                if (r.getStartTime() > 0) {
                    totalTime += now.getTimeInMillis() - r.getStartTime();
                }
            }
        }

        return totalTime;
    }

    @Override
    protected void onPostExecute(Long time) {
        wrapper.textView.setText(TimeUtils.TimeDHMtoString(time));
    }
}