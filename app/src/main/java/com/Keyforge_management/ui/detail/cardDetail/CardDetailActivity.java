package com.Keyforge_management.ui.detail.cardDetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.Keyforge_management.R;
import com.Keyforge_management.data.model.Card;
import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CardDetailActivity extends AppCompatActivity {

    private static Card card;
    private Bitmap image;

    public static void start(Context context, Intent i) {
        context.startActivity(new Intent(context, CardDetailActivity.class));
        card = (Card) i.getSerializableExtra("card");
    }

    public void getMyIamge(Bitmap image) {
        this.image = image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);


        Toolbar infoToolbar = findViewById(R.id.card_toolbar);

        infoToolbar.setTitle(card.getCard_title());
        setSupportActionBar(infoToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView imageView = findViewById(R.id.cardicon);
        Glide.with(this).load(card.getFront_image()).into(imageView);

        TextView type = findViewById(R.id.card_type);
        type.setText(card.getCard_type());
        TextView text = findViewById(R.id.card_text);
        text.setText(card.getCard_text());
        TextView amber = findViewById(R.id.amber_number);
        amber.setText(String.valueOf(card.getAmber()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
