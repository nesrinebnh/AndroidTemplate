package com.example.navjava;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navjava.Adapter.RecycleAdapter;
import com.example.navjava.Model.Appartment;

import java.util.HashMap;

public class ListOfCardView extends Fragment {

    public static ListOfCardView newInstance() {
        return (new ListOfCardView());
    }

    MainActivity mapsActivity;

    private HashMap<String, Appartment> appartmentHashMap = new HashMap<>();;

    private LinearLayoutManager linearLayoutManager;

    private RecyclerView recyclerView;
    private RecycleAdapter recycleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment2, container, false);
        mapsActivity = (MainActivity) getActivity();
        ((MainActivity) getActivity()).setActionBarTitle("ListOfCardView");

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.rec);

        appartmentHashMap = new HashMap<>();

        appartmentHashMap.put("val1", new Appartment("name1"));
        appartmentHashMap.put("val2",new Appartment("name2"));
        appartmentHashMap.put("val3",new Appartment("name3"));
        recycleAdapter = new RecycleAdapter(getContext(), appartmentHashMap, ListOfCardView.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new ListOfCardView.GridSpacingItemDecoration(1,dpToPx(10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        prepareData();


        return view;
    }

    private void prepareData(){
        /*appartmentHashMap.put("val1", new Appartment("name1"));
        appartmentHashMap.put("val2",new Appartment("name2"));
        appartmentHashMap.put("val3",new Appartment("name3"));*/
        recycleAdapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration{
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int span, int space, boolean includeEdge){
            this.spanCount = span;
            this.spacing = space;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect out, View view, RecyclerView parent, RecyclerView.State state){
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if(includeEdge){
                out.left = spacing - column*spacing/spanCount;
                out.right = (column+1) * spacing / spanCount;

                if(position < spanCount) out.top = spacing;
                out.bottom = spacing;
            }else{
                out.left = column * spacing/spanCount;
                out.right = spacing - (column + 1) * spacing / spanCount;
                if(position>=spanCount){
                    out.top = spacing;
                }
            }
        }
    }

    private int dpToPx(int dp){
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,r.getDisplayMetrics()));

    }


}
