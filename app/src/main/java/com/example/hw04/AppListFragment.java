package com.example.hw04;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class AppListFragment extends Fragment implements AppListViewAdapter.AppListViewAdapterInterface{
    private static final String token = "TOKEN";
    private static final String category = "CATEGORY";
    private String mParam1;
    private String mParam2;
    ArrayList<DataServices.App> appListData;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    AppListViewAdapter adapter;
    AppListInterface Listener;

    public AppListFragment() {
        // Required empty public constructor
    }
    public static AppListFragment newInstance(String param1, String param2) {
        AppListFragment fragment = new AppListFragment();
        Bundle args = new Bundle();
        args.putString(token, param1);
        args.putString(category, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(token);
            mParam2 = getArguments().getString(category);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AppListFragment.AppListInterface) {
            Listener = (AppListFragment.AppListInterface)context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_list, container, false);
        getActivity().setTitle(mParam2);
        recyclerView = view.findViewById(R.id.recyclerView_app_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        new AsyncGetAppList().execute(mParam1,mParam2);
        return view;
    }

    @Override
    public void get_app_details(DataServices.App app) {
        Listener.displayAppDetails(app);
    }
    public interface AppListInterface{
        void displayAppDetails(DataServices.App app);
    }

    class AsyncGetAppList extends AsyncTask<String,String,ArrayList<DataServices.App>>{
        @Override
        protected void onPostExecute(ArrayList<DataServices.App> apps) {
            if(apps!=null){
                adapter = new AppListViewAdapter(apps,AppListFragment.this);
                recyclerView.setAdapter(adapter);
            }
            else{
                Toast.makeText(getActivity(), getResources().getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected ArrayList<DataServices.App> doInBackground(String... strings) {
            try{
                return DataServices.getAppsByCategory(strings[0], strings[1]);
            }
            catch (DataServices.RequestException e){
                return null;
            }

        }
    }
}