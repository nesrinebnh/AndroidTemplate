package com.example.navjava.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navjava.Fragment2;
import com.example.navjava.Model.Appartment;
import com.example.navjava.R;
import com.google.android.gms.common.api.Api;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {


    private Context context;
    private List<Appartment> appartmentList;
    private int j;

    private Fragment2 fragment2;

    public RecycleAdapter(Context context, HashMap<String, Appartment> map, Fragment2 fragment2
                         ){
        this.context = context;
        Log.i("TAG","map size :: "+String.valueOf(map.size()) );
        this.appartmentList = new ArrayList<Appartment>(map.values());


        this.fragment2 = fragment2;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount(){return appartmentList.size();}

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt;

        View layout;
        public MyViewHolder(View view){
        super(view);
        layout = view;
        Log.i("TAG","ok");
        txt = (TextView) view.findViewById(R.id.txt);

    }
}

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){
        j = position;
        Appartment fragment2 = appartmentList.get(position);
        holder.txt.setText(fragment2.getName());
        Log.i("TAG", "name::  "+fragment2.getName());


    }
}
