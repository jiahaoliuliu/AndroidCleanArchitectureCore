package com.jiahaoliuliu.androidcleanarchitecturecore.itemslist;

import com.jiahaoliuliu.datalayer.itemsrepository.ItemsRepository;
import com.jiahaoliuliu.entity.Item;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ItemsListModel implements ItemsListContract.Model {

    private final ItemsRepository itemsRepository;

    @Inject
    public ItemsListModel(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Override
    public Single<? extends List<? extends Item>> retrieveItemsList() {
        return itemsRepository.retrieveItemsList();
    }
}
