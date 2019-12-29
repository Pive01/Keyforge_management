package com.Keyforge_management.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.Keyforge_management.R;
import com.Keyforge_management.data.model.Deck;
import com.Keyforge_management.data.storage.DeckRepository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.Keyforge_management.common.Utils.absolute;


public class DetailActivity extends AppCompatActivity {

    private static Deck deck;
    private DeckRepository repository;
    private TextView winsView;
    private TextView lossesView;


    public static void start(Context context, Intent i) {
        context.startActivity(new Intent(context, DetailActivity.class));
        deck = (Deck) i.getSerializableExtra("deckInfo");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar infoToolbar = findViewById(R.id.deck_info_toolbar);
        infoToolbar.setTitle(deck.getName());
        winsView = findViewById(R.id.winsCounter);
        lossesView = findViewById(R.id.lossCounter);

        updateView();
        Button addWin = findViewById(R.id.addWins);
        Button addLoss = findViewById(R.id.addLoss);
        Button removeWin = findViewById(R.id.removeWins);
        Button removeLoss = findViewById(R.id.removeLoss);

        repository = new DeckRepository(this);

        addLoss.setOnClickListener(v -> {
            deck.setLocalLosses((deck.getLocalLosses() + 1));
            updateLosses();

        });
        addWin.setOnClickListener(v -> {
            deck.setLocalWins((deck.getLocalWins() + 1));
            updateWins();

        });
        removeWin.setOnClickListener(v -> {
            deck.setLocalWins(absolute((deck.getLocalWins() - 1)));
            updateWins();
        });
        removeLoss.setOnClickListener(v -> {
            deck.setLocalLosses(absolute((deck.getLocalLosses() - 1)));
            updateLosses();
        });
    }

    private void updateWins() {
        repository.updateWins(deck.getLocalWins(), deck.getId());
        updateView();
    }

    private void updateLosses() {
        repository.updateLosses(deck.getLocalLosses(), deck.getId());
        updateView();
    }

    private void updateView() {
        winsView.setText(String.valueOf(deck.getLocalWins()));
        lossesView.setText(String.valueOf(deck.getLocalLosses()));
    }

}
//ViewPager