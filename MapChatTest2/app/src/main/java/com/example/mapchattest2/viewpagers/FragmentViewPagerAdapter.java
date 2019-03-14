package com.example.mapchattest2.viewpagers;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mapchattest2.fragments.GroupFragment;
import com.example.mapchattest2.fragments.MessageFragment;
import com.example.mapchattest2.fragments.MusicFragment;
import com.example.mapchattest2.fragments.ProfileFragment;

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 4;

    public FragmentViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? MessageFragment.newInstance("0", "messages")
                : position == 1 ? GroupFragment.newInstance("1", "group")
                : position == 2 ? MusicFragment.newInstance("2", "music")
                : position == 3 ? ProfileFragment.newInstance("3", "profile")
                : new Fragment();
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
