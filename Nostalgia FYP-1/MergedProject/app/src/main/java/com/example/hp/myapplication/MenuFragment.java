package com.example.hp.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by hp on 12/15/2017.
 */

public class MenuFragment extends android.support.v4.app.Fragment{
    private View mMainView;
    ImageView imageView;
    private static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String place_name = prefs.getString("MyLocation", null);
        String place_address = prefs.getString("MyAddress", null);
        String Current_place = place_name + ", " + place_address;
        mMainView = inflater.inflate(R.layout.menu_fragment, container, false);

        imageView = mMainView.findViewById(R.id.menu_img);
        if(Current_place.equals("National University Of Computer & Emerging Sciences, National Highway، Karachi, Pakistan")) {
            Picasso.with(getActivity()).load("https://firebasestorage.googleapis.com/v0/b/fyp1-7736f.appspot.com/o/IMAG0162.jpg?alt=media&token=f21099d2-e258-4d8a-9e39-f63627fb5fd7").placeholder(R.drawable.default_avatar).into(imageView);
        }

        return mMainView;
    }

}
