package br.com.atlantico.mychronos.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Timer;
import java.util.TimerTask;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.adapters.TaskListAdapter;
import br.com.atlantico.mychronos.db.TaskDAO;
import br.com.atlantico.mychronos.dialogs.TextInputDialog;
import br.com.atlantico.mychronos.model.Task;

public class TasksFragment extends Fragment implements View.OnClickListener, TextInputDialog.TextInputListner, AdapterView.OnItemLongClickListener {

    public static final String TAG = "TasksFragment";

    private TaskListAdapter adapter;

    private TaskDAO dao;

    private Timer timer = new Timer();

    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (adapter != null) {
                        adapter.updateActiveTask();
                    }
                }
            });
        }
    };

    public TasksFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        dao = TaskDAO.getInstance(getActivity());

        adapter = new TaskListAdapter(getActivity(), this);

        ListView list = (ListView) view.findViewById(R.id.taskList);
        list.setAdapter(adapter);
        list.setOnItemLongClickListener(this);

        view.findViewById(R.id.fabAddTask).setOnClickListener(this);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    public void onStart() {
        super.onStart();
        timer.schedule(timerTask, 1000, 30000);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fabAddTask:
                TextInputDialog dialog = new TextInputDialog();
                dialog.setListner(this);
                dialog.show(getFragmentManager(), TAG);
                break;
        }
    }

    @Override
    public void onTextInput(String text, Object tagItem) {
        if (tagItem == null) {
            //New task
            Task task = dao.getByName(text);
            //Check if task already exists
            if (task == null) {
                task = new Task(text);
                dao.add(task);
            } else {
                Snackbar.make(getView(), R.string.msg_task_already_exist, Snackbar.LENGTH_SHORT).show();
            }
        } else {
            //Edit task
            Task task = (Task) tagItem;
            task.setName(text);
            dao.update(task);
        }

        adapter.updateData();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Task task = adapter.getItem(position);
        if (task != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setListner(this);
            dialog.setTagItem(task);
            dialog.setText(task.getName());
            dialog.show(getFragmentManager(), TAG);
        }
        return true;
    }
}
