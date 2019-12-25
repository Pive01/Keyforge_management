package com.Keyforge_management.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Keyforge_management.R;
import com.Keyforge_management.data.model.House;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.DeckViewHolder> {

    private ArrayList<DeckUI> mDeckList;

    public static class DeckViewHolder extends RecyclerView.ViewHolder {

        public ImageView[] houseArr;
        public TextView deckName;
        public TextView expansion;
        public TextView sasScore;
        public TextView rawAember;

        public DeckViewHolder(@NonNull View itemView) {
            super(itemView);
            houseArr = new ImageView[]{itemView.findViewById(R.id.img_house1), itemView.findViewById(R.id.img_house2), itemView.findViewById(R.id.img_house3)};
            deckName = itemView.findViewById(R.id.deck_name);
            expansion = itemView.findViewById(R.id.expansion_name);
            sasScore = itemView.findViewById(R.id.sas_rating);
            rawAember = itemView.findViewById(R.id.raw_amber);

        }
    }

    public DeckAdapter(ArrayList<DeckUI> deckList) {
        mDeckList = deckList;
    }

    @NonNull
    @Override
    public DeckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_displayer, parent, false);
        DeckViewHolder dvh = new DeckViewHolder(v);
        return dvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DeckViewHolder holder, int position) {
        DeckUI currentItem = mDeckList.get(position);

        House[] houses = currentItem.getHouses();
        holder.houseArr[0].setImageResource(houses[0].getImageId());
        holder.houseArr[1].setImageResource(houses[1].getImageId());
        holder.houseArr[2].setImageResource(houses[2].getImageId());
        holder.deckName.setText(currentItem.getName());
        holder.expansion.setText(currentItem.getSet());
        holder.sasScore.setText(Integer.toString(currentItem.getSas()));
        holder.rawAember.setText(Integer.toString(currentItem.getAmber()));
    }

    @Override
    public int getItemCount() {
        return mDeckList.size();
    }


}
