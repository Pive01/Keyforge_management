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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.data.api.Api;
import com.KeyforgeManagement.application.data.model.Card;
import com.KeyforgeManagement.application.data.model.CardMetadataDTO;
import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.model.DeckDTO;
import com.KeyforgeManagement.application.data.model.House;
import com.KeyforgeManagement.application.data.model.wrapperMasterVault.Kmvresults;
import com.KeyforgeManagement.application.data.storage.DatabaseSaver;
import com.KeyforgeManagement.application.data.storage.Deck.DeckRepository;
import com.KeyforgeManagement.application.data.storage.stats.StatsRepository;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.KeyforgeManagement.application.common.Utils.absolute;


public class DetailActivity extends AppCompatActivity {

    private static DeckDTO deckDTO;
    private DeckRepository repository;
    private TextView winsView;
    private TextView lossesView;
    private CustomViewPager viewPager;
    private final HashMap<House, List<Card>> map = new HashMap<>();
    private static DatabaseSaver dbs;
    private ProgressDialog dialog;

    public static void start(Context context, Intent i) {
        context.startActivity(new Intent(context, DetailActivity.class));
        deckDTO = (DeckDTO) i.getSerializableExtra("deckInfo");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar infoToolbar = findViewById(R.id.deck_info_toolbar);
        infoToolbar.setTitle(deckDTO.getDeck().getName());

        setSupportActionBar(infoToolbar);

        dbs = new DatabaseSaver(this);

        dialog = new ProgressDialog(DetailActivity.this);
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .build();
            customTabsIntent.launchUrl(this, Uri.parse("https://decksofkeyforge.com/about/sas"));

        });
        repository = new DeckRepository(this);

        assembleData();

        BarChart chart = findViewById(R.id.barchart);
        BarChartImplementer chartImplementer = new BarChartImplementer(chart, StatsRepository.get(this),
                "Sas Ratings");
        chartImplementer.createSasBarChart(deckDTO.getDeck().getSasRating());
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                getShareIntent();
                return true;
            case R.id.menu_item_refresh_cards:
                downloadCards();
                return true;
            default:
                finish();
                return true;
        }
    }

    private void initializeTextViews() {

        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);

        DecimalFormat noDot = new DecimalFormat("#");
        noDot.setRoundingMode(RoundingMode.CEILING);

        Deck deck = deckDTO.getDeck();
        TextView power = findViewById(R.id.deck_power_txt);
        TextView chain = findViewById(R.id.deck_chain_txt);
        TextView winloss = findViewById(R.id.deck_winandloss_txt);
        power.setText(String.valueOf(deck.getPowerLevel()));
        chain.setText(String.valueOf(deck.getChains()));
        winloss.setText((deck.getWins()) + " / " + deck.getLosses());
        TextView amberCntrl = findViewById(R.id.txtView_amber_control);
        TextView expectedAmber = findViewById(R.id.txtView_expected_amber);
        TextView creatureProtection = findViewById(R.id.txtView_creature_protection);
        TextView artifactControl = findViewById(R.id.txtView_artifact_control);
        TextView creatureControl = findViewById(R.id.txtView_creature_control);
        TextView effectivePower = findViewById(R.id.txtView_effective_power);
        TextView efficiency = findViewById(R.id.txtView_efficiency);
        TextView disruption = findViewById(R.id.txtView_disruption);
        TextView amber = findViewById(R.id.txtView_amber);
        TextView keyCheat = findViewById(R.id.txtView_key_cheat);
        TextView archive = findViewById(R.id.txtView_archive);
        TextView aerc = findViewById(R.id.base_aerc);
        TextView synergy = findViewById(R.id.synegy);
        TextView antisynergy = findViewById(R.id.antisynegy);
        TextView action = findViewById(R.id.txtView_action);
        TextView creature = findViewById(R.id.txtView_creature);
        TextView artifact = findViewById(R.id.txtView_artifact);
        TextView upgrade = findViewById(R.id.txtView_upgrade);
        action.setText(String.valueOf(df.format(deck.getActionCount())));
        creature.setText(String.valueOf(df.format(deck.getCreatureCount())));
        artifact.setText(String.valueOf(df.format(deck.getArtifactCount())));
        upgrade.setText(String.valueOf(df.format(deck.getUpgradeCount())));
        amberCntrl.setText(String.valueOf(df.format(deck.getAmberControl())));
        expectedAmber.setText(String.valueOf(noDot.format(deck.getExpectedAmber())));
        creatureProtection.setText(String.valueOf(df.format(deck.getCreatureProtection())));
        artifactControl.setText(String.valueOf(df.format(deck.getArtifactControl())));
        creatureControl.setText(String.valueOf(df.format(deck.getCreatureControl())));
        effectivePower.setText(String.valueOf(df.format(deck.getEffectivePower())));
        efficiency.setText(String.valueOf(df.format(deck.getEfficiency())));
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
        repository.updateWins(deckDTO.getDeck().getLocalWins(), deckDTO.getDeck().getId());
        updateView();
    }

    private void updateLosses() {
        repository.updateLosses(deckDTO.getDeck().getLocalLosses(), deckDTO.getDeck().getId());
        updateView();
    }

    private void updateView() {
        winsView.setText(String.valueOf(deckDTO.getDeck().getLocalWins()));
        lossesView.setText(String.valueOf(deckDTO.getDeck().getLocalLosses()));
        adjustSize(winsView);
        adjustSize(lossesView);
    }

    private void initializeButtons(Button addWin, Button addLoss,
                                   Button removeLoss, Button removeWin) {
        addLoss.setOnClickListener(v -> {
            deckDTO.getDeck().setLocalLosses((deckDTO.getDeck().getLocalLosses() + 1));
            updateLosses();
        });
        addWin.setOnClickListener(v -> {
            deckDTO.getDeck().setLocalWins((deckDTO.getDeck().getLocalWins() + 1));
            updateWins();

        });
        removeWin.setOnClickListener(v -> {
            deckDTO.getDeck().setLocalWins(absolute((deckDTO.getDeck().getLocalWins() - 1)));
            updateWins();
        });
        removeLoss.setOnClickListener(v -> {
            deckDTO.getDeck().setLocalLosses(absolute((deckDTO.getDeck().getLocalLosses() - 1)));
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

    private void assembleData() {
        if (deckDTO.getCards() == null || deckDTO.getCards().size() == 0) {
            downloadCards();
            return;
        }

        List<Card> temp = new ArrayList<>();
        List<CardMetadataDTO> cardRefList = deckDTO.getCards();

        cardRefList.forEach(cardMetadataDTO -> {
            Card refCard;
            refCard = cardMetadataDTO.getCard();
            refCard.setIs_anomaly(cardMetadataDTO.getCardsDeckRef().getIs_anomaly());
            refCard.setIs_maverick(cardMetadataDTO.getCardsDeckRef().getIs_maverick());
            refCard.setIs_legacy(cardMetadataDTO.getCardsDeckRef().getIs_legacy());
            refCard.setIs_enhanced(cardMetadataDTO.getCardsDeckRef().getIs_enhanced());
            for (int i = 0; i < cardMetadataDTO.getCardsDeckRef().getCount(); i++)
                temp.add(refCard);

        });
        getCards(temp);
    }

    private void downloadCards() {

        dialog.setMessage("Downloading cards");
        dialog.show();
        Api.getCards(deckDTO.getDeck().getKeyforgeId()).enqueue(new Callback<Kmvresults>() {
            @Override
            public void onResponse(Call<Kmvresults> call, Response<Kmvresults> response) {

                if (response.body() == null) {
                    dialog.hide();
                    onBackPressed();
                    return;
                }

                dbs.trySaveCards(response.body(), deckDTO.getDeck(), collection -> {
                    dialog.hide();
                    refreshDeckCards();
                });
            }

            @Override
            public void onFailure(Call<Kmvresults> call, Throwable t) {
                dialog.hide();
            }
        });
    }

    private void refreshDeckCards() {
        repository.getDeckDTO(deckDTO.getDeck().getId()).observe(this, this::adjustObj);
    }

    private void adjustObj(DeckDTO x) {
        deckDTO = x;
        assembleData();
    }

    private void getShareIntent() {
        String basePath = "https://www.keyforgegame.com/deck-details/";
        String shareBody = "Take a look at this deck!\n" + basePath + deckDTO.getDeck().getKeyforgeId();
        Intent sharingIntent = new Intent(Intent.ACTION_SEND)
                .setType("text/html")
                .putExtra(Intent.EXTRA_SUBJECT, "sample")
                .putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    private void adjustSize(TextView item) {
        if (Integer.parseInt(item.getText().toString()) >= 10)
            item.setTextSize(20);
        else
            item.setTextSize(38);
    }

    @Override
    protected void onPause() {
        dialog.dismiss();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        dialog.dismiss();
        super.onDestroy();
    }
}

