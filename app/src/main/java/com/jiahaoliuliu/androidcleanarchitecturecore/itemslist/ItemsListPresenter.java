package com.jiahaoliuliu.androidcleanarchitecturecore.itemslist;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ItemsListPresenter implements ItemsListContract.Presenter {

    private final ItemsListContract.Model model;

    private ItemsListContract.View view;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public ItemsListPresenter(ItemsListContract.Model model) {
        this.model = model;
    }

    @Override
    public void setView(ItemsListContract.View view) {
        this.view = view;
    }

    @Override
    public void retrieveItemsList() {
        compositeDisposable.add(model.retrieveItemsList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                itemsList -> view.showItemsList(itemsList),
                throwable -> {
                    // TODO: Show the error on the screen
                    Timber.e(throwable,"Error getting the list of the items from backend ");
                }));
    }

    @Override
    public void dispose() {
        compositeDisposable.dispose();
    }
}
