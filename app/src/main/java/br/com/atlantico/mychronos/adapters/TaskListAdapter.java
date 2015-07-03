package br.com.atlantico.mychronos.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.db.ReportDAO;
import br.com.atlantico.mychronos.db.TaskDAO;
import br.com.atlantico.mychronos.fragments.TasksFragment;
import br.com.atlantico.mychronos.model.Report;
import br.com.atlantico.mychronos.model.Task;
import br.com.atlantico.mychronos.utils.TimeUtils;

/**
 * Created by pereira_ygor on 23/06/2015.
 */
public class TaskListAdapter extends BaseAdapter implements View.OnClickListener {

    public static final String TAG = "TaskListAdapter";

    private ArrayList<Task> tasks = new ArrayList<Task>();
    private Context context;
    final private TaskDAO taskDao;
    final private ReportDAO reportDao;
    private LayoutInflater inflater;
    private TasksFragment tasksFragment;
    private Report activeReport;

    public TaskListAdapter(Context context, TasksFragment tasksFragment) {
        this.context = context;
        this.taskDao = TaskDAO.getInstance(context);
        this.tasks = this.taskDao.getAll();
        this.inflater = LayoutInflater.from(context);
        this.reportDao = ReportDAO.getInstance(context);
        this.tasksFragment = tasksFragment;

        activeReport = reportDao.getLastReport();
    }

    @Override
    public int getCount() {
        if (tasks != null) {
            return tasks.size();
        }
        return 0;
    }

    @Override
    public Task getItem(int position) {
        if (tasks != null) {
            return tasks.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (tasks != null) {
            Task task = tasks.get(position);
            if (task != null) {
                return task.getId();
            }
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.task_item, parent, false);
        }

        Task task = getItem(position);

        if (task == null) {
            return convertView;
        }

        TextView taskName = (TextView) convertView.findViewById(R.id.txtTaskName);
        taskName.setText(task.getName());

        ImageView btnPlayPause = (ImageView) convertView.findViewById(R.id.btnPlayPause);
        btnPlayPause.setTag(task);
        btnPlayPause.setOnClickListener(this);

        TextView tvTime = (TextView) convertView.findViewById(R.id.txtTaskTime);

        long activeTaskId = (activeReport == null) ? 0 : activeReport.getTask_id();

        if (task.getId() == activeTaskId) {
            btnPlayPause.setBackgroundResource(R.drawable.pause);
        } else {
            btnPlayPause.setBackgroundResource(R.drawable.play);
        }

        Wrapper w = new Wrapper(task, position, tvTime);
        TaskTimeCalc ttc = new TaskTimeCalc();
        ttc.execute(w);

        Log.d(TAG, "TaskListAdapter: task id = " + activeTaskId);

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        activeReport = reportDao.getLastReport();
        Log.d(TAG, "notifyDataSetChanged: Active task id = " + activeReport.getTask_id());
        super.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Task task;
        try {
            task = (Task) v.getTag();
        } catch (ClassCastException e) {
            task = null;
        }

        if (task != null) {
            Report last = reportDao.getLastReport();
            if (last != null) {
                long now = Calendar.getInstance().getTimeInMillis();
                if (last.getEndTime() == 0) {
                    last.setEndTime(now);
                    reportDao.update(last);
                }

                Report report = new Report(task.getId(), now);
                reportDao.add(report);
            } else {
                Snackbar.make(tasksFragment.getView(), R.string.msg_start_day, Snackbar.LENGTH_SHORT).show();
            }
        }

        notifyDataSetChanged();
    }

    public void updateData() {
        this.tasks = this.taskDao.getAll();
        notifyDataSetChanged();
    }

    private class Wrapper {
        Task task;
        int position;
        TextView textView;

        public Wrapper(Task t, int pos, TextView txt) {
            task = t;
            position = pos;
            textView = txt;
        }
    }

    private class TaskTimeCalc extends AsyncTask<Wrapper, Void, Long> {

        private Wrapper wrapper;

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
}
