package com.Keyforge_management.ui.main;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.Keyforge_management.R;
import com.Keyforge_management.data.model.Deck;
import com.Keyforge_management.data.storage.Deck.DeckRepository;
import com.Keyforge_management.data.storage.DeckWithCards.DeckCardRepository;
import com.Keyforge_management.ui.decklist.DeckListAdapter;
import com.Keyforge_management.ui.decklist.DeckListInteractionListener;
import com.Keyforge_management.ui.detail.DetailActivity;
import com.Keyforge_management.ui.search.SearchActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity implements DeckListInteractionListener {

    private DeckRepository repository;
    private DeckListAdapter mAdapter;
    private DeckCardRepository deckCardRepository;

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

        deckCardRepository = new DeckCardRepository(this);
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
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    deckCardRepository.delete(deck);
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search_deck_mylist);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                searchItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int ARTIFACT = 4;
        int ACTION = 3;
        int CREATURE = 2;
        int AEMBER = 1;
        int SAS = 0;
        switch (item.getItemId()) {
            case R.id.sort_decks:
                return true;
            case R.id.action_about_us:
                Toast.makeText(this, "About us not yet implemented", Toast.LENGTH_SHORT).show();
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
