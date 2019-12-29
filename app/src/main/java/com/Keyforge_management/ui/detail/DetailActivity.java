package com.Keyforge_management.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.Keyforge_management.R;

import androidx.appcompat.app.AppCompatActivity;


public class DetailActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, DetailActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
//ViewPager