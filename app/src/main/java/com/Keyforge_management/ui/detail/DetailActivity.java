package com.Keyforge_management.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.Keyforge_management.R;
import com.Keyforge_management.data.model.Card;
import com.Keyforge_management.data.model.Deck;
import com.Keyforge_management.data.storage.Card.CardRepository;
import com.Keyforge_management.data.storage.Deck.DeckRepository;
import com.Keyforge_management.data.storage.DeckWithCards.DeckCardRepository;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.Keyforge_management.common.Utils.absolute;


public class DetailActivity extends AppCompatActivity {

    private static Deck deck;
    private List<Card> cardsList;
    private DeckRepository repository;
    private DeckCardRepository deckCardRepository;
    private CardRepository cardRepository;
    private TextView winsView;
    private TextView lossesView;
    private TextView cards;


    public static void start(Context context, Intent i) {
        context.startActivity(new Intent(context, DetailActivity.class));
        deck = (Deck) i.getSerializableExtra("deckInfo");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        cards = findViewById(R.id.showCards);

        deckCardRepository = new DeckCardRepository(this);
        deckCardRepository.getCards(deck).observe(this, this::showCards);
        ImageButton backBtn = findViewById(R.id.backBtnDetail);
        backBtn.setOnClickListener(v -> finish());

        Toolbar infoToolbar = findViewById(R.id.deck_info_toolbar);
        infoToolbar.setTitle(deck.getName());
        winsView = findViewById(R.id.winsCounter);
        lossesView = findViewById(R.id.lossCounter);
        inizializeTextViews();
        updateView();
        Button addWin = findViewById(R.id.addWins);
        Button addLoss = findViewById(R.id.addLoss);
        Button removeWin = findViewById(R.id.removeWins);
        Button removeLoss = findViewById(R.id.removeLoss);

        repository = new DeckRepository(this);

        inizializeButtons(addWin, addLoss, removeLoss, removeWin);

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

    private void inizializeButtons(Button addWin, Button addLoss, Button removeLoss, Button removeWin) {
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

    private void showCards(List<Card> cardToShoww) {

        cardToShoww.forEach(item -> {
            cards.append(item.getCard_title() + "\n");
            System.out.println(item.getCard_title() + "\n");
        });
    }


}
//ViewPager