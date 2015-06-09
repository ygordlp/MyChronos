package br.com.atlantico.mychronos.adapters;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.fragments.CheckinFragment;
import br.com.atlantico.mychronos.fragments.ReportFragment;
import br.com.atlantico.mychronos.fragments.TasksFragment;

public class ChronosPageAdapter extends FragmentPagerAdapter {

    private Fragment[] pages = new Fragment[]{
            new CheckinFragment(),
            new TasksFragment(),
            new ReportFragment()
    };

    private int[] titlesId = new int[]{
            R.string.lbl_checkin,
            R.string.lbl_tasks,
            R.string.lbl_report
    };

    private Resources resources;

    public ChronosPageAdapter(FragmentManager fm, Resources res) {
        super(fm);
        resources = res;
    }

    @Override
    public Fragment getItem(int i) {
        return pages[i];
    }

    @Override
    public int getCount() {
        return pages.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = resources.getString(titlesId[position]);
        return title;
    }
}
