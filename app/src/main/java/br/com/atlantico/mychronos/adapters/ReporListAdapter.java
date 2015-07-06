package br.com.atlantico.mychronos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.db.ReportDAO;
import br.com.atlantico.mychronos.db.TaskDAO;
import br.com.atlantico.mychronos.model.Task;
import br.com.atlantico.mychronos.utils.TimeUtils;

/**
 * Created by pereira_ygor on 23/06/2015.
 */
public class ReporListAdapter extends BaseAdapter {

    public static final String TAG = "TaskListAdapter";

    private ArrayList<Task> tasks = new ArrayList<Task>();
    private Context context;
    final private TaskDAO taskDao;
    final private ReportDAO reportDao;
    private LayoutInflater inflater;
    private Calendar curDate = Calendar.getInstance();

    public ReporListAdapter(Context context) {
        this.context = context;
        this.taskDao = TaskDAO.getInstance(context);
        this.tasks = this.taskDao.getAll();
        this.inflater = LayoutInflater.from(context);
        this.reportDao = ReportDAO.getInstance(context);

        updateTasksWithReports();
    }

    public void setReporteDate(Calendar date){
        curDate = date;
        notifyDataSetChanged();
    }

    private void updateTasksWithReports(){
        this.tasks = this.taskDao.getAllTaskWithReport(TimeUtils.getSQLDate(curDate));
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
            convertView = inflater.inflate(R.layout.report_item, parent, false);
        }

        Task task = getItem(position);

        if (task == null) {
            return convertView;
        }

        TextView taskName = (TextView) convertView.findViewById(R.id.txtTaskName);
        taskName.setText(task.getName());

        TextView tvTime = (TextView) convertView.findViewById(R.id.txtTaskTime);

        Wrapper w = new Wrapper(task, position, tvTime);
        TaskTimeCalc ttc = new TaskTimeCalc(context);
        ttc.execute(w);

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        updateTasksWithReports();
        super.notifyDataSetChanged();
    }
}
