package com.Keyforge_management.ui.main;

import android.os.Bundle;
import android.widget.Button;

import com.Keyforge_management.R;
import com.Keyforge_management.ui.search.SearchActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Button addDeckBtn = findViewById(R.id.addBtn);

        addDeckBtn.setOnClickListener(v -> SearchActivity.start(MainActivity.this));
    }
}
