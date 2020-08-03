package com.KeyforgeManagement.application.ui.deckDTOlist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.model.DeckDTO;
import com.KeyforgeManagement.application.data.model.House;
import com.KeyforgeManagement.application.data.storage.typeConverters.ExpansionTypeConverter;
import com.KeyforgeManagement.application.data.storage.typeConverters.HouseArrayTypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeckDTOListAdapter extends RecyclerView.Adapter<DeckDTOListAdapter.DeckDTOViewHolder> {
    private final List<DeckDTO> deckDTOlist;
    private final DeckDTOListInteractionListener listener;
    private final List<DeckDTO> deckDTOBufferList;

    public DeckDTOListAdapter(DeckDTOListInteractionListener listener) {
        this.listener = listener;
        this.deckDTOlist = new ArrayList<>();
        this.deckDTOBufferList = new ArrayList<>();
    }

    @NonNull
    @Override
    public DeckDTOViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_displayer, parent, false);
        return new DeckDTOViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DeckDTOViewHolder holder, int position) {
        holder.display(deckDTOlist.get(position));
    }

    @Override
    public int getItemCount() {
        return deckDTOlist.size();
    }

    public void onNewDecks(List<DeckDTO> decks) {

        this.deckDTOlist.clear();
        this.deckDTOlist.addAll(decks);
        this.deckDTOBufferList.clear();
        this.deckDTOBufferList.addAll(decks);
        this.deckDTOlist.sort(Comparator.comparing((DeckDTO x) -> x.getDeck().getSasRating())
                .thenComparing((DeckDTO x) -> x.getDeck().getRawAmber())
                .reversed());
        notifyDataSetChanged();

    }

    public DeckDTO getDeckDTOAt(int position) {
        if (position >= deckDTOlist.size())
            return null;
        return deckDTOlist.get(position);
    }

    public void sort(int parameter) {
        switch (parameter) {
            case 0:
                this.deckDTOlist.sort(Comparator.comparing((DeckDTO x) -> x.getDeck().getSasRating())
                        .thenComparing((DeckDTO x) -> x.getDeck().getRawAmber())
                        .reversed());
                break;
            case 1:
                this.deckDTOlist.sort(Comparator.comparing((DeckDTO x) -> x.getDeck().getRawAmber())
                        .thenComparing((DeckDTO x) -> x.getDeck().getSasRating())
                        .reversed());
                break;
            case 2:
                this.deckDTOlist.sort(Comparator.comparing((DeckDTO x) -> x.getDeck().getLocalWins())
                        .thenComparing((DeckDTO x) -> x.getDeck().getSasRating())
                        .reversed());
                break;
            case 3:
                this.deckDTOlist.sort(Comparator.comparing((DeckDTO x) -> x.getDeck().getActionCount())
                        .thenComparing((DeckDTO x) -> x.getDeck().getSasRating())
                        .reversed());
                break;
            case 4:
                this.deckDTOlist.sort(Comparator.comparing((DeckDTO x) -> x.getDeck().getArtifactCount())
                        .thenComparing((DeckDTO x) -> x.getDeck().getSasRating())
                        .reversed());
                break;
            default:
                break;

        }
        notifyDataSetChanged();
    }

    public void filter(String tofilter, int sortingParameter) {
        List<DeckDTO> newList = new ArrayList<>();
        this.deckDTOBufferList.forEach(item -> {
            if (item.getDeck().getName().toLowerCase().contains(tofilter.toLowerCase()))
                newList.add(item);
        });
        this.deckDTOlist.clear();
        this.deckDTOlist.addAll(newList);
        this.sort(sortingParameter);
        notifyDataSetChanged();
    }

    public void advancedFilter(String tofilter, int sortingParameter) {
        List<DeckDTO> newList = new ArrayList<>(deckDTOBufferList);
        List<DeckDTO> toRemove = new ArrayList<>();
        List<String> parameterList = Arrays.asList(tofilter.substring(1).toUpperCase().replace(" ", "_").split(","));
        for (int i = 0; i < parameterList.size(); i++) {
            int finalI = i;
            newList.forEach(item -> {
                if (HouseArrayTypeConverter.fromSingleString(parameterList.get(finalI)) != null) {
                    boolean isIn = false;
                    for (int j = 0; j < 3; j++) {
                        if (HouseArrayTypeConverter.fromSingleString(parameterList.get(finalI)).equals(item.getDeck().getHouses()[j]))
                            isIn = true;
                    }
                    if (!isIn)
                        toRemove.add(item);
                } else if (ExpansionTypeConverter.fromString(parameterList.get(finalI)) != null) {
                    if (!ExpansionTypeConverter.fromString(parameterList.get(finalI)).equals(item.getDeck().getExpansion()))
                        toRemove.add(item);
                } else toRemove.add(item);
            });
            toRemove.forEach(item -> Log.i("cose", item.getDeck().getName()));
            newList.removeAll(toRemove);
        }
        this.deckDTOlist.clear();
        this.deckDTOlist.addAll(newList);
        this.sort(sortingParameter);
        notifyDataSetChanged();
    }

    static class DeckDTOViewHolder extends RecyclerView.ViewHolder {

        private final ImageView[] houseArr;
        private final TextView deckName;
        private final TextView expansion;
        private final TextView sasScore;
        private final TextView rawAember;
        private final DeckDTOListInteractionListener listener;

        public DeckDTOViewHolder(@NonNull View itemView, DeckDTOListInteractionListener listener) {
            super(itemView);
            this.listener = listener;
            houseArr = new ImageView[]{
                    itemView.findViewById(R.id.img_house1),
                    itemView.findViewById(R.id.img_house2),
                    itemView.findViewById(R.id.img_house3)
            };
            deckName = itemView.findViewById(R.id.deck_name);
            expansion = itemView.findViewById(R.id.expansion_name);
            sasScore = itemView.findViewById(R.id.sas_rating);
            rawAember = itemView.findViewById(R.id.raw_amber);
        }

        void display(DeckDTO currentItem) {
            Deck deck = currentItem.getDeck();
            House[] houses = deck.getHouses();
            houseArr[0].setImageResource(houses[0].getImageId());
            houseArr[1].setImageResource(houses[1].getImageId());
            houseArr[2].setImageResource(houses[2].getImageId());
            deckName.setText(deck.getName());
            expansion.setText(deck.getExpansion().toString());
            sasScore.setText(Integer.toString(deck.getSasRating()));
            rawAember.setText(Integer.toString(deck.getRawAmber()));

            itemView.setOnClickListener(v -> listener.onDeckClicked(currentItem));
            itemView.setOnLongClickListener(v -> {
                listener.onLongDeckClicked(currentItem);
                return true;
            });
        }
    }
}
