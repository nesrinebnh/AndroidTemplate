package com.example.navjava;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimplaCardView extends Fragment {

    public static SimplaCardView newInstance() {
        return (new SimplaCardView());
    }

    MainActivity mapsActivity;

    TextView txt1, txt2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment1, container, false);
        mapsActivity = (MainActivity) getActivity();
        ((MainActivity) getActivity()).setActionBarTitle("SimplaCardView");
        txt1 = view.findViewById(R.id.text);
        txt2 = view.findViewById(R.id.text2);

        /** you extract data from database, but i keep things sample for me*/
        txt1.setText("text1");
        txt2.setText("text2");
        return view;
    }
}
