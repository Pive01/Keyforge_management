package com.Keyforge_management.ui.cardlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Keyforge_management.R;
import com.Keyforge_management.data.model.Card;

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


    public class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView cardName;
        private CardListInteractionListener listener;

        public CardViewHolder(@NonNull View itemView, CardListInteractionListener listener) {
            super(itemView);
            this.listener = listener;
            cardName = itemView.findViewById(R.id.cardnameview);
        }

        public void display(Card card) {

            cardName.setText(card.getCard_title());
            itemView.setOnClickListener(v -> listener.onCardClicked(card));
        }
    }
}
