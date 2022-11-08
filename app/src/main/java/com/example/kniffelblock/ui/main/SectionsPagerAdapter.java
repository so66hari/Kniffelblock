package com.example.kniffelblock.ui.main;

import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.kniffelblock.Game;

import java.util.List;


public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    private int tabNum;
    private Boolean continueButton;

    public SectionsPagerAdapter(int tabNum, FragmentManager fm, Boolean continueButton) {
        super(fm);
        this.tabNum = tabNum;
        this.continueButton = continueButton;
    }

    @Override
    public PlaceholderFragment getItem(int position) {
        return PlaceholderFragment.newInstance(position, continueButton);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        int playerNum = position + 1;
        return "Spieler " + playerNum;
    }

    @Override
    public int getCount() {
        return tabNum;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    public void setWords(List<Game> games) {
    }
}