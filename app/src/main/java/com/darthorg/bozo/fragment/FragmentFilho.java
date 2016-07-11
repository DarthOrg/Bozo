package com.darthorg.bozo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.darthorg.bozo.R;

/**
 * Created by Gustavo on 11/07/2016.
 */
public class FragmentFilho extends Fragment {
    private String nomeFragmentFilho;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filho, container, false);
        getIDs(view);
        setEvents();
        return view;
    }

    private void getIDs(View view) {
        //Exemplo :
        // TextView textview = (TextView) view.findViewById(R.id.meuTextView);
    }

    private void setEvents() {

    }


}
