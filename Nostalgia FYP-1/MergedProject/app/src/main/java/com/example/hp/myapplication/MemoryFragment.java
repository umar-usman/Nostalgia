package com.example.hp.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by hp on 12/15/2017.
 */

public class MemoryFragment extends android.support.v4.app.Fragment {

    private RecyclerView mFriendsList;

    private DatabaseReference mFriendsDatabase;
    private DatabaseReference mUsersDatabase;
    private String mCurrent_user_id;
    private View mMainView;
    private DatabaseReference mDatabaseReference,mMemoryReference,mUserReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mAuth;
    private static final String MY_PREFS_NAME = "MyPrefsFile";
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    ArrayList<String> arrayListPic;

    public MemoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.frament_memory, container, false);
        mAuth = FirebaseAuth.getInstance();
        arrayList = new ArrayList<String>();
        arrayListPic = new ArrayList<String>();
//        arrayList.add("Sami Noor Khan");
//        arrayList.add("Meherawar");
        // Toast.makeText(getActivity(), mAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String place_name = prefs.getString("MyLocation", null);
        String place_address = prefs.getString("MyAddress", null);
        String Current_place = place_name + ", " + place_address;
//        Toast.makeText(getActivity(), "" + Current_place, Toast.LENGTH_LONG).show();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Friends").child(mAuth.getCurrentUser().getUid());
        mMemoryReference = FirebaseDatabase.getInstance().getReference().child("Views").child(Current_place).child("Memories");
        //mUserReference = FirebaseDatabase.getInstance().getReference().child("Users");
        listView = mMainView.findViewById(R.id.listView);
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_expandable_list_item_1,arrayList);
        listView.setAdapter(adapter);
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String friendUID = dataSnapshot.getKey();
                //Toast.makeText(getActivity(), "FRIEND"+ friendUID, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), friendUID, Toast.LENGTH_SHORT).show();
                mMemoryReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String chkId = dataSnapshot.child("userID").getValue(String.class);
                        final String picId = dataSnapshot.child("Picture URL").getValue(String.class);
                        //Toast.makeText(getActivity(), "a"+chkId, Toast.LENGTH_SHORT).show();
                       // Toast.makeText(getActivity(), "Sami"+friendUID, Toast.LENGTH_SHORT).show();
                        if(friendUID.equals(chkId)) {
                            //Toast.makeText(getActivity(), "FRIEND"+chkId, Toast.LENGTH_SHORT).show();
                            mUserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(chkId);
                            mUserReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String name = dataSnapshot.child("name").getValue(String.class);
                                    arrayList.add(name);
                                    arrayListPic.add(picId);
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
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pictureURL = arrayListPic.get(i);
                Intent intent = new Intent(getActivity(),FullImageActivity.class);
                intent.putExtra("Image",pictureURL);
                startActivity(intent);
//                MemoriesFragment memoriesFragment = new MemoriesFragment();
//                Bundle args = new Bundle();
//                args.putString("KeyofUser", userKey);
//                memoriesFragment.setArguments(args);
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.content_frame, memoriesFragment,"Hello")
//                        .addToBackStack(null)
//                        .commit();
            }
        });


        return mMainView;
    }
}