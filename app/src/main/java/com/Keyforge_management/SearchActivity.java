package com.Keyforge_management;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.Keyforge_management.decksofkeyforge.Api;
import com.Keyforge_management.decksofkeyforge.Deck;
import com.Keyforge_management.decksofkeyforge.DeckAdapter;
import com.Keyforge_management.decksofkeyforge.DeckUI;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<DeckUI> resultsList;

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
        mAdapter = new DeckAdapter(resultsList);

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

                List<Deck> actualDecks = response.body();
                for (Deck deck : actualDecks) {
                    fillRV(deck, resultsList);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Deck>> call, Throwable t) {

            }
        });
    }

    private void fillRV(Deck deck, ArrayList<DeckUI> resultsList) {

        int[] imgHouses = new int[deck.getHouses().length];
        for (int i = 0; i < imgHouses.length; i++) {
            imgHouses[i] = deck.getHouses()[i].imageId;
        }

        resultsList.add(new DeckUI(deck.getName(), deck.getExpansion(),
                deck.getSasRating(), deck.getRawAmber(), imgHouses));
    }
}
