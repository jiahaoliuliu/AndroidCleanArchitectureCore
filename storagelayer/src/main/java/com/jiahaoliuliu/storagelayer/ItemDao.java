package com.jiahaoliuliu.storagelayer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void insert(ItemImpl itemImpl);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void update(ItemImpl itemImpl);

    public void upsert(ItemImpl itemImpl) {
        insert(itemImpl);
        update(itemImpl);
    }

    public void upsert(List<ItemImpl> itemsList) {
        for (ItemImpl itemImpl : itemsList) {
            upsert(itemImpl);
        }
    }

    @Delete
    public abstract void delete(ItemImpl itemImpl);

    @Query("Delete from item_table")
    public abstract void deleteAllItems();

    @Query("Select * from item_table order by timeStamp asc")
    public abstract Single<List<ItemImpl>> getAllItems();
}
