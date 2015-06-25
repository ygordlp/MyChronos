package br.com.atlantico.mychronos.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

import br.com.atlantico.mychronos.R;

/**
 * Created by pereira_ygor on 25/06/2015.
 */
public class TimePickerFragment extends DialogFragment {

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