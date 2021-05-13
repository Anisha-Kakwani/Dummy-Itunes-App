/*
File Name - MainActivity
Assignment - Homework 04
Group B8
Group Members - Anisha Kakwani
                Hiten Changlani

 */
package com.example.hw04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginInterface, RegisterFragment.RegisterAccountInterface, AppCategoriesFragment.AppCategoryInterface, AppListFragment.AppListInterface{
    String loggedInToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer,new LoginFragment())
                .commit();
    }

    @Override
    public void getLoginCredentials(String token) {
        loggedInToken = token;
        Log.d("demo","successful login");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer,AppCategoriesFragment.newInstance(loggedInToken))
                .commit();
    }

    @Override
    public void createNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer,new RegisterFragment())
                .commit();
    }

    @Override
    public void displayAppListByCategory(String category) {
        Log.d("Demo",category);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer,AppListFragment.newInstance(loggedInToken,category))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void logOut() {
        this.loggedInToken =null;
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new LoginFragment())
                .commit();
    }

    @Override
    public void displayAppDetails(DataServices.App app) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer,AppDetailFragment.newInstance(loggedInToken,app))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addNewlyCreatedUser(String token) {
        loggedInToken=token;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer,AppCategoriesFragment.newInstance(loggedInToken))
                .commit();
    }

    @Override
    public void cancelRegisterUser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer,new LoginFragment())
                .commit();
    }
}