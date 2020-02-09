package com.KeyforgeManagement.application.ui.main.Informations;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.KeyforgeManagement.application.R;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

import androidx.appcompat.app.AppCompatActivity;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class Information extends AppCompatActivity {

    private long mLastClickTime = 0;

    public static void start(Context context) {
        context.startActivity(new Intent(context, Information.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(findViewById(R.id.toolbar_infomation));

        String version = "";
        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription("Application Information")
                .addItem(new Element().setTitle("Version " + version))
                .addItem(getDisclaimer())
                .addItem(getPrivacyPolicy())
                .addItem(getLicenseElement())
                .addEmail("lucapivettaworkstuff@gmail.com")
                .addPlayStore("com.KeyforgeManagement.application")
                .addGitHub("Pive01/Keyforge_management")
                .create();

        setContentView(aboutPage);
    }

    private Element getPrivacyPolicy() {
        Element privacy = new Element();
        privacy.setTitle("Privacy policy");
        privacy.setOnClickListener(v -> CreditsActivity.start(this, getString(R.string.crashLytics)));
        return privacy;
    }

    private Element getDisclaimer() {
        Element discalimer = new Element();
        discalimer.setTitle("Keyforge Information");
        discalimer.setOnClickListener(v -> CreditsActivity.start(this, getString(R.string.Information)));
        return discalimer;
    }

    private Element getLicenseElement() {

        Element license = new Element();
        license.setTitle(getString(R.string.licenses));
        license.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            startActivity(new Intent(getApplicationContext(), OssLicensesMenuActivity.class));
        });
        return license;
    }
}
