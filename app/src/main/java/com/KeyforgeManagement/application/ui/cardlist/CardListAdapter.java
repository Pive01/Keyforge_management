package com.KeyforgeManagement.application.ui.cardlist;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.data.model.Card;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {
    private final List<Card> cards;
    private final CardListInteractionListener listener;

    public CardListAdapter(CardListInteractionListener listener) {
        this.cards = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardname_view, parent, false);
        return new CardViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.display(cards.get(position));
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void onNewCards(List<Card> cards) {
        this.cards.clear();
        this.cards.addAll(cards);
        notifyDataSetChanged();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView cardRarity;
        private final ImageView specialRarity;
        private final TextView cardName;
        private final CardListInteractionListener listener;

        CardViewHolder(@NonNull View itemView, CardListInteractionListener listener) {
            super(itemView);
            this.listener = listener;
            cardName = itemView.findViewById(R.id.cardnameview);
            cardRarity = itemView.findViewById(R.id.cardrarity);
            specialRarity = itemView.findViewById(R.id.cardspecial);

        }

        void display(Card card) {
            cardRarity.setImageResource(card.getRarity().getImageCardId());
            cardName.setText(card.getCard_title());
            cardName.setTextColor(card.getIs_enhanced() ? Color.CYAN : Color.BLACK);

            if (card.getIs_legacy())
                specialRarity.setImageResource(R.drawable.ic_legacy);
            else if (card.getIs_anomaly())
                specialRarity.setImageResource(R.drawable.ic_anomalies);
            else if (card.getIs_maverick())
                specialRarity.setImageResource(R.drawable.ic_maverick);
            itemView.setOnClickListener(v -> listener.onCardClicked(card));
        }
    }
}
