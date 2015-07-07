package br.com.atlantico.mychronos.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.adapters.ReporListAdapter;
import br.com.atlantico.mychronos.db.ReportDAO;
import br.com.atlantico.mychronos.model.Report;
import br.com.atlantico.mychronos.utils.TimeUtils;

public class ReportFragment extends Fragment implements View.OnClickListener {

    private Calendar curDate = Calendar.getInstance();

    private Button btnNextDate;

    private TextView txtWorkedHours, txtDate;

    private ReporListAdapter adapter;

    private ReportDAO reportDAO;

    public ReportFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);


        view.findViewById(R.id.btnPrevDate).setOnClickListener(this);
        btnNextDate = (Button) view.findViewById(R.id.btnNextDate);
        btnNextDate.setOnClickListener(this);

        adapter = new ReporListAdapter(getActivity());

        ListView list = (ListView) view.findViewById(R.id.taskList);
        list.setAdapter(adapter);

        txtWorkedHours = (TextView) view.findViewById(R.id.txtWorkedTime);
        txtDate = (TextView) view.findViewById(R.id.txtDate);

        reportDAO = ReportDAO.getInstance(getActivity());

        updateUI();

        return view;
    }

    public void updateUI() {
        adapter.setReporteDate(curDate);

        txtDate.setText(TimeUtils.getShortDate(curDate));

        ArrayList<Report> reports = reportDAO.getAllFromStartedDate(TimeUtils.getSQLDate(curDate));

        long workedTime = TimeUtils.getReportsTotalTime(reports);

        if (workedTime > 0) {
            txtWorkedHours.setText(TimeUtils.TimeDHMtoString(workedTime));
        } else {
            txtWorkedHours.setText(R.string.lbl_time_not_set);
        }

        updateNextDateButton();
    }

    private void updateNextDateButton() {
        Calendar now = Calendar.getInstance();
        btnNextDate.setEnabled(!TimeUtils.isSameDay(curDate, now));
    }

    private void stepDate(boolean next) {
        if (next) {
            curDate.add(Calendar.DAY_OF_MONTH, 1);
        } else {
            curDate.add(Calendar.DAY_OF_MONTH, -1);
        }

        updateUI();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btnPrevDate:
                stepDate(false);
                break;
            case R.id.btnNextDate:
                stepDate(true);
                break;
        }

        updateUI();
    }

    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
