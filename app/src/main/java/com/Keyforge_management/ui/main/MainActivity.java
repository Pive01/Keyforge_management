package com.Keyforge_management.ui.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.Keyforge_management.R;
import com.Keyforge_management.data.model.Deck;
import com.Keyforge_management.data.storage.DeckRepository;
import com.Keyforge_management.ui.decklist.DeckListAdapter;
import com.Keyforge_management.ui.decklist.DeckListInteractionListener;
import com.Keyforge_management.ui.detail.DetailActivity;
import com.Keyforge_management.ui.search.SearchActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity implements DeckListInteractionListener {

    private DeckRepository repository;
    private final int SAS = 0;
    private final int AEMBER = 1;
    private final int CREATURE = 2;
    private final int ACTION = 3;
    private final int ARTIFACT = 4;
    private DeckListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        View fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> SearchActivity.start(this));

        RecyclerView mRecyclerView = findViewById(R.id.decksRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new DeckListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        repository = new DeckRepository(this);
        repository.getAllDecks().observe(this, mAdapter::onNewDecks);
    }

    @Override
    public void onDeckClicked(Deck deck) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("deckInfo", deck);
        DetailActivity.start(this, i);
        System.out.println(deck.toString());
    }

    @Override
    public void onLongDeckClicked(Deck deck) {
        new AlertDialog.Builder(this)
                .setTitle("Remove a deck")
                .setMessage("Are you sure you want to remove this deck?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> repository.delete(deck))
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_decks:
                return true;
            case R.id.action_about_us:
                Toast.makeText(this, "About us not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.search_deck:
                SearchActivity.start(this);
                return true;
            case R.id.sort_by_sas:
                mAdapter.sort(SAS);
                return true;
            case R.id.sort_by_amber:
                mAdapter.sort(AEMBER);
                return true;
            case R.id.sort_by_creature_count:
                mAdapter.sort(CREATURE);
                return true;
            case R.id.sort_by_action_count:
                mAdapter.sort(ACTION);
                return true;
            case R.id.sort_by_artifact_count:
                mAdapter.sort(ARTIFACT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
