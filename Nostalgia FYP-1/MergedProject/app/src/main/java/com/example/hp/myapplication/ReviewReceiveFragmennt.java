package com.example.hp.myapplication;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by hp on 12/15/2017.
 */

public class ReviewReceiveFragmennt  extends android.support.v4.app.Fragment {

    private DatabaseReference uUserDatabaseReference;

    private DatabaseReference myRef,mReviewReference,mDatabaseReference,mUserReference;
    private FirebaseAuth mAuth;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    ArrayList<String> arrayListPic;
    private StorageReference mStorage;
    private static final String MY_PREFS_NAME = "MyPrefsFile";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review_receive, container, false);
        mAuth = FirebaseAuth.getInstance();
        arrayList = new ArrayList<String>();
        arrayListPic = new ArrayList<String>();
        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String place_name = prefs.getString("MyLocation", null);
        String place_address = prefs.getString("MyAddress", null);
        String Current_place = place_name + ", " + place_address;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Friends").child(mAuth.getCurrentUser().getUid());
        mReviewReference = FirebaseDatabase.getInstance().getReference().child("Views").child(Current_place).child("Reviews");
        listView = v.findViewById(R.id.listView);
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_expandable_list_item_1,arrayList);
        listView.setAdapter(adapter);
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String friendUID = dataSnapshot.getKey();
                mReviewReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String chkId = dataSnapshot.child("userID").getValue(String.class);
                        final String review = dataSnapshot.child("Review").getValue(String.class);
                        if(friendUID.equals(chkId)) {
                            //Toast.makeText(getActivity(), "FRIEND"+chkId, Toast.LENGTH_SHORT).show();
                            mUserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(chkId);
                            mUserReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String name = dataSnapshot.child("name").getValue(String.class);
                                    arrayList.add(name+"\n"+review);
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }
}
