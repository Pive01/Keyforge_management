package com.KeyforgeManagement.application.ui.main.Informations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.KeyforgeManagement.application.R;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class CreditsActivity extends AppCompatActivity {

    private static String txtToShow;

    public static void start(Context context, String txtToShowPassed) {
        context.startActivity(new Intent(context, CreditsActivity.class));
        txtToShow = txtToShowPassed;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        setSupportActionBar(findViewById(R.id.toolbar_infomation));

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView showCredits = findViewById(R.id.mainTextView);
        showCredits.setText(txtToShow);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
