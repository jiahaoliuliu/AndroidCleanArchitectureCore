package com.jiahaoliuliu.storagelayer;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ItemsDatabaseModule {

    private ItemsDatabase itemsDatabase;

    @Provides
    @Singleton
    public ItemsDatabase provideItemsDatabase(Context context) {
        if (itemsDatabase == null) {
            itemsDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    ItemsDatabase.class, ItemsDatabase.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return itemsDatabase;
    }
}
