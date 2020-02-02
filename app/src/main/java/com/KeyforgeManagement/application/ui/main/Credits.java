package com.KeyforgeManagement.application.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.KeyforgeManagement.application.R;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class Credits extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, Credits.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        setSupportActionBar(findViewById(R.id.toolbar_infomation));

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String version = "";
        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        TextView versionNumber = findViewById(R.id.version_number);
        versionNumber.setText("Version " + version);

        Button showLicense = findViewById(R.id.showLicense);
        showLicense.setOnClickListener(v -> {
            startActivity(new Intent(this, OssLicensesMenuActivity.class));
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
