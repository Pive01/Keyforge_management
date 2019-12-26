package com.Keyforge_management.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;

import com.Keyforge_management.R;
import com.Keyforge_management.data.model.Deck;
import com.Keyforge_management.data.storage.DeckRepository;
import com.Keyforge_management.ui.search.InteractionListener;
import com.Keyforge_management.ui.search.SearchActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity implements InteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Button addDeckBtn = findViewById(R.id.addBtn);

        addDeckBtn.setOnClickListener(v -> SearchActivity.start(MainActivity.this));

        DeckRepository deckRepository = new DeckRepository(this);
        deckRepository.getAllDecks().observe(this, (decks) -> {
            for (Deck deck : decks) {
                System.out.println(deck);
            }
        });
    }

    public void onDeckClicked(Deck deck) {
        new AlertDialog.Builder(this)
                .setTitle("Remove a deck")
                .setMessage("Are you sure you want to remove this deck?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DeckRepository deckRepository = new DeckRepository(MainActivity.this);
                        deckRepository.delete(deck);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
}
