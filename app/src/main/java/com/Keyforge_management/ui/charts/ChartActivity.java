package com.Keyforge_management.ui.charts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.Keyforge_management.R;
import com.Keyforge_management.data.model.Deck;
import com.Keyforge_management.data.model.Stats;
import com.Keyforge_management.data.storage.Deck.DeckRepository;
import com.Keyforge_management.data.storage.typeConverters.HouseArrayTypeConverter;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChartActivity extends AppCompatActivity {

    private static Stats statistic;
    private static Deck mostWinDeck;
    private static Deck betterWinRate;
    private static int totalPlays;
    private static double bestTotalPlaysWinRate = 0;

    public static void start(Context context, Intent i) {
        context.startActivity(new Intent(context, ChartActivity.class));
        statistic = (Stats) i.getSerializableExtra("stats");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Toolbar infoToolbar = findViewById(R.id.charts_toolbar);
        infoToolbar.setTitle("Charts");

        setSupportActionBar(infoToolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        BarChart chart = findViewById(R.id.houses_chart);
        BarChartImplementer chartImplementer = new BarChartImplementer(chart, statistic,
                "Houses Win Rates");

        List<Bitmap> imageList = new ArrayList<>();
        statistic.getHouseWinRate().forEach(item -> {
            if (item.getX().toUpperCase().equals("STARALLIANCE"))
                item.setX("STAR_ALLIANCE");
            imageList.add(BitmapFactory.decodeResource(getResources(),
                    HouseArrayTypeConverter.fromSingleString(item.getX().toUpperCase()).getImageId()));
        });
        chartImplementer.createHousesBarChart(getApplicationContext(), imageList);


        DeckRepository repository;
        repository = new DeckRepository(this);
        repository.getAllDecks().observe(this, this::getSatsDecks);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getSatsDecks(List<Deck> deckList) {
        mostWinDeck = deckList.get(0);
        betterWinRate = deckList.get(0);

        deckList.forEach(item -> {

            if (item.getLocalWins() > mostWinDeck.getLocalWins())
                mostWinDeck = item;

            totalPlays = item.getLocalLosses() + item.getLocalWins();

            if ((item.getLocalWins() != 0) && (((double) item.getLocalWins()) / totalPlays) * 100 > bestTotalPlaysWinRate) {

                betterWinRate = item;
                bestTotalPlaysWinRate = (((double) item.getLocalWins()) / totalPlays) * 100;
            }

        });
        fillView(mostWinDeck, findViewById(R.id.most_win_deck));
        fillView(betterWinRate, findViewById(R.id.most_win_rate_deck));

        TextView winRate = findViewById(R.id.txtView_winrate);
        winRate.setText(winRate.getText() + " " + bestTotalPlaysWinRate + "%");
    }

    private void fillView(Deck deck, View itemView) {
        ImageView[] houseArr = new ImageView[]{
                itemView.findViewById(R.id.img_house1),
                itemView.findViewById(R.id.img_house2),
                itemView.findViewById(R.id.img_house3)
        };
        TextView deckName = itemView.findViewById(R.id.deck_name);
        TextView expansion = itemView.findViewById(R.id.expansion_name);
        TextView sasScore = itemView.findViewById(R.id.sas_rating);
        TextView rawAember = itemView.findViewById(R.id.raw_amber);

        houseArr[0].setImageResource(deck.getHouses()[0].getImageId());
        houseArr[1].setImageResource(deck.getHouses()[1].getImageId());
        houseArr[2].setImageResource(deck.getHouses()[2].getImageId());
        deckName.setText(deck.getName());
        expansion.setText(deck.getExpansion().toString());
        sasScore.setText(Integer.toString(deck.getSasRating()));
        rawAember.setText(Integer.toString(deck.getRawAmber()));
    }
}