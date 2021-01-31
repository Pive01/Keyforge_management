package com.KeyforgeManagement.application.ui.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.common.Utils;
import com.KeyforgeManagement.application.data.api.Api;
import com.KeyforgeManagement.application.data.api.ApiPerformer;
import com.KeyforgeManagement.application.data.model.DeckDTO;
import com.KeyforgeManagement.application.data.model.decksOfKeyforgeRequired.RequestBody;
import com.KeyforgeManagement.application.data.model.decksOfKeyforgeRequired.UserInfo;
import com.KeyforgeManagement.application.data.model.decksOfKeyforgeRequired.UserValidator;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.ResponseImport;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements DeckDTOListInteractionListener {

    private static DeckRepository repository;
    private DeckDTOListAdapter mAdapter;
    private String auth = "";
    private String usrName = "";
    private ProgressDialog dialog;
    private boolean error = false;
    private static int sortParameter = 0;
    private SwipeRefreshLayout swipeRefresh;
    private boolean compare = false;
    private List<String> toAdd;

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
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
        Intent i = new Intent(this, DetailActivity.class)
                .putExtra("deckInfo", deck);
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

    @SuppressLint("RestrictedApi")
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
        searchView.setQueryHint("@Untamed,Mass Mutation,Logos");
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
                mAdapter.filter(newText, sortParameter);
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
                compare = false;
                showDialogCredential();
                return true;
            case R.id.compare_dok:
                compare = true;
                showDialogCredential();

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
                ChartActivity.start(this);
                return true;
            case R.id.sort_by_sas:
                mAdapter.sort(0);
                sortParameter = 0;
                return true;
            case R.id.sort_by_amber:
                mAdapter.sort(1);
                sortParameter = 1;
                return true;
            case R.id.sort_by_wins:
                mAdapter.sort(2);
                sortParameter = 2;
                return true;
            case R.id.sort_by_action_count:
                mAdapter.sort(3);
                sortParameter = 3;
                return true;
            case R.id.sort_by_artifact_count:
                mAdapter.sort(4);
                sortParameter = 4;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.checkIsFirst(getApplicationContext()))
            showSnackBarMain(getString(R.string.lonely));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_HOME)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        if (compare)
            dialog.setMessage("Comparing data...");
        else
            dialog.setMessage("Importing data ...");
        dialog.show();
        Api.importDecks(new RequestBody(userName), auth).enqueue(new Callback<ResponseImport>() {
            @Override
            public void onResponse(Call<ResponseImport> call, Response<ResponseImport> response) {
                if (response.body() != null) {
                    if (compare) {
                        toAdd = new ArrayList<>();
                        List<String> tempList = mAdapter.getDecksIDs();
                        List<String> returnedList = new ArrayList<>();
                                response.body().getDecks().forEach(item->{
                                    returnedList.add(item.getKeyforgeId());
                                });

                        tempList.forEach(deckID -> {
                            if (!returnedList.contains(deckID))
                                toAdd.add(deckID);
                        });
                        dialog.hide();
                        if (tempList.size() == 0)
                            showSnackBarMain("All deck on the app are in DOK");
                        else
                            showDialogCompared();

                    } else {
                        DatabaseSaver dbs = new DatabaseSaver(getApplicationContext());
                        dbs.saveMultipleDecks(response.body().getDecks(), deckCollection -> dialog.hide());
                    }
                } else {
                    showSnackBarMain("An error occurred while getting data");
                    dialog.hide();
                }
            }

            @Override
            public void onFailure(Call<ResponseImport> call, Throwable t) {
                showSnackBarMain("An error occurred while getting data");
                dialog.hide();
            }
        });
    }

    private void showDialogCompared() {
        new AlertDialog.Builder(this)
                .setTitle("Compare decks")
                .setMessage("It seems that some decks are not in DOK, would you like to add them?")
                .setPositiveButton("OK", (dialog, id) -> uploadDecks())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void uploadDecks() {
        dialog.setMessage("Uploading...");
        dialog.show();
        error = false;
        addDecks(0);
    }

    private void abortUpload() {
        dialog.hide();
        showSnackBarMain("Something went wrong :(");
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

    private void showDialogCredential() {
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.credential_layout, null);
        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Sign in", (dialogInterface, i) -> {
                    TextInputEditText email = dialogView.findViewById(R.id.userEmail);
                    TextInputEditText psw = dialogView.findViewById(R.id.password_dok);

                    getAuthorization(email.getText().toString(), psw.getText().toString());
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void addDecks(int i) {
        if (toAdd.size()<=i || toAdd.get(i) == null) {
            return;
        }
        Api.addDeck(auth, toAdd.get(i)).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.body() || response.body() == null) {
                    abortUpload();
                } else {
                    System.out.println("####uploading "+i);
                    if(toAdd.size()<=i+1){
                        dialog.hide();
                        return;
                    }
                    addDecks(i + 1);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                abortUpload();
            }
        });
    }

    private void refreshStatus(int i) {
        if (error || mAdapter.getDeckDTOAt(i) == null) {
            swipeRefresh.setRefreshing(false);
            return;
        }
        ApiPerformer.getDecksFromId(singleDeckReference -> {
            if (singleDeckReference == null) showSnackBarMain("Error try later");
            error = singleDeckReference == null;
            repository.updateStatus(singleDeckReference.getDeck().convertToOld(), deck -> {
                if (i == mAdapter.getItemCount() - 1) {
                    swipeRefresh.setRefreshing(false);
                    return;
                }
                refreshStatus(i + 1);
            });
        }, failure -> {
            showSnackBarMain("Error try later");
            error = true;
            swipeRefresh.setRefreshing(false);
        }, mAdapter.getDeckDTOAt(i).getDeck().getKeyforgeId());
    }
}
