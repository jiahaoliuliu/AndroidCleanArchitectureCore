package com.jiahaoliuliu.datalayer.itemsrepository;

import com.jiahaoliuliu.entity.Item;
import com.jiahaoliuliu.networklayer.ItemsService;
import com.jiahaoliuliu.storagelayer.ItemImpl;
import com.jiahaoliuliu.storagelayer.ItemsDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import timber.log.Timber;

public class ItemsRepositoryImpl implements ItemsRepository {

    private final ItemsService itemsService;
    private final ItemsDatabase itemsDatabase;
    // Temporal memory for the items list
    private List<? extends Item> memoryCache = new ArrayList<>();

    public ItemsRepositoryImpl(ItemsService itemsService, ItemsDatabase itemsDatabase) {
        this.itemsService = itemsService;
        this.itemsDatabase = itemsDatabase;
    }

    @Override
    public Single<? extends List<? extends Item>> retrieveItemsList() {
        // List of Priorities
        Single<? extends List<? extends Item>> backendSource = retrieveItemsListFromBackend();
        Single<? extends List<? extends Item>> storageSource = retrieveItemsListFromStorage();
        Single<? extends List<? extends Item>> cacheSource = retrieveItemsListFromCache();

        return Single.concat(backendSource, storageSource, cacheSource)
                .filter(source -> !source.isEmpty())
                .first(memoryCache);
    }

    private Single<? extends List<? extends Item>> retrieveItemsListFromBackend() {
        return itemsService.itemsList
             .map(itemsListBackend -> itemsListBackend.getItemsList())
             .doOnSuccess(itemsList -> {
                // Updates the internal cache
                saveItemsListToCache(itemsList);
                // Save the content into the database
                for (Item item: itemsList) {
                    Timber.v("Trying to save " + item + " into the database");
                    itemsDatabase.itemDao().upsert(new ItemImpl(item));
                }
             }).onErrorResumeNext(throwable -> {
                 Timber.e(throwable, "Error retrieving data from backend");
                 return Single.just(new ArrayList<>());
             });
    }

    private Single<? extends List<? extends Item>> retrieveItemsListFromCache() {
        return Single.just(memoryCache);
    }

    private Single<? extends List<? extends Item>> retrieveItemsListFromStorage() {
        return itemsDatabase.itemDao().getAllItems()
                .doOnSuccess(itemsList -> saveItemsListToCache(itemsList))
                .onErrorResumeNext(throwable -> {
                   Timber.e(throwable, "Error retrieving data from the database ");
                   return Single.just(new ArrayList<>());
                });
    }

    private void saveItemsListToCache(List<? extends Item> newItemsList) {
        this.memoryCache = newItemsList;
    }
}
