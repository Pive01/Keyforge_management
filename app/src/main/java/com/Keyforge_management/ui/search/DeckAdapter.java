package com.Keyforge_management.ui.search;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Keyforge_management.R;
import com.Keyforge_management.data.model.House;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.DeckViewHolder> {

    private List<DeckUI> mDeckList;

    public DeckAdapter(List<DeckUI> deckList) {
        mDeckList = deckList;
    }

    @NonNull
    @Override
    public DeckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_displayer, parent, false);
        return new DeckViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DeckViewHolder holder, int position) {
        holder.display(mDeckList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDeckList.size();
    }

    static class DeckViewHolder extends RecyclerView.ViewHolder {

        private static final String LOG_TAG = DeckViewHolder.class.getName();

        private ImageView[] houseArr;
        private TextView deckName;
        private TextView expansion;
        private TextView sasScore;
        private TextView rawAember;

        DeckViewHolder(@NonNull View itemView) {
            super(itemView);
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

        @SuppressLint("SetTextI18n")
        void display(DeckUI currentItem) {
            House[] houses = currentItem.getHouses();
            houseArr[0].setImageResource(houses[0].getImageId());
            houseArr[1].setImageResource(houses[1].getImageId());
            houseArr[2].setImageResource(houses[2].getImageId());
            deckName.setText(currentItem.getName());
            expansion.setText(currentItem.getSet());
            sasScore.setText(Integer.toString(currentItem.getSas()));
            rawAember.setText(Integer.toString(currentItem.getAmber()));

            itemView.setOnClickListener(v -> Log.d(LOG_TAG, currentItem.toString()));
        }
    }
}
