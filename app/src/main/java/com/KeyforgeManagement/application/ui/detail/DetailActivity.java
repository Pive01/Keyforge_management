package com.KeyforgeManagement.application.ui.detail;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.data.api.Api;
import com.KeyforgeManagement.application.data.model.Card;
import com.KeyforgeManagement.application.data.model.CardsDeckRef;
import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.model.House;
import com.KeyforgeManagement.application.data.model.Stats;
import com.KeyforgeManagement.application.data.model.wrapperMasterVault.Kmvresults;
import com.KeyforgeManagement.application.data.storage.DatabaseSaver;
import com.KeyforgeManagement.application.data.storage.Deck.DeckRepository;
import com.KeyforgeManagement.application.data.storage.DeckWithCards.DeckCardRepository;
import com.KeyforgeManagement.application.ui.charts.BarChartImplementer;
import com.KeyforgeManagement.application.ui.detail.fragments.CardFragmentAdapter;
import com.KeyforgeManagement.application.ui.detail.fragments.CustomViewPager;
import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.snackbar.Snackbar;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.KeyforgeManagement.application.common.Utils.absolute;


public class DetailActivity extends AppCompatActivity {

    private static Deck deck;
    private static Stats statistic;
    private DeckRepository repository;
    private DeckCardRepository deckCardRepository;
    private TextView winsView;
    private TextView lossesView;
    private CustomViewPager viewPager;
    private final HashMap<House, List<Card>> map = new HashMap<>();
    private List<CardsDeckRef> refList;
    private static DatabaseSaver dbs;
    private ProgressDialog dialog;

