package com.example.hp.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by hp on 12/15/2017.
 */

public class MemoryPagerAdapter extends FragmentPagerAdapter {


    public MemoryPagerAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                MemoryFragment requestsFragment = new MemoryFragment();
                return requestsFragment;

            case 1:
                ReviewReceiveFragmennt chatsFragment= new ReviewReceiveFragmennt();
                return  chatsFragment;

            case 2:
               MenuFragment friendsFragment = new MenuFragment();
                return friendsFragment;

            default:
                return  null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){

        switch (position) {
            case 0:
                return "MEMORIES";

            case 1:
                return "REVIEWS";

            case 2:
                return "MENU";

            default:
                return null;
        }

    }

}
