package com.example.navjava;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.navjava.Adapter.ViewPagerAdapter;

import java.util.Arrays;
import java.util.List;

public class TabBarExample extends Fragment {

    public static TabBarExample newInstance() {
        return (new TabBarExample());
    }

    MainActivity mapsActivity;

    private TabLayout tab;
    private ViewPager page;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab_bar_example, container, false);
        mapsActivity = (MainActivity) getActivity();
        ((MainActivity) getActivity()).setActionBarTitle("TabBar Example");

        tab = view.findViewById(R.id.tab);
        page = view.findViewById(R.id.viewpager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter((getChildFragmentManager()));

        /** set the fragents*/
        Fragment1 fragment1 = Fragment1.newInstance();//page1
        viewPagerAdapter.addFragment(fragment1,"Fragment1");

        Fragment2 fragment2 = Fragment2.newInstance();
        viewPagerAdapter.addFragment(fragment2,"Fragment2");

        Fragment3 fragment3 = Fragment3.newInstance();
        viewPagerAdapter.addFragment(fragment3,"Fragment3");

        /**static no changes*/
        page.setAdapter(viewPagerAdapter);
        tab.setupWithViewPager(page);



        return view;
    }
}
