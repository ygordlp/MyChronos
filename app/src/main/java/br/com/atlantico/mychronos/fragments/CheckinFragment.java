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

import java.util.Calendar;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.model.Timestamp;
import br.com.atlantico.mychronos.utils.TimeUtils;

public class CheckinFragment extends Fragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    public static final String TAG = "CheckinFragment";

    private Timestamp[] stamps = new Timestamp[4];

    private int current = 0;

    private TextView[] textViews = new TextView[4];

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

        textViews[0] = (TextView) view.findViewById(R.id.txtFirstIn);
        textViews[1] = (TextView) view.findViewById(R.id.txtFirstOut);
        textViews[2] = (TextView) view.findViewById(R.id.txtSecondIn);
        textViews[3] = (TextView) view.findViewById(R.id.txtSecondOut);

        workedHours = (TextView) view.findViewById(R.id.txtWorkedTime);
        timeToLeave = (TextView) view.findViewById(R.id.txtTimToLeave);

        return view;
    }

    public void updateUI() {
        for (int i = 0; i < current; i++) {
            textViews[i].setText(stamps[i].toString());
        }

        if(current > 1){
            long time = stamps[1].getTime() - stamps[0].getTime();

            if(current > 4){
                time += stamps[3].getTime() - stamps[2].getTime();
            }

            workedHours.setText(TimeUtils.TimeToString(time));
        }
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Timestamp ts = new Timestamp(hourOfDay, minute);
        stamps[current] = ts;
        current++;

        updateUI();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btnCheck:
                if (current < 4) {
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
