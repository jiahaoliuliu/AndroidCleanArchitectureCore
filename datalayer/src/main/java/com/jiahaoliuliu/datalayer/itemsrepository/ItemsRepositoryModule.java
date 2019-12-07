package com.jiahaoliuliu.datalayer.itemsrepository;

import com.jiahaoliuliu.networklayer.ItemsService;
import com.jiahaoliuliu.storagelayer.ItemsDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ItemsRepositoryModule {

    @Provides
    @Singleton
    ItemsRepository providesItemsRepository(ItemsService itemsService, ItemsDatabase itemsDatabase) {
        return new ItemsRepositoryImpl(itemsService, itemsDatabase);
    }
}
