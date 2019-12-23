package com.Keyforge_management;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.Keyforge_management.decksofkeyforge.ApiResponse;
import com.Keyforge_management.decksofkeyforge.Deck;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ImageButton backBtn = findViewById(R.id.backBtnSrc);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EditText searchBar = findViewById(R.id.SearchEditText);
        ImageButton search = findViewById(R.id.srcBtn);
        final TextView test = findViewById(R.id.textView);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDecks(searchBar.getText().toString(), test);

            }
        });


    }

    private void displayDecks(String name, TextView test) {
        Retrofit testStuff = new Retrofit.Builder()
                .baseUrl("https://decksofkeyforge.com/api/decks/by-name/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiResponse testApi = testStuff.create(ApiResponse.class);

        Call<List<Deck>> call = testApi.getData(name);

        call.enqueue(new Callback<List<Deck>>() {
            @Override
            public void onResponse(Call<List<Deck>> call, Response<List<Deck>> response) {
                if (!response.isSuccessful()) {
                    test.setText("Code: " + response.code());
                    return;
                }

                List<Deck> actualDecks = response.body();
                for (Deck deck : actualDecks) {
                    String content = "";
                    content += "ID: " + deck.getId() + "\n";
                    content += "Name: " + deck.getName() + "\n";
                    content += "Sas: " + deck.getSasRating() + "\n";
                    content += "amber: " + deck.getRawAmber() + "\n\n";

                    test.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Deck>> call, Throwable t) {
                test.setText(t.getMessage());
            }
        });
    }
}
