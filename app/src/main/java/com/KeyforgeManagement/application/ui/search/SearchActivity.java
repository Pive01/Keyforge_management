package com.KeyforgeManagement.application.ui.search;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.common.Utils;
import com.KeyforgeManagement.application.data.api.ApiPerformer;
import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.storage.DatabaseSaver;
import com.KeyforgeManagement.application.ui.deckDTOlist.ListPaddingDecoration;
import com.KeyforgeManagement.application.ui.decklist.DeckListAdapter;
import com.KeyforgeManagement.application.ui.decklist.DeckListInteractionListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static androidx.appcompat.widget.SearchView.OnQueryTextListener;

public class SearchActivity extends AppCompatActivity implements DeckListInteractionListener {

    private DeckListAdapter mAdapter;
    private DatabaseSaver dbs;
    private final Pattern DECK_ID_PATTERN = Pattern.compile(".{8}-.{4}-.{4}-.{4}-.{12}");
    private ProgressBar loadingDecks;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setSupportActionBar(findViewById(R.id.toolbar_search));

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadingDecks = findViewById(R.id.progress_bar);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerViewSrc);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new DeckListAdapter(this);

        dbs = new DatabaseSaver(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new ListPaddingDecoration(getApplicationContext()));


        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            Matcher m = DECK_ID_PATTERN.matcher(intent.getStringExtra(Intent.EXTRA_TEXT));
            if (m.find())
                searchById(m.group());
            else
                displayDecks(intent.getStringExtra(Intent.EXTRA_TEXT));
        }
    }

    private void displayDecks(String name) {
        loadingDecks.setVisibility(View.VISIBLE);
        ApiPerformer.getDecksFromName(deckList -> {
            if (deckList == null || deckList.size() == 0)
                showSnackBar("No decks found for your query");
            else
                mAdapter.onNewDecks(Utils.convertToOldList(deckList));
            loadingDecks.setVisibility(View.GONE);
        }, failure -> {
            loadingDecks.setVisibility(View.GONE);
            failure.printStackTrace();
            showSnackBar("There has been an error while loading decks\n Try again later");
        }, name);
    }

    @Override
    public void onDeckClicked(Deck deck) {
        new AlertDialog.Builder(this)
                .setTitle("Add a deck")
                .setMessage("Are you sure you want to add this deck?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> dbs.saveDeck(deck))
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public void onLongDeckClicked(Deck deck) {
    }

    private void searchById(String id) {
        loadingDecks.setVisibility(View.VISIBLE);
        ApiPerformer.getDecksFromId(singleDeckReference -> {
            loadingDecks.setVisibility(View.GONE);
            if (singleDeckReference == null)
                showSnackBar("Bad id");
            else {
                List<Deck> justToMakeItWork = new ArrayList<>();
                justToMakeItWork.add(singleDeckReference.getDeck().convertToOld());
                mAdapter.onNewDecks(justToMakeItWork);
            }
        }, failure -> {
            loadingDecks.setVisibility(View.GONE);
            showSnackBar("This id doesn't correspond to any deck");
        }, id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search_button);
        searchItem.expandActionView();
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search deck, name or id");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Matcher m = DECK_ID_PATTERN.matcher(searchView.getQuery().toString());
                searchView.clearFocus();
                if (m.find())
                    searchById(m.group());
                else
                    displayDecks(searchView.getQuery().toString());
                searchItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showSnackBar(String s) {
        Snackbar.make(
                findViewById(R.id.activity_search_parent_layout),
                s, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", view -> {
                })
                .setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                .show();
    }
}
