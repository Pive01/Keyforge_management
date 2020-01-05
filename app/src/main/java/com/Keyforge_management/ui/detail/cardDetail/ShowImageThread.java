package com.Keyforge_management.ui.detail.cardDetail;

import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ShowImageThread implements Runnable {
    private String url;
    private CardDetailActivity act;

    public ShowImageThread(String url, CardDetailActivity act) {
        this.url = url;
        this.act = act;
    }

    @Override
    public void run() {
        try {
            act.getMyIamge(BitmapFactory.decodeStream((InputStream) new URL(url).getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
