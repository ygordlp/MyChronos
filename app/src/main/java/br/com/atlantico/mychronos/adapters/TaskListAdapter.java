package br.com.atlantico.mychronos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.db.TaskDAO;
import br.com.atlantico.mychronos.model.Task;

/**
 * Created by pereira_ygor on 23/06/2015.
 */
public class TaskListAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<Task> tasks = new ArrayList<Task>();
    private Context context;
    private TaskDAO dao;
    private LayoutInflater inflater;

    public TaskListAdapter(Context context) {
        this.context = context;
        this.dao = TaskDAO.getInstance(context);
        this.tasks = this.dao.getAll();
        this.inflater = LayoutInflater.from(context);
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

        return convertView;
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
            Toast.makeText(context, task.getName() + " selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyDataSetChanged() {
        this.tasks = this.dao.getAll();
        super.notifyDataSetChanged();
    }
}
