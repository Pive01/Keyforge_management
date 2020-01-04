package com.Keyforge_management.ui.search;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.Keyforge_management.R;
import com.Keyforge_management.data.api.Api;
import com.Keyforge_management.data.model.Card;
import com.Keyforge_management.data.model.Deck;
import com.Keyforge_management.data.model.wrapper.Kmvresults;
import com.Keyforge_management.data.storage.Card.CardRepository;
import com.Keyforge_management.data.storage.Deck.DeckRepository;
import com.Keyforge_management.data.storage.DeckWithCards.DeckCardRepository;
import com.Keyforge_management.ui.decklist.DeckListAdapter;
import com.Keyforge_management.ui.decklist.DeckListInteractionListener;

import java.util.List;

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

    private int count = 1;
    private int iteraction = 0;
    private Boolean isFirst = true;
    private Card buff = new Card("hope_this_name_will_never_exist");
    private DeckListAdapter mAdapter;
    private DeckRepository deckRepository;
    private CardRepository cardRepository;
    private DeckCardRepository deckCardRepository;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setSupportActionBar(findViewById(R.id.toolbar_search));

        ImageButton backBtn = findViewById(R.id.backBtnSrc);
        backBtn.setOnClickListener(v -> finish());

        deckRepository = new DeckRepository(this);
        cardRepository = new CardRepository(this);
        deckCardRepository = new DeckCardRepository(this);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerViewSrc);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new DeckListAdapter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void displayDecks(String name) {
        ProgressBar loadingDecks = findViewById(R.id.progress_bar);
        loadingDecks.setVisibility(View.VISIBLE);
        Api.getDecks(name).enqueue(new Callback<List<Deck>>() {
            @Override
            public void onResponse(Call<List<Deck>> call, Response<List<Deck>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                loadingDecks.setVisibility(View.GONE);
                mAdapter.onNewDecks(response.body());

            }

            @Override
            public void onFailure(Call<List<Deck>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDeckClicked(Deck deck) {
        System.out.println(deck.toString());
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
                if (!(response.body() == null)) {
                    response.body().get_linked().getCards().forEach(card -> {
                        cardRepository.insert(card);
                    });

                    response.body().get_linked().getCards().forEach(card -> {
                        iteraction++;
                        if (card.getCard_title().equals(buff.getCard_title()))
                            count++;
                        else {
                            if (!isFirst) {
                                deckCardRepository.insert(buff, deck, count);
                            }
                            buff = card;
                            count = 1;
                            isFirst = false;
                        }
                        if (iteraction == 36)
                            deckCardRepository.insert(buff, deck, count);
                    });

                }
            }


            @Override
            public void onFailure(Call<Kmvresults> call, Throwable t) {

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

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
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
}
