package com.example.navjava;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.Arrays;
import java.util.List;

public class Settings extends Fragment {

    public static Settings newInstance() {
        return (new Settings());
    }

    MainActivity mapsActivity;
    private List<String> settings_item = Arrays.asList("item1","item2","itemi","itemn");
    private List<Integer> Settings_icons = Arrays.asList(R.drawable.minus, R.drawable.location, R.drawable.search2, R.drawable.ic_menu_camera);
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings, container, false);
        mapsActivity = (MainActivity) getActivity();
        ((MainActivity) getActivity()).setActionBarTitle("Settings");

        listView = view.findViewById(R.id.listview);
        /** recuperer les valeurs de la liste de settings ici avant d'appeler l'adapter*/

        cusmus cusmus = new cusmus();
        listView.setAdapter(cusmus);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:{
                        /** item1 is clicked*/

                        /** Example of how to switch between screens when you are in fragment*/
                        TabBarExample nextFrag = new TabBarExample();
                        /** if you need to pass a parameter you need t create methods and nextFrag.method(param)*/
                        /** start the transaction*/
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(getId(), nextFrag)
                                .commit();
                        break;

                    }
                    case 1:{
                        /**item 2 is clicked*/
                    }
                    case 2:{
                        /**itemi is clicked*/
                    }
                    case 3:{
                        /**itemn is clicked*/
                    }
                }
            }
        });





        return view;
    }

    public class cusmus extends BaseAdapter{
        @Override
        public int getCount(){return settings_item.size();}

        @Override
        public Object getItem(int position){return null;}

        @Override
        public long getItemId(int position){return 0;}

        @Override
        public View getView(int position, View view, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();

            view = inflater.inflate(R.layout.simple_list_item, null);
            TextView textView = view.findViewById(R.id.txt);
            ImageView imageView = view.findViewById(R.id.imageView);
            textView.setText(settings_item.get(position));
            imageView.setImageDrawable(getResources().getDrawable(Settings_icons.get(position)));

            return view;


        }
    }


}
