package com.KeyforgeManagement.application.ui.search;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.data.api.Api;
import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.SingleDeckReference;
import com.KeyforgeManagement.application.data.model.wrapperMasterVault.Kmvresults;
import com.KeyforgeManagement.application.data.storage.Card.CardRepository;
import com.KeyforgeManagement.application.data.storage.Deck.DeckRepository;
import com.KeyforgeManagement.application.data.storage.DeckWithCards.DeckCardRepository;
import com.KeyforgeManagement.application.ui.decklist.DeckListAdapter;
import com.KeyforgeManagement.application.ui.decklist.DeckListInteractionListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.appcompat.widget.SearchView.OnQueryTextListener;


public class SearchActivity extends AppCompatActivity implements DeckListInteractionListener {

    private DeckListAdapter mAdapter;
    private DeckRepository deckRepository;
    private CardRepository cardRepository;
    private DeckCardRepository deckCardRepository;
    private List<String> cardList;
    List<Boolean> tempMaverick = new ArrayList<>();
    List<Boolean> tempAnomaly = new ArrayList<>();
    private int index = 0;
    ProgressBar loadingDecks;
    private Pattern p = Pattern.compile(".{8}-.{4}-.{4}-.{4}-.{12}");


    public static void start(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setSupportActionBar(findViewById(R.id.toolbar_search));

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        deckRepository = new DeckRepository(this);
        cardRepository = new CardRepository(this);
        deckCardRepository = new DeckCardRepository(this);

        loadingDecks = findViewById(R.id.progress_bar);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerViewSrc);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new DeckListAdapter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        cardList = new ArrayList<>();


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
        loadingDecks.setVisibility(View.VISIBLE);
        Api.getDecks(name).enqueue(new Callback<List<Deck>>() {
            @Override
            public void onResponse(Call<List<Deck>> call, Response<List<Deck>> response) {

                if (response.body() == null || response.body().size() == 0) {
                    showSnackBar("No decks found for your query");
                } else
                    mAdapter.onNewDecks(response.body());
                loadingDecks.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<List<Deck>> call, Throwable t) {
                loadingDecks.setVisibility(View.GONE);
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
                    deckRepository.insert(deck);
                    saveCards(deck);
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public void onLongDeckClicked(Deck deck) {

    }

    private void saveCards(Deck deck) {
        Api.getCards(deck.getKeyforgeId()).enqueue(new Callback<Kmvresults>() {

            @Override
            public void onResponse(Call<Kmvresults> call, Response<Kmvresults> response) {
                if (response.body() == null) {
                    showSnackBar("This deck has not been registered yet\n Try again later");
                    deckRepository.delete(deck);
                    return;
                }
                tempMaverick.clear();
                tempAnomaly.clear();
                List<String> legacy = response.body().getData().getSet_era_cards().getLegacy();

                response.body().get_linked().getCards().forEach(card -> {
                    tempMaverick.add(card.getIs_maverick());
                    tempAnomaly.add(card.getIs_anomaly());
                    card.setIs_anomaly(false);
                    card.setIs_legacy(false);
                    card.setIs_maverick(false);
                    cardRepository.insert(card);
                });
                cardList.clear();
                cardList.addAll(response.body().getData().get_links().getCards());

                response.body().get_linked().getCards().forEach(card -> {
                    deckCardRepository.insert(card, deck,
                            Collections.frequency(cardList, card.getId()),
                            tempMaverick.get(index), legacy.contains(card.getId()), tempAnomaly.get(index));
                    index++;

                });
                index = 0;


            }

            @Override
            public void onFailure(Call<Kmvresults> call, Throwable t) {
                showSnackBar("There has been an error while loading cards\n Try again later");
            }
        });
    }


    private void searchById(String id) {
        loadingDecks.setVisibility(View.VISIBLE);
        Api.getDeckFromId(id).enqueue(new Callback<SingleDeckReference>() {
            @Override
            public void onResponse(Call<SingleDeckReference> call, Response<SingleDeckReference> response) {
                loadingDecks.setVisibility(View.GONE);
                if (response.body() == null) {
                    showSnackBar("Bad id");
                } else {
                    List<Deck> justToMakeItWork = new ArrayList<>();
                    justToMakeItWork.add(response.body().getDeck());
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
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }

}
