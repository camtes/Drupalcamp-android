package ccamposfuentes.es.drupalcamp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.sql.Date;

import ccamposfuentes.es.drupalcamp.PageFragment;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 28/01/16
 * Project: DrupalCamp
 */

public class PageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Faraday", "Cine 3", "Einstein", "Gutenberg" };
    private Context context;
    private String day;

    public PageAdapter(FragmentManager fm, Context context, String day) {
        super(fm);
        this.context = context;
        this.day = day;
    }


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
        }

        return PageFragment.newInstance(position + 1, day);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
