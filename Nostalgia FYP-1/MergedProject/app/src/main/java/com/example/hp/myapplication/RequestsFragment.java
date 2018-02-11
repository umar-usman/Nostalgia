package com.example.hp.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {
    private Toolbar mToolbar;

    private RecyclerView mUsersList;
    private LinearLayoutManager mLayoutManager;

    private RecyclerView mRequestList;

    private DatabaseReference mFriendsDatabase,mDatabaseRef;
    private DatabaseReference mUsersDatabase,mDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;
    private View mMainView;
    private ArrayList<String> uIDs;
    RecyclerView listshowrcy;
    List<Requests> productlists = new ArrayList<>();
    RequestsAdapter adapter;


    public RequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_requests, container, false);
        // mRequestList = (RecyclerView) mMainView.findViewById(R.id.users_list);
        mAuth = FirebaseAuth.getInstance();
        mCurrent_user_id = mAuth.getCurrentUser().getUid();
        // Toast.makeText(getActivity(), mCurrent_user_id, Toast.LENGTH_SHORT).show();
        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req").child(mAuth.getCurrentUser().getUid());
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req").child(mAuth.getCurrentUser().getUid());
        //mFriendsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        //mUsersDatabase.keepSynced(true);
        listshowrcy = (RecyclerView) mMainView.findViewById(R.id.users_list);
        listshowrcy.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        listshowrcy.setLayoutManager(linearLayoutManager);
        adapter = new RequestsAdapter(productlists, getActivity());
        listshowrcy.setAdapter(adapter);




        //mRequestList.setHasFixedSize(true);
        //mRequestList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return mMainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        final String n;
        mFriendsDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String user_key = dataSnapshot.getKey();
                String request_type = dataSnapshot.child("request_type").getValue(String.class);
                //Toast.makeText(getActivity(), user_key, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), request_type, Toast.LENGTH_SHORT).show();
                if (request_type.equals("received")) {
                    //Toast.makeText(getActivity(), dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user_key);
                    mDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Toast.makeText(getActivity(), dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                            DataSnapshot ds = dataSnapshot;
                            String username = dataSnapshot.child("name").getValue(String.class);
                            Toast.makeText(getActivity(), username, Toast.LENGTH_SHORT).show();
                            String image = dataSnapshot.child("image").getValue(String.class);
                            Toast.makeText(getActivity(), image, Toast.LENGTH_SHORT).show();
                            String status = dataSnapshot.child("status").getValue(String.class);
                            Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                            String thumb_image = dataSnapshot.child("thumb_image").getValue(String.class);
                            Requests r = new Requests(dataSnapshot.getKey(), username, image, status, thumb_image);
                            productlists.add(r);
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
    public void onPause() {
        super.onPause();
        productlists = new ArrayList<>();
    }
}
