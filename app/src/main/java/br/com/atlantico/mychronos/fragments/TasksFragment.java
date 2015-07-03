package br.com.atlantico.mychronos.fragments;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.adapters.TaskListAdapter;
import br.com.atlantico.mychronos.db.DataSetChangedBroadcastReceiver;
import br.com.atlantico.mychronos.db.TaskDAO;
import br.com.atlantico.mychronos.dialogs.TextInputDialog;
import br.com.atlantico.mychronos.model.Task;

public class TasksFragment extends Fragment implements View.OnClickListener, TextInputDialog.TextInputListner, AdapterView.OnItemLongClickListener {

    public static final String TAG = "TasksFragment";

    private static final int DELAY = 10000;

    private TaskListAdapter adapter;

    private TaskDAO dao;

    private Handler handler = new Handler();

    private DataSetChangedBroadcastReceiver receiver;

    private Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            if(adapter!= null){
                Log.d(TAG, "Runnable updateActiveTask.");
                adapter.notifyDataSetChanged();
            }
            handler.postDelayed(this, DELAY);
        }
    };

    public TasksFragment() {
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

        receiver = new DataSetChangedBroadcastReceiver(adapter);

        return view;
    }

    @Override
    public void onPause() {
        Log.d(TAG, "TasksFragment onPause");

        getActivity().unregisterReceiver(receiver);
        handler.removeCallbacks(updateTask);
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "TasksFragment onResume");

        getActivity().registerReceiver(receiver, new IntentFilter(DataSetChangedBroadcastReceiver.DATA_CHANGE));
        adapter.notifyDataSetChanged();
        handler.postDelayed(updateTask, DELAY);
        super.onResume();
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
