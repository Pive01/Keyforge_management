package com.KeyforgeManagement.application.ui.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.data.api.Api;
import com.KeyforgeManagement.application.data.model.DeckDTO;
import com.KeyforgeManagement.application.data.model.Stats;
import com.KeyforgeManagement.application.data.model.decksOfKeyforgeRequired.RequestBody;
import com.KeyforgeManagement.application.data.model.decksOfKeyforgeRequired.UserInfo;
import com.KeyforgeManagement.application.data.model.decksOfKeyforgeRequired.UserValidator;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.ResponseImport;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.SingleDeckReference;
import com.KeyforgeManagement.application.data.storage.DatabaseSaver;
import com.KeyforgeManagement.application.data.storage.Deck.DeckRepository;
import com.KeyforgeManagement.application.ui.charts.ChartActivity;
import com.KeyforgeManagement.application.ui.deckDTOlist.DeckDTOListAdapter;
import com.KeyforgeManagement.application.ui.deckDTOlist.DeckDTOListInteractionListener;
import com.KeyforgeManagement.application.ui.deckDTOlist.ListPaddingDecoration;
import com.KeyforgeManagement.application.ui.detail.DetailActivity;
import com.KeyforgeManagement.application.ui.main.information.Information;
import com.KeyforgeManagement.application.ui.search.SearchActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements DeckDTOListInteractionListener {

    private static DeckRepository repository;
    private DeckDTOListAdapter mAdapter;
    private static Stats statistics;
    private String auth = "";
    private String usrName = "";
    private ProgressDialog dialog;
    private boolean error = false;
    private static int sortParamenter = 0;
    private SwipeRefreshLayout swipeRefresh;


    public static void start(Context context, Intent i) {
        context.startActivity(new Intent(context, MainActivity.class));
        statistics = (Stats) i.getSerializableExtra("stats");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);

        View fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> SearchActivity.start(this));

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(() -> {
            error = false;
            refreshStatus(0);
        });

        RecyclerView mRecyclerView = findViewById(R.id.decksRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new ListPaddingDecoration(getApplicationContext()));


        mAdapter = new DeckDTOListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);


        repository = new DeckRepository(this);
        repository.getAllDecksDTO().observe(this, mAdapter::onNewDecks);
    }

    @Override
    public void onDeckClicked(DeckDTO deck) {
        System.out.println(deck.getDeck().toString());
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("stats", statistics);
        i.putExtra("deckInfo", deck);
        DetailActivity.start(this, i);

    }

    @Override
    public void onLongDeckClicked(DeckDTO deck) {
        new AlertDialog.Builder(this)
                .setTitle("Remove a deck")
                .setMessage("Are you sure you want to remove this deck?")
                .setPositiveButton(android.R.string.yes, (dialog, which) ->
                        repository.delete(deck.getDeck())
                )
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search_deck_mylist);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("@Untamed,Mass_Mutation,Logos");
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
                Log.i("Shit", "Text submitted");
                if (newText.length() > 0 && newText.charAt(0) == '@')
                    mAdapter.advancedFilter(newText, sortParamenter);
                else
                    mAdapter.filter(newText, sortParamenter);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_decks:
                return true;
            case R.id.action_about_us:
                Information.start(this);
                return true;
            case R.id.import_dok:
                showDialog();
                return true;
            case R.id.graph_charts:
                if (mAdapter.getItemCount() <= 0) {
                    Snackbar.make(
                            findViewById(R.id.activity_main_parent_layout),
                            "Add some decks before", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", view -> {

                            })
                            .setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                            .show();
                    return true;
                }
                Intent i = new Intent(this, ChartActivity.class);
                i.putExtra("stats", statistics);
                ChartActivity.start(this, i);
                return true;

            case R.id.sort_by_sas:
                mAdapter.sort(0);
                sortParamenter = 0;
                return true;
            case R.id.sort_by_amber:
                mAdapter.sort(1);
                sortParamenter = 1;
                return true;
            case R.id.sort_by_wins:
                mAdapter.sort(2);
                sortParamenter = 2;
                return true;
            case R.id.sort_by_action_count:
                mAdapter.sort(3);
                sortParamenter = 3;
                return true;
            case R.id.sort_by_artifact_count:
                mAdapter.sort(4);
                sortParamenter = 4;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean checkIsFirst() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (!preferences.contains("isFirstAccess")) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstAccess", true);
            editor.apply();
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkIsFirst())
            showSnackBarMain(getString(R.string.lonely));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void getAuthorization(String email, String psw) {
        dialog.setMessage("Getting Authorization ...");
        dialog.show();

        Api.getAuthorization(new UserValidator(email, psw)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.headers().get("Authorization") == null) {
                    dialog.hide();
                    showSnackBarMain("Invalid credential");
                }

                auth = response.headers().get("Authorization");
                dialog.hide();
                getUserName();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showSnackBarMain("There has been an error");
                dialog.hide();
            }
        });
    }

    private void getUserName() {
        dialog.setMessage("Getting UserName ...");
        dialog.show();
        Api.getUserName(auth).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.body() == null) {
                    showSnackBarMain("There has been an error");
                    dialog.hide();
                    return;
                }
                usrName = response.body().getUsername();
                dialog.hide();
                importDok(usrName);
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                showSnackBarMain("There has been an error");
                dialog.hide();
            }
        });
    }

    private void importDok(String userName) {
        dialog.setMessage("Importing data ...");
        dialog.show();
        Api.importDecks(new RequestBody(userName), auth).enqueue(new Callback<ResponseImport>() {
            @Override
            public void onResponse(Call<ResponseImport> call, Response<ResponseImport> response) {
                if (response.body() != null) {
                    DatabaseSaver dbs = new DatabaseSaver(getApplicationContext());
                    dbs.saveMultipleDecks(response.body().getDecks(), deckCollection -> dialog.hide());
                } else {
                    showSnackBarMain("An error occurred while importing data");
                    dialog.hide();
                }
            }

            @Override
            public void onFailure(Call<ResponseImport> call, Throwable t) {
                showSnackBarMain("An error occurred while importing data");
                dialog.hide();
            }
        });
    }

    private void showSnackBarMain(String s) {
        Snackbar.make(
                findViewById(R.id.activity_main_parent_layout),
                s, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", view -> {
                })
                .setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                .show();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.credential_layout, null);
        builder.setView(dialogView);
        builder.setPositiveButton("Sign in", (dialogInterface, i) -> {
            TextInputEditText email = dialogView.findViewById(R.id.userEmail);
            TextInputEditText psw = dialogView.findViewById(R.id.password_dok);

            getAuthorization(email.getText().toString(), psw.getText().toString());
        });
        builder.setNegativeButton("Cancel", null);

        builder.show();
    }

    private void refreshStatus(int i) {
        if (error || mAdapter.getDeckDTOAt(i) == null) {
            swipeRefresh.setRefreshing(false);
            return;
        }
        Api.getDeckFromId(mAdapter.getDeckDTOAt(i).getDeck().getKeyforgeId()).enqueue(new Callback<SingleDeckReference>() {
            @Override
            public void onResponse(Call<SingleDeckReference> call, Response<SingleDeckReference> response) {
                if (response.body() == null) {
                    showSnackBarMain("Error try later");
                    error = true;
                }
                error = false;

                repository.updateStatus(response.body().getDeck().convertToOld(), deck -> {
                    if (i == mAdapter.getItemCount() - 1) {
                        swipeRefresh.setRefreshing(false);
                        return;
                    }

                    refreshStatus(i + 1);
                });
            }

            @Override
            public void onFailure(Call<SingleDeckReference> call, Throwable t) {
                showSnackBarMain("Error try later");
                error = true;
                swipeRefresh.setRefreshing(false);
            }
        });
    }
}
