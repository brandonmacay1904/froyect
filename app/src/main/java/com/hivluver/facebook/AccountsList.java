package com.hivluver.facebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class AccountsList extends AppCompatActivity {
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DataBaseFacebookAccount dbConnector;
    private AdapterAccounts adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_list);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        getAlllActivities("_id");
    }
    private void getAlllActivities(String filter){
        dbConnector = new DataBaseFacebookAccount(this);
        adapter = new AdapterAccounts(dbConnector.getAllActividades(filter), this, recyclerView);
        recyclerView.setAdapter(adapter);
    }
}