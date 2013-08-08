package uk.co.jamiemagee.chainefm.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import uk.co.jamiemagee.chainefm.R;
import uk.co.jamiemagee.chainefm.fragment.AboutFragment;
import uk.co.jamiemagee.chainefm.fragment.ContactFragment;
import uk.co.jamiemagee.chainefm.fragment.DummySectionFragment;
import uk.co.jamiemagee.chainefm.fragment.RadioFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                return new RadioFragment();
            case 1:
                return new ContactFragment();
            case 2:
                return new AboutFragment();
            default:
                return new DummySectionFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mContext.getResources().getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return mContext.getResources().getString(R.string.title_section2).toUpperCase(l);
            case 2:
                return mContext.getResources().getString(R.string.title_section3).toUpperCase(l);
        }
        return null;
    }
}