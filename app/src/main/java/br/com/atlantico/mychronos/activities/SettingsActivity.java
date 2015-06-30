
package br.com.atlantico.mychronos.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.model.Constants;

/**
 * Settings activity
 * 
 * @author pereira_ygor
 * @since 6/3/2015
 */
public class SettingsActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener {

    private SharedPreferences mPrefs;

    private SharedPreferences.Editor mEditor;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.settings_rg_journey);
        RadioButton rbOp4 = (RadioButton)findViewById(R.id.settings_rb_op4);
        RadioButton rbOp6 = (RadioButton)findViewById(R.id.settings_rb_op6);
        RadioButton rbOp8 = (RadioButton)findViewById(R.id.settings_rb_op8);

        mPrefs = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        switch (mPrefs.getInt(Constants.SHARED_PREF_DAILY_JOURNEY, 8)) {
            case 4: {
                rbOp4.setChecked(true);
                break;
            }
            case 6: {
                rbOp6.setChecked(true);
                break;
            }
            default: {
                rbOp8.setChecked(true);
                break;
            }
        }

        radioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (mEditor == null) {
            mEditor = mPrefs.edit();
        }

        RadioButton rdb = (RadioButton) findViewById(checkedId);

        int id = Integer.valueOf(rdb.getTag().toString());

        mEditor.putInt(Constants.SHARED_PREF_DAILY_JOURNEY, id);

        mEditor.apply();

        Snackbar.make(findViewById(android.R.id.content),
                getString(R.string.msg_daily_journey, rdb.getText()), Snackbar.LENGTH_SHORT).show();

    }
}
