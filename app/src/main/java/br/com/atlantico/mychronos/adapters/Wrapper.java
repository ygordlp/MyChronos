package br.com.atlantico.mychronos.adapters;

import android.widget.TextView;

import br.com.atlantico.mychronos.model.Task;

/**
 * Created by Ygor Duarte on 06/07/2015.
 */
public class Wrapper {
    Task task;
    int position;
    TextView textView;

    public Wrapper(Task t, int pos, TextView txt) {
        task = t;
        position = pos;
        textView = txt;
    }
}