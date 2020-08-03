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

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.common.Utils;
import com.KeyforgeManagement.application.data.api.Api;
import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.model.adaptation.NewDeckFormat;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.SingleDeckReference;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.appcompat.widget.SearchView.OnQueryTextListener;

public class SearchActivity extends AppCompatActivity implements DeckListInteractionListener {

    private DeckListAdapter mAdapter;
    private DatabaseSaver dbs;
    private final Pattern p = Pattern.compile(".{8}-.{4}-.{4}-.{4}-.{12}");
    private ProgressBar loadingDecks;
    private static boolean byName = false;

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
            System.out.println(intent.getStringExtra(Intent.EXTRA_TEXT));
            Matcher m = p.matcher(intent.getStringExtra(Intent.EXTRA_TEXT));
            if (m.find())
                searchById(m.group());
            else
                displayDecks(intent.getStringExtra(Intent.EXTRA_TEXT));
        }
    }

    private void displayDecks(String name) {
        byName = true;
        loadingDecks.setVisibility(View.VISIBLE);
        Api.getDecks(name).enqueue(new Callback<List<NewDeckFormat>>() {
            @Override
            public void onResponse(Call<List<NewDeckFormat>> call, Response<List<NewDeckFormat>> response) {

                if (response.body() == null || response.body().size() == 0)
                    showSnackBar("No decks found for your query");
                else
                    mAdapter.onNewDecks(Utils.convertToOldList(response.body()));

                loadingDecks.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<NewDeckFormat>> call, Throwable t) {
                loadingDecks.setVisibility(View.GONE);
                t.printStackTrace();
                showSnackBar("There has been an error while loading decks\n Try again later");
            }
        });
    }

    @Override
    public void onDeckClicked(Deck deck) {
        new AlertDialog.Builder(this)
                .setTitle("Add a deck")
                .setMessage("Are you sure you want to add this deck?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    if (byName) {
                        loadingDecks.setVisibility(View.VISIBLE);
                        Api.getDeckFromId(deck.getKeyforgeId()).enqueue(new Callback<SingleDeckReference>() {
                            @Override
                            public void onResponse(Call<SingleDeckReference> call, Response<SingleDeckReference> response) {
                                if (response.body() == null) {
                                    showSnackBar("Error");
                                } else
                                    dbs.saveDeck(response.body().getDeck().convertToOld());
                                loadingDecks.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<SingleDeckReference> call, Throwable t) {
                                loadingDecks.setVisibility(View.GONE);
                                showSnackBar("Error");
                            }
                        });

                    } else
                        dbs.saveDeck(deck);
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public void onLongDeckClicked(Deck deck) {
    }

    private void searchById(String id) {
        byName = false;
        loadingDecks.setVisibility(View.VISIBLE);
        Api.getDeckFromId(id).enqueue(new Callback<SingleDeckReference>() {
            @Override
            public void onResponse(Call<SingleDeckReference> call, Response<SingleDeckReference> response) {
                loadingDecks.setVisibility(View.GONE);
                if (response.body() == null) {
                    showSnackBar("Bad id");
                } else {
                    List<Deck> justToMakeItWork = new ArrayList<>();
                    justToMakeItWork.add(response.body().getDeck().convertToOld());
                    mAdapter.onNewDecks(justToMakeItWork);
                }

            }

            @Override
            public void onFailure(Call<SingleDeckReference> call, Throwable t) {
                loadingDecks.setVisibility(View.GONE);
                showSnackBar("This is doesn't correspond to any deck");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search_button);
        searchItem.expandActionView();
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search deck name or id");

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {

                Matcher m = p.matcher(searchView.getQuery().toString());
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
