package com.Keyforge_management.ui.search;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.Keyforge_management.R;
import com.Keyforge_management.data.api.Api;
import com.Keyforge_management.data.model.Deck;
import com.Keyforge_management.data.storage.DeckRepository;
import com.Keyforge_management.ui.decklist.DeckListAdapter;
import com.Keyforge_management.ui.decklist.DeckListInteractionListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity implements DeckListInteractionListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Deck> resultsList;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ImageButton backBtn = findViewById(R.id.backBtnSrc);
        backBtn.setOnClickListener(v -> finish());

        EditText searchBar = findViewById(R.id.SearchEditText);
        ImageButton search = findViewById(R.id.srcBtn);

        search.setOnClickListener(v -> displayDecks(searchBar.getText().toString()));

        resultsList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recyclerViewSrc);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new DeckListAdapter(resultsList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void displayDecks(String name) {
        Api.getDecks(name).enqueue(new Callback<List<Deck>>() {
            @Override
            public void onResponse(Call<List<Deck>> call, Response<List<Deck>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                resultsList.clear();
                resultsList.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Deck>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDeckClicked(Deck deck) {
        new AlertDialog.Builder(this)
                .setTitle("Add a deck")
                .setMessage("Are you sure you want to add this deck?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DeckRepository deckRepository = new DeckRepository(SearchActivity.this);
                        deckRepository.insert(deck);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
}
