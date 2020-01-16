package com.Keyforge_management.ui.detail.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.Keyforge_management.R;
import com.Keyforge_management.data.model.Card;
import com.Keyforge_management.data.model.House;
import com.Keyforge_management.ui.cardlist.CardListAdapter;
import com.Keyforge_management.ui.cardlist.CardListInteractionListener;
import com.Keyforge_management.ui.detail.cardDetail.CardDetailActivity;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CardListFragment extends Fragment implements CardListInteractionListener {

    private CardListAdapter mAdapter;

    public CardListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_list1, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.cardlistrecycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerView.setHasFixedSize(false);

        mAdapter = new CardListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        ImageView imageView = view.findViewById(R.id.house_icon);

        House house = (House) getArguments().getSerializable("house");
        imageView.setImageResource(house.getImageId());

        List<Card> cardList = (List<Card>) getArguments().getSerializable("list");
        mAdapter.onNewCards(cardList);

        return view;
    }

    @Override
    public void onCardClicked(Card card) {
        Intent i = new Intent(getContext(), CardDetailActivity.class);
        i.putExtra("card", card);
        CardDetailActivity.start(getContext(), i);

    }

}
