package com.Keyforge_management.ui.main;

import android.app.AlertDialog;
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

        DeckListAdapter mAdapter = new DeckListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        repository = new DeckRepository(this);
        repository.getAllDecks().observe(this, mAdapter::onNewDecks);
    }

    @Override
    public void onDeckClicked(Deck deck) {
        DetailActivity.start(this);
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
            case R.id.scan_decks:
                Toast.makeText(this, "ScanActivity deck not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about_us:
                Toast.makeText(this, "About us not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.search_deck:
                SearchActivity.start(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
