package com.darthorg.bozo.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.darthorg.bozo.fragment.FragmentJogador01;
import com.darthorg.bozo.fragment.FragmentJogador02;
import com.darthorg.bozo.fragment.FragmentJogador03;
import com.darthorg.bozo.fragment.FragmentJogador04;
import com.darthorg.bozo.fragment.FragmentJogador05;
import com.darthorg.bozo.fragment.FragmentJogador06;
import com.darthorg.bozo.fragment.FragmentJogador07;
import com.darthorg.bozo.fragment.FragmentJogador08;
import com.darthorg.bozo.fragment.FragmentJogador09;
import com.darthorg.bozo.fragment.FragmentJogador10;


public class TabLayoutJogadores extends FragmentPagerAdapter {
    private Context context;

    public TabLayoutJogadores(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "WENDELL UGALDS";
        }
        else if (position == 1) {
            return "GUSTAVO CANDIDO";
        }
        else if (position == 2) {
            return "Jogador 03";
        }
        else if (position == 3) {
            return "Jogador 04";
        }
        else if (position == 4) {
            return "Jogador 05";
        }
        else if (position == 5) {
            return "Jogador 06";
        }
        else if (position == 6) {
            return "Jogador 07";
        }
        else if (position == 7) {
            return "Jogador 08";
        }
        else if (position == 8) {
            return "Jogador 09";
        }

        return "Jogador 10";
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        if (position == 0) {
            f = new FragmentJogador01();
        } else if (position == 1) {
            f = new FragmentJogador02();
        }
        else if (position == 2) {
            f = new FragmentJogador03();
        }
        else if (position == 3) {
            f = new FragmentJogador04();
        }
        else if (position == 4) {
            f = new FragmentJogador05();
        }
        else if (position == 5) {
            f = new FragmentJogador06();
        }
        else if (position == 6) {
            f = new FragmentJogador07();
        }
        else if (position == 7) {
            f = new FragmentJogador08();
        }
        else if (position == 8) {
            f = new FragmentJogador09();
        }
        else if (position == 9) {
            f = new FragmentJogador10();
        }

        return f;
    }

    @Override
    public int getCount() {
        return 10;
    }
}