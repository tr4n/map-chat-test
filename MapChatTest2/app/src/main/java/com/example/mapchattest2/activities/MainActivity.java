package com.example.mapchattest2.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.example.mapchattest2.R;
import com.example.mapchattest2.fragments.GroupFragment;
import com.example.mapchattest2.fragments.MessageFragment;
import com.example.mapchattest2.fragments.MusicFragment;
import com.example.mapchattest2.fragments.ProfileFragment;
import com.example.mapchattest2.viewpagers.FragmentViewPagerAdapter;
import com.example.mapchattest2.viewpagers.NonSwipeableViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MessageFragment.OnFragmentInteractionListener, GroupFragment.OnFragmentInteractionListener, MusicFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener {


    @BindView(R.id.ah_bottom_navigation)
    AHBottomNavigation ahBottomNavigation;
    @BindView(R.id.non_swipeable_view_pager)
    NonSwipeableViewPager nonSwipeableViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialization();
        setupUI();
    }

    private void initialization() {
        initViewPager();
        initAHBottomNavigation();

    }

    private void initViewPager() {
        FragmentViewPagerAdapter fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
        nonSwipeableViewPager.setAdapter(fragmentViewPagerAdapter);
    }

    private void initAHBottomNavigation() {
//        AHBottomNavigationItem ahBottomNavigationItem1 = new AHBottomNavigationItem(R.string.bottom_navigation_tab_1, R.drawable.ic_chat_24dp, 0);
//        AHBottomNavigationItem ahBottomNavigationItem2 = new AHBottomNavigationItem(R.string.bottom_navigation_tab_2, R.drawable.ic_group_24dp, 0);
//        AHBottomNavigationItem ahBottomNavigationItem3 = new AHBottomNavigationItem(R.string.bottom_navigation_tab_3, R.drawable.ic_music_note_24dp, 0);

        AHBottomNavigationAdapter ahBottomNavigationAdapter = new AHBottomNavigationAdapter(this, R.menu.menu_tab_bar);
        ahBottomNavigationAdapter.setupWithBottomNavigation(ahBottomNavigation);
        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        //  ahBottomNavigation.setDefaultBackgroundResource(R.color.colorPrimary);
        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                nonSwipeableViewPager.setCurrentItem(position);
                return true;
            }
        });
        // Set background color
        // ahBottomNavigation.setDefaultBackgroundColor(Color.parseColor("#F0F0"));


    }

    private void setupUI() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
