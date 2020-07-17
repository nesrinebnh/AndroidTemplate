package com.example.navjava;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navjava.Adapter.ViewPagerAdapter;

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
        SimplaCardView fragment1 = SimplaCardView.newInstance();//page1
        viewPagerAdapter.addFragment(fragment1,"SimplaCardView");

        ListOfCardView fragment2 = ListOfCardView.newInstance();
        viewPagerAdapter.addFragment(fragment2,"ListOfCardView");

        Fragment3 fragment3 = Fragment3.newInstance();
        viewPagerAdapter.addFragment(fragment3,"Fragment3");

        /**static no changes*/
        page.setAdapter(viewPagerAdapter);
        tab.setupWithViewPager(page);



        return view;
    }
}
