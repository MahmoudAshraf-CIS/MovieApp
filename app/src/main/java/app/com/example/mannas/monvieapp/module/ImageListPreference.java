package app.com.example.mannas.monvieapp.module;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.example.mannas.monvieapp.R;

/**
 * A Costume Preference display a Costume  {@link ListView} into {@link DialogFragment}
 * <h3>How it Works </h3> passing the parameters {@link Context}, ArrayList<{@link Movie_itemMode } > construct the ListView
 * BUT does not display it, to <h3>show</h3> the DialogFragment call {@code StartList(FragmentActivity fragmentActivity , String Tag) }
 * <h3>NOTE :-</h3> the ListView is associated with CustomAdapter that - Item = {@link ImageView} + {@link TextView}
 *<h3>Return Value</h3> the selected item is stored into - theSellectedItem - if it != {@link null} after the DialogFragment dismiss()
 */

public  class ImageListPreference extends Preference{
        static ArrayList<Movie_itemMode> ls;
        public static Movie_itemMode theSellectedItem = null;

        public static ImageListPreference this_imageListPreferenceFreagment;
        public Context context;

        public ImageListPreference(Context context, ArrayList<Movie_itemMode> ListItems) {
            super(context);
            this.context = context;
            ls = ListItems;

        }
        public void StartList(FragmentActivity fragmentActivity , String Tag){
            ImageListPreferenceFreagment imageListPreferenceFreagment = new ImageListPreferenceFreagment();
            imageListPreferenceFreagment.show(fragmentActivity.getSupportFragmentManager(),Tag);
            this_imageListPreferenceFreagment = this;
        }

        public static class ImageListPreferenceFreagment extends DialogFragment {
            ImageListPreferenceFreagment thisRef;
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.image_list_preference_fragment ,null,false);
                thisRef = this;

                ListView listView = (ListView) view.findViewById(R.id.image_list_preference_fragment_listView);
                listView.setAdapter(new CustomAdapter(getContext(),ls));
                listView.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                              // Toast.makeText(getContext(),Integer.toString(i),Toast.LENGTH_LONG).show();
                                theSellectedItem = ls.get(i);
                                thisRef.dismiss();
                                this_imageListPreferenceFreagment.setSummary(ls.get(i).name);
                            }
                        }
                );

                return view;
            }
        }
        public Movie_itemMode getTheSellectedIndex(){
            return theSellectedItem;
        }
        public static class CustomAdapter  extends BaseAdapter {

            private ArrayList<Movie_itemMode> itemsList;
            private LayoutInflater inflater = null;

            public CustomAdapter(Context context , @NonNull ArrayList<Movie_itemMode> objects){
                itemsList = objects;
                inflater = LayoutInflater.from(context);
            }
            @Override
            public int getCount() {
                return itemsList.size();
            }
            @Override
            public Object getItem(int position) {
                return itemsList.get(position);
            }
            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View rootView = convertView;
                if(convertView == null && inflater != null)
                    rootView = inflater.inflate( R.layout.item , parent,false );

                Movie_itemMode it = itemsList.get(position);

                ImageView img = (ImageView) rootView.findViewById(R.id.Item_image);
                TextView txt = (TextView) rootView.findViewById(R.id.Item_txt);

                if(img != null)
                    img.setImageResource(it.Drwablw_icon_Res);
                if(txt != null)
                    txt.setText(it.name);

                return rootView;
            }

        }
        public static class Movie_itemMode{

        public int Drwablw_icon_Res;
        public String name;

        public Movie_itemMode(int drwablw_icon, String name) {
            Drwablw_icon_Res = drwablw_icon;
            this.name = name;
        }
    }

}