    public static void start(Context context, Intent i) {
        context.startActivity(new Intent(context, DetailActivity.class));
        deck = (Deck) i.getSerializableExtra("deckInfo");
        statistic = (Stats) i.getSerializableExtra("stats");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar infoToolbar = findViewById(R.id.deck_info_toolbar);
        infoToolbar.setTitle(deck.getName());

        setSupportActionBar(infoToolbar);

        dbs = new DatabaseSaver(this);

        dialog = new ProgressDialog(DetailActivity.this);
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        refList = new ArrayList<>();

        winsView = findViewById(R.id.winsCounter);
        lossesView = findViewById(R.id.lossCounter);
        Button addWin = findViewById(R.id.addWins);
        Button addLoss = findViewById(R.id.addLoss);
        Button removeWin = findViewById(R.id.removeWins);
        Button removeLoss = findViewById(R.id.removeLoss);
        initializeTextViews();
        updateView();
        initializeButtons(addWin, addLoss, removeLoss, removeWin);


        ImageButton infoLogo = findViewById(R.id.infobutton);
        infoLogo.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://decksofkeyforge.com/about/sas"));
            startActivity(browserIntent);
        });
        repository = new DeckRepository(this);

        deckCardRepository = new DeckCardRepository(getApplicationContext());
        getInfoCards();

        BarChart chart = findViewById(R.id.barchart);
        BarChartImplementer chartImplementer = new BarChartImplementer(chart, statistic,
                "Sas Ratings");
        chartImplementer.createSasBarChart(deck.getSasRating());
    }

    private void initializeTextViews() {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);

        DecimalFormat noDot = new DecimalFormat("#");
        noDot.setRoundingMode(RoundingMode.CEILING);

        TextView power = findViewById(R.id.deck_power_txt);
        TextView chain = findViewById(R.id.deck_chain_txt);
        TextView winloss = findViewById(R.id.deck_winandloss_txt);
        power.setText(String.valueOf(deck.getPowerLevel()));
        chain.setText(String.valueOf(deck.getChains()));
        winloss.setText((deck.getWins()) + " / " + deck.getLosses());
        TextView amberCntrl = findViewById(R.id.txtView_amber_control);
        TextView expectedAmber = findViewById(R.id.txtView_expected_amber);
        TextView amberProtection = findViewById(R.id.txtView_amber_protection);
        TextView artifactControl = findViewById(R.id.txtView_artifact_control);
        TextView creatureControl = findViewById(R.id.txtView_creature_control);
        TextView effectivePower = findViewById(R.id.txtView_effective_power);
        TextView houseCheating = findViewById(R.id.txtView_house_cheating);
        TextView disruption = findViewById(R.id.txtView_disruption);
        TextView amber = findViewById(R.id.txtView_amber);
        TextView keyCheat = findViewById(R.id.txtView_key_cheat);
        TextView archive = findViewById(R.id.txtView_archive);
        TextView aerc = findViewById(R.id.base_aerc);
        TextView synergy = findViewById(R.id.synegy);
        TextView antisynergy = findViewById(R.id.antisynegy);
        amberCntrl.setText(String.valueOf(df.format(deck.getAmberControl())));
        expectedAmber.setText(String.valueOf(noDot.format(deck.getExpectedAmber())));
        amberProtection.setText(String.valueOf(df.format(deck.getAmberProtection())));
        artifactControl.setText(String.valueOf(df.format(deck.getArtifactControl())));
        creatureControl.setText(String.valueOf(df.format(deck.getCreatureControl())));
        effectivePower.setText(String.valueOf(df.format(deck.getEffectivePower())));
        houseCheating.setText(String.valueOf(df.format(deck.getHouseCheating())));
        disruption.setText(String.valueOf(df.format(deck.getDisruption())));
        amber.setText(String.valueOf(df.format(deck.getRawAmber())));
        keyCheat.setText(String.valueOf(df.format(deck.getKeyCheatCount())));
        archive.setText(String.valueOf(df.format(deck.getCardArchiveCount())));
        aerc.setText(String.valueOf(noDot.format(deck.getAercScore())));
        synergy.setText("+ " + noDot.format(deck.getSynergyRating()));
        antisynergy.setText("- " + noDot.format(deck.getAntisynergyRating()));

        ImageView expansionIcon = findViewById(R.id.expansion_img);
        expansionIcon.setImageResource(deck.getExpansion().getImageExpId());


    }

    private void updateWins() {
        repository.updateWins(deck.getLocalWins(), deck.getId());
        updateView();
    }

    private void updateLosses() {
        repository.updateLosses(deck.getLocalLosses(), deck.getId());
        updateView();
    }

    private void updateView() {
        winsView.setText(String.valueOf(deck.getLocalWins()));
        lossesView.setText(String.valueOf(deck.getLocalLosses()));
        adjustSize(winsView);
        adjustSize(lossesView);
    }

    private void initializeButtons(Button addWin, Button addLoss,
                                   Button removeLoss, Button removeWin) {
        addLoss.setOnClickListener(v -> {
            deck.setLocalLosses((deck.getLocalLosses() + 1));
            updateLosses();
        });
        addWin.setOnClickListener(v -> {
            deck.setLocalWins((deck.getLocalWins() + 1));
            updateWins();

        });
        removeWin.setOnClickListener(v -> {
            deck.setLocalWins(absolute((deck.getLocalWins() - 1)));
            updateWins();
        });
        removeLoss.setOnClickListener(v -> {
            deck.setLocalLosses(absolute((deck.getLocalLosses() - 1)));
            updateLosses();
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void getCards(List<Card> cardToShow) {
        if (cardToShow.isEmpty()) {
            Snackbar.make(
                    findViewById(R.id.mainDetailLayout),
                    "Error while loading cards...Try delete and re-add this deck", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", view -> {
                    })
                    .setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                    .show();
            return;
        }
        House[] houseArr = new House[3];

        cardToShow.sort(Comparator.comparing(Card::getHouse));
        houseArr[0] = cardToShow.get(0).getHouse();
        houseArr[1] = cardToShow.get(12).getHouse();
        houseArr[2] = cardToShow.get(24).getHouse();

        map.put(houseArr[0], new ArrayList<>(cardToShow.subList(0, 12)));
        map.put(houseArr[1], new ArrayList<>(cardToShow.subList(12, 24)));
        map.put(houseArr[2], new ArrayList<>(cardToShow.subList(24, 36)));

        viewPager = findViewById(R.id.viewpager);
        viewPager.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                viewPager.getParent().requestDisallowInterceptTouchEvent(true);
            }
        });
        viewPager.setAdapter(new CardFragmentAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, map, houseArr));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void assembleData(List<Card> cardList) {
        List<Card> temp = new ArrayList<>();

        if (cardList == null || cardList.size() == 0) {
            downloadCards();
            return;
        }
        cardList.forEach(reference -> refList.forEach(value -> {
            if (reference.getId().equals(value.getCardId())) {
                reference.setIs_maverick(value.getIs_maverick());
                reference.setIs_legacy(value.getIs_legacy());
                reference.setIs_anomaly(value.getIs_anomaly());
                for (int i = 0; i < value.getCount(); i++)
                    temp.add(reference);
            }
        }));

        getCards(temp);
    }

    private void getInfoCards() {
        deckCardRepository.getInfoForCards(deck).observe(this, this::support);
    }

    private void downloadCards() {

        dialog.setMessage("Downloading cards");
        dialog.show();
        Api.getCards(deck.getKeyforgeId()).enqueue(new Callback<Kmvresults>() {
            @Override
            public void onResponse(Call<Kmvresults> call, Response<Kmvresults> response) {

                if (response.body() == null) {
                    dialog.hide();
                    onBackPressed();
                    return;
                }

                dbs.trySaveCards(response.body(), deck, collection -> {
                    dialog.hide();
                    getInfoCards();
                });

            }

            @Override
            public void onFailure(Call<Kmvresults> call, Throwable t) {
                dialog.hide();
            }
        });
    }

    private void support(List<CardsDeckRef> cardList) {
        refList.clear();
        refList.addAll(cardList);
        deckCardRepository.getCards(deck).observe(this, this::assembleData);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void getShareIntent() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        String BASE_PATH = "https://www.keyforgegame.com/deck-details/";
        String shareBody = "Take a look at this deck!\n" + BASE_PATH + deck.getKeyforgeId();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "sample");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_share)
            getShareIntent();
        else finish();
        return true;
    }

    private void adjustSize(TextView item) {
        if (Integer.parseInt(item.getText().toString()) >= 10)
            item.setTextSize(20);
        else
            item.setTextSize(38);
    }
}

