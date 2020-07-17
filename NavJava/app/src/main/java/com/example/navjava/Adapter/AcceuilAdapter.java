package com.example.navjava.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navjava.Acceuil;
import com.example.navjava.Model.Appartment;
import com.example.navjava.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AcceuilAdapter extends RecyclerView.Adapter<AcceuilAdapter.MyViewHolder> {


    private Context context;
    private List<Appartment> appartmentList;
    private int j;

    private Acceuil fragment2;

    public AcceuilAdapter(Context context, ArrayList<Appartment> map, Acceuil fragment2
    ){
        this.context = context;
        Log.i("TAG","map size :: "+String.valueOf(map.size()) );
        this.appartmentList = map;


        this.fragment2 = fragment2;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_acceuil, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount(){return appartmentList.size();}

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView location, price;
        ImageView image;
        Button map, infos;

        View layout;
        public MyViewHolder(View view){
            super(view);
            layout = view;
            Log.i("TAG","ok");
            location = (TextView) view.findViewById(R.id.location);
            price = view.findViewById(R.id.price);
            image = view.findViewById(R.id.imageView);
            map = view.findViewById(R.id.map);
            infos = view.findViewById(R.id.infos);

        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){
        j = position;
        Appartment fragment2 = appartmentList.get(position);
        holder.location.setText(fragment2.getRef());
        holder.price.setText((fragment2.getPrice()+" DA"));
        Picasso.get().load("http://192.168.1.2/Zahra/"+fragment2.getImage()).into(holder.image);
        //this.fragment2.prepareData();
        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "map"+position, Toast.LENGTH_SHORT).show();
            }
        });

        holder.infos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "infos"+position, Toast.LENGTH_SHORT).show();
            }
        });
        Log.i("TAG", "name::  "+fragment2.getName());


    }
}
