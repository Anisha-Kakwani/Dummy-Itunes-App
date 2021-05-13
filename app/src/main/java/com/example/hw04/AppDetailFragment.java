package com.example.hw04;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AppDetailFragment extends Fragment {

    private static final String TOKEN = "TOKEN";
    private static final String APP = "APP";
    private String mParam1;
    private DataServices.App mParam2;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    AppDetailViewAdapter adapter;
    TextView name,artist_name,release_date;

    public AppDetailFragment() {
        // Required empty public constructor
    }
    public static AppDetailFragment newInstance(String param1, DataServices.App param2) {
        AppDetailFragment fragment = new AppDetailFragment();
        Bundle args = new Bundle();
        args.putString(TOKEN, param1);
        args.putSerializable(APP, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(TOKEN);
            mParam2 = (DataServices.App)getArguments().getSerializable(APP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_detail, container, false);
        getActivity().setTitle(getResources().getString(R.string.app_details_label));
        name = view.findViewById(R.id.textViewAppNameDisplay);
        artist_name = view.findViewById(R.id.textViewArtistNameDisplay);
        release_date = view.findViewById(R.id.textViewDateDisplay);
        name.setText(mParam2.name);
        artist_name.setText(mParam2.artistName);
        release_date.setText(mParam2.releaseDate);
        recyclerView = view.findViewById(R.id.recyclerView_app_detail_display);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AppDetailViewAdapter(mParam2.genres);
        recyclerView.setAdapter(adapter);
        return view;
    }
}