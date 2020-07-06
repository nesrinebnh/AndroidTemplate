package com.example.navjava.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentListTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i){
        return fragmentList.get(i);
    }

    @Override
    public int getCount(){
        return fragmentListTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return fragmentListTitles.get(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object){
        super.setPrimaryItem(container, position, object);
        fragmentList.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        fragmentListTitles.add(title);
    }


}
