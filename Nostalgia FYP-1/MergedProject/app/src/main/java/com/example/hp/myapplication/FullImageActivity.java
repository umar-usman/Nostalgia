package com.example.hp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FullImageActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        Intent intent = getIntent();
        String image = intent.getStringExtra("Image");
//        Toast.makeText(this, image , Toast.LENGTH_SHORT).show();
        imageView = findViewById(R.id.imageView);
        Picasso.with(FullImageActivity.this).load(image).placeholder(R.drawable.default_avatar).into(imageView);
    }
}
