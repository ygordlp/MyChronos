package br.com.atlantico.mychronos.db;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.BaseAdapter;

/**
 * Created by Ygor Duarte on 03/07/2015.
 */
public class DataSetChangedBroadcastReceiver extends BroadcastReceiver {

    public static final String DATA_CHANGE = "br.com.atlantico.mychronos.db.DATA_CHANGE";

    private BaseAdapter adapter;

    public DataSetChangedBroadcastReceiver(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }
}
