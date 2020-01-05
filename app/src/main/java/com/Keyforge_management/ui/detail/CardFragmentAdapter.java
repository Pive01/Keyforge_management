package com.Keyforge_management.ui.detail;

import android.os.Bundle;

import com.Keyforge_management.data.model.Card;
import com.Keyforge_management.data.model.House;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CardFragmentAdapter extends FragmentPagerAdapter {

    House[] houseArr;
    private HashMap<House, List<Card>> map;

    public CardFragmentAdapter(@NonNull FragmentManager fm, int behavior, HashMap<House, List<Card>> map, House[] houseArr) {
        super(fm, behavior);
        this.map = map;
        this.houseArr = houseArr;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        CardListFragment cardListFragment = new CardListFragment();


        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) map.get(houseArr[position]));
        bundle.putSerializable("house", houseArr[position]);

        cardListFragment.setArguments(bundle);
        return cardListFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
