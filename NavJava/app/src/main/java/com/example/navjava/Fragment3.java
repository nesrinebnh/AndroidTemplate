package com.example.navjava;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;

public class Fragment3 extends Fragment {

    public static Fragment3 newInstance() {
        return (new Fragment3());
    }

    MainActivity mapsActivity;

    TextView txt, txt1;
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment3, container, false);
        mapsActivity = (MainActivity) getActivity();
        ((MainActivity) getActivity()).setActionBarTitle("Fragment1");

        txt = view.findViewById(R.id.txt1);
        txt1 = view.findViewById(R.id.txt2);
        btn = view.findViewById(R.id.btn);

        txt.setText("hello");
        txt1.setText("hi");
        btn.setText("click");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "click btn effect",Toast.LENGTH_LONG).show();


            }
        });

        return view;
    }
}
