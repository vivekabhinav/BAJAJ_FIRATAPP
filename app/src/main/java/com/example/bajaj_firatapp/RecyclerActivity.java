package com.example.bajaj_firatapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

public class RecyclerActivity extends AppCompatActivity {
    String[] countries = new String[]{"India","Pakistan","Dubai","Nepal","Bhutan","Egypt"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG,"creating");

        setContentView(R.layout.activity_recycler);
        RecyclerView countriesRecyclerView = findViewById(R.id.countriesRview); //socket
        CountriesAdapter adapter = new CountriesAdapter(countries);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        countriesRecyclerView.setLayoutManager(layoutManager);
        countriesRecyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG,"starting");
        int j=1;
        for(int i=1;i<10;i++){
            j = i*5 +10 -14;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"resuming -- restore the state of the game");

    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"pausing -- save the state of the game");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG,"stop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "destroy");
    }
}