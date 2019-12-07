package com.jiahaoliuliu.androidcleanarchitecturecore.itemslist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jiahaoliuliu.androidcleanarchitecturecore.MainApplication;
import com.jiahaoliuliu.androidcleanarchitecturecore.R;
import com.jiahaoliuliu.entity.Item;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class ItemsListActivity extends AppCompatActivity implements ItemsListContract.View {

    @Inject
    protected ItemsListContract.Presenter presenter;

    private RecyclerView recyclerView;
    private ItemsListAdapter itemsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        MainApplication.getMainComponent().inject(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        itemsListAdapter = new ItemsListAdapter();
        recyclerView.setAdapter(itemsListAdapter);

        presenter.setView(this);
        presenter.retrieveItemsList();
    }

    @Override
    public void showItemsList(List<? extends Item> itemsList) {
        Timber.v("List of items retrieved " + itemsList.toString());
        itemsListAdapter.setItemsList(itemsList);
    }

    @Override
    protected void onDestroy() {
        presenter.dispose();
        super.onDestroy();
    }
}
