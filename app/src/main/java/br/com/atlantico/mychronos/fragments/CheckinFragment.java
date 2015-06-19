package br.com.atlantico.mychronos.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.db.TimestampDAO;
import br.com.atlantico.mychronos.model.Timestamp;
import br.com.atlantico.mychronos.utils.TimeUtils;

public class CheckinFragment extends Fragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    public static final String TAG = "CheckinFragment";

    private ArrayList<Timestamp> timestamps;

    private ArrayList<TextView> textViews;

    private TextView txtWorkedHours, txtTimeToLeave, txtDate;

    private TimestampDAO tsDao;

    private Calendar curDate = Calendar.getInstance();

    private Button btnNextDate;

    public CheckinFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkin, container, false);

        view.findViewById(R.id.btnCheck).setOnClickListener(this);
        view.findViewById(R.id.btnPrevDate).setOnClickListener(this);
        btnNextDate = (Button) view.findViewById(R.id.btnNextDate);
        btnNextDate.setOnClickListener(this);

        textViews = new ArrayList<TextView>();

        textViews.add((TextView) view.findViewById(R.id.txtFirstIn));
        textViews.add((TextView) view.findViewById(R.id.txtFirstOut));
        textViews.add((TextView) view.findViewById(R.id.txtSecondIn));
        textViews.add((TextView) view.findViewById(R.id.txtSecondOut));

        txtWorkedHours = (TextView) view.findViewById(R.id.txtWorkedTime);
        txtTimeToLeave = (TextView) view.findViewById(R.id.txtTimToLeave);
        txtDate = (TextView) view.findViewById(R.id.txtDate);

        tsDao = TimestampDAO.getInstance(getActivity());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        txtDate.setText(TimeUtils.getShortDate(curDate));

        timestamps = tsDao.getAllFromDate(TimeUtils.getSQLDate(curDate));

        int count = timestamps.size();
        for (int i = 0; i < 4; i++) {
            if(i < count) {
                textViews.get(i).setText(timestamps.get(i).toString());
            } else {
                textViews.get(i).setText(R.string.lbl_time_not_set);
            }
        }

        long workedTime = TimeUtils.calcWorkedTime(timestamps);

        if (workedTime > 0) {
            txtWorkedHours.setText(TimeUtils.TimeDHMtoString(workedTime));
        } else {
            txtWorkedHours.setText(R.string.lbl_time_not_set);
        }

        Timestamp outTime = TimeUtils.calcTimeToLeave(timestamps);

        if (outTime != null) {
            txtTimeToLeave.setText(outTime.toString());
        } else {
            txtTimeToLeave.setText(R.string.lbl_time_not_set);
        }

        updateNextDateButton();
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Timestamp ts = new Timestamp(curDate, hourOfDay, minute);
        int count = timestamps.size();
        if (count > 0) {
            Timestamp last = timestamps.get(count - 1);
            if (ts.getTime() > last.getTime()) {
                addTimestamp(ts);
            } else {
                Snackbar.make(getView(), R.string.lbl_greater_time, Snackbar.LENGTH_SHORT).show();
            }
        } else {
            addTimestamp(ts);
        }

        updateUI();
    }

    private void updateNextDateButton(){
        Calendar now = Calendar.getInstance();
        btnNextDate.setEnabled(!TimeUtils.isSameDay(curDate, now));
    }

    private void addTimestamp(Timestamp ts) {
        // If added to DB with success, add it to UI.
        if (tsDao.add(ts)) {
            timestamps.add(ts);
        }
    }

    private void stepDate(boolean next){
        if(next){
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
            case R.id.btnCheck:
                if (timestamps.size() < 4) {
                    TimePickerFragment dialog = new TimePickerFragment();
                    dialog.setListener(this);
                    dialog.show(getFragmentManager(), TAG);
                } else {
                    Snackbar.make(getView(), R.string.lbl_all_time_set, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnPrevDate:
                stepDate(false);
                break;
            case R.id.btnNextDate:
                stepDate(true);
                break;
        }
    }

    public static class TimePickerFragment extends DialogFragment {

        private TimePickerDialog.OnTimeSetListener listener;

        public void setListener(TimePickerDialog.OnTimeSetListener listener) {
            this.listener = listener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog dialog = new TimePickerDialog(getActivity(), listener, hour, minute, true);

            dialog.setCancelable(true);
            dialog.setButton(TimePickerDialog.BUTTON_POSITIVE, getString(R.string.lbl_ok), dialog);
            dialog.setButton(TimePickerDialog.BUTTON_NEGATIVE, getString(R.string.lbl_cancel), dialog);
            return dialog;
        }
    }
}
