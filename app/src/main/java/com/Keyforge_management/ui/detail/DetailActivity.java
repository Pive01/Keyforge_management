package com.Keyforge_management.ui.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.Keyforge_management.R;
import com.Keyforge_management.data.model.Card;
import com.Keyforge_management.data.model.CardsDeckRef;
import com.Keyforge_management.data.model.Deck;
import com.Keyforge_management.data.model.House;
import com.Keyforge_management.data.model.Stats;
import com.Keyforge_management.data.storage.Deck.DeckRepository;
import com.Keyforge_management.data.storage.DeckWithCards.DeckCardRepository;
import com.Keyforge_management.ui.charts.BarChartImplementer;
import com.Keyforge_management.ui.detail.Fragments.CardFragmentAdapter;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.Keyforge_management.common.Utils.absolute;


public class DetailActivity extends AppCompatActivity {

    private static Deck deck;
    private static Stats statistic;
    private DeckRepository repository;
    private DeckCardRepository deckCardRepository;
    private TextView winsView;
    private TextView lossesView;
    private ViewPager viewPager;
    private HashMap<House, List<Card>> map = new HashMap<>();
    private List<CardsDeckRef> refList;


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

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        refList = new ArrayList<>();

        winsView = findViewById(R.id.winsCounter);
        lossesView = findViewById(R.id.lossCounter);
        Button addWin = findViewById(R.id.addWins);
        Button addLoss = findViewById(R.id.addLoss);
        Button removeWin = findViewById(R.id.removeWins);
        Button removeLoss = findViewById(R.id.removeLoss);
        inizializeTextViews();
        updateView();
        inizializeButtons(addWin, addLoss, removeLoss, removeWin);

        repository = new DeckRepository(this);

        deckCardRepository = new DeckCardRepository(getApplicationContext());
        deckCardRepository.getInfoForCards(deck).observe(this, this::support);

        BarChart chart = findViewById(R.id.barchart);
        BarChartImplementer chartImplementer = new BarChartImplementer(chart, statistic,
                "Sas Ratings");
        chartImplementer.createSasBarChart(deck.getSasRating());
    }

    private void inizializeTextViews() {
        TextView power = findViewById(R.id.deck_power_txt);
        TextView chain = findViewById(R.id.deck_chain_txt);
        TextView winloss = findViewById(R.id.deck_winandloss_txt);
        TextView actionCount = findViewById(R.id.action_count_value);
        TextView artifactCount = findViewById(R.id.artifact_count_value);
        TextView creatureCount = findViewById(R.id.creature_count_value);
        TextView upgradeCount = findViewById(R.id.upgrade_count_value);
        TextView totalPower = findViewById(R.id.totalpower_count_value);
        TextView totalarmor = findViewById(R.id.totalarmor_count_value);
        power.setText(String.valueOf(deck.getPowerLevel()));
        chain.setText(String.valueOf(deck.getChains()));
        winloss.setText((deck.getWins()) + " / " + deck.getLosses());
        actionCount.setText(String.valueOf(deck.getActionCount()));
        artifactCount.setText(String.valueOf(deck.getArtifactCount()));
        creatureCount.setText(String.valueOf(deck.getCreatureCount()));
        upgradeCount.setText(String.valueOf(deck.getUpgradeCount()));
        totalPower.setText(String.valueOf(deck.getTotalPower()));
        totalarmor.setText(String.valueOf(deck.getTotalArmor()));
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
    }

    private void inizializeButtons(Button addWin, Button addLoss,
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
        House[] houseArr = new House[3];
        int start = 0;
        int j = 1;
        cardToShow.sort(Comparator.comparing(Card::getHouse));
        List<Card> temp;

        for (int i = 0; i < 3; i++) {
            while ((cardToShow.size() != j + 1)
                    && cardToShow.get(j).getHouse().equals(cardToShow.get(j + 1).getHouse())) {
                j++;
            }
            houseArr[i] = cardToShow.get(j).getHouse();
            temp = new ArrayList<>(cardToShow.subList(start, j + 1));
            map.put(houseArr[i], temp);
            start = j + 1;
            j++;
        }
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

        cardList.forEach(reference -> {
            refList.forEach(value -> {
                if (reference.getId().equals(value.getCardId())) {
                    reference.setIs_maverick(value.getIs_maverick());
                    reference.setIs_legacy(value.getIs_legacy());
                    reference.setIs_anomaly(value.getIs_anomaly());
                    for (int i = 0; i < value.getCount(); i++)
                        temp.add(reference);
                }
            });
        });

        getCards(temp);
    }

    private void support(List<CardsDeckRef> cardList) {
        refList.addAll(cardList);
        deckCardRepository.getCards(deck).observe(this, this::assembleData);
    }

}
