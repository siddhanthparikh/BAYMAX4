package com.example.kavit.pelicula1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by kavit on 30-03-2018.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new firstFragment();
        } else if (position == 1){
            return new secondFragment();
        } else {
            return new thirdFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}