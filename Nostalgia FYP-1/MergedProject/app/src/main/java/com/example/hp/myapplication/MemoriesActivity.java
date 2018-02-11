package com.example.hp.myapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MemoriesActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private MemoryPagerAdapter mMemoryPagerAdapter;

    //private DatabaseReference mUserRef;

    private TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories);
        //Tabs
        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Nostalgia");
        mViewPager = findViewById(R.id.main_tabPager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mMemoryPagerAdapter =  new MemoryPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMemoryPagerAdapter);
        mTabLayout =findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
