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
import com.Keyforge_management.ui.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity implements DeckListInteractionListener {

    private RecyclerView.Adapter mAdapter;
    private List<Deck> myDecksList;
    private DeckRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        View fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> SearchActivity.start(MainActivity.this));

        repository = new DeckRepository(this);
        displayDecks();

        myDecksList = new ArrayList<>();
        RecyclerView mRecyclerView = findViewById(R.id.decksRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new DeckListAdapter(myDecksList, this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void displayDecks() {
        repository.getAllDecks().observe(this, (decks) -> {
            myDecksList.clear();
            myDecksList.addAll(decks);
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onDeckClicked(Deck deck) {
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
                System.out.println("item 1 pressed");
                Toast.makeText(MainActivity.this, "Scan deck not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about_us:
                Toast.makeText(MainActivity.this, "About us not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.search_deck:
                SearchActivity.start(MainActivity.this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
