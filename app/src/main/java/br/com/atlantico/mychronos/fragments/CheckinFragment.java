package br.com.atlantico.mychronos.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.model.Timestamp;
import br.com.atlantico.mychronos.utils.TimeUtils;

public class CheckinFragment extends Fragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    public static final String TAG = "CheckinFragment";

    private ArrayList<Timestamp> timestamps = new ArrayList<Timestamp>();

    private ArrayList<TextView> textViews = new ArrayList<TextView>();

    private TextView workedHours, timeToLeave;

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

        textViews.add((TextView) view.findViewById(R.id.txtFirstIn));
        textViews.add((TextView) view.findViewById(R.id.txtFirstOut));
        textViews.add((TextView) view.findViewById(R.id.txtSecondIn));
        textViews.add((TextView) view.findViewById(R.id.txtSecondOut));

        workedHours = (TextView) view.findViewById(R.id.txtWorkedTime);
        timeToLeave = (TextView) view.findViewById(R.id.txtTimToLeave);

        return view;
    }

    public void updateUI() {
        int count = timestamps.size();
        for (int i = 0; i < count; i++) {
            textViews.get(i).setText(timestamps.get(i).toString());
        }

        long workedTime = TimeUtils.calcWorkedTime(timestamps);

        if(workedTime > 0){
            workedHours.setText(TimeUtils.TimeDHMtoString(workedTime));
        }

        Timestamp outTime = TimeUtils.calcTimeToLeave(timestamps);

        if(outTime != null){
            timeToLeave.setText(outTime.toString());
        }
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Timestamp ts = new Timestamp(hourOfDay, minute);
        int count = timestamps.size();
        if(count > 0){
            Timestamp last = timestamps.get(count - 1);
            if(ts.getTime() > last.getTime()){
                timestamps.add(ts);
            } else {
                Snackbar.make(getView(), R.string.lbl_greater_time, Snackbar.LENGTH_SHORT).show();
            }
        } else {
            timestamps.add(ts);
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

            return new TimePickerDialog(getActivity(), listener, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }
    }
}
