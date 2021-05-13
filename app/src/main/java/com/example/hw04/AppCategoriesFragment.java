package com.example.hw04;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class AppCategoriesFragment extends Fragment implements AppCategoryViewAdapter.AppCategoryViewAdapterInterface{

    private static final String TOKEN = "TOKEN";
    private String mParam1;
    TextView user_name;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    AppCategoryViewAdapter adapter;
    AppCategoryInterface appCategoryInterfaceListener;

    public AppCategoriesFragment() {
        // Required empty public constructor
    }

    public static AppCategoriesFragment newInstance(String param1) {
        AppCategoriesFragment fragment = new AppCategoriesFragment();
        Bundle args = new Bundle();
        args.putString(TOKEN, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(TOKEN);
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AppCategoriesFragment.AppCategoryInterface) {
            appCategoryInterfaceListener = (AppCategoriesFragment.AppCategoryInterface)context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_categories, container, false);
        getActivity().setTitle(getResources().getString(R.string.app_categories_label));
        user_name = view.findViewById(R.id.textView_loggedInName);
        recyclerView = view.findViewById(R.id.recyclerView_app_categories);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        new AsyncGetAccount().execute(mParam1);
        view.findViewById(R.id.button_logOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appCategoryInterfaceListener.logOut();
            }
        });
        return view;
    }

    public interface AppCategoryInterface{
        void displayAppListByCategory(String category);
        void logOut();
    }

    @Override
    public void get_app_list(String name) {
        appCategoryInterfaceListener.displayAppListByCategory(name);
    }

    class AsyncGetAccount extends AsyncTask<String,Integer,DataServices.Account>{

        @Override
        protected void onPostExecute(DataServices.Account account) {
            if(account!=null){
                user_name.setText(getResources().getString(R.string.welcome_label) + " " +  account.getName());
                new AsyncGetAppCategory().execute(mParam1);
            }
            else{
                Toast.makeText(getActivity(), getResources().getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected DataServices.Account doInBackground(String... strings) {
            try{
                return DataServices.getAccount(strings[0]);
            }
            catch (DataServices.RequestException e){
                return null;
            }
        }
    }

    class AsyncGetAppCategory extends AsyncTask<String,Integer,ArrayList<String>>  {
        @Override
        protected void onPostExecute(ArrayList<String> accounts) {
            if(accounts!=null){
                adapter = new AppCategoryViewAdapter(accounts,AppCategoriesFragment.this);
                recyclerView.setAdapter(adapter);
            }
            else{
                Toast.makeText(getActivity(), getResources().getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try{
                return DataServices.getAppCategories(strings[0]);
            }
            catch (DataServices.RequestException e){
                return null;
            }
        }


    }
}