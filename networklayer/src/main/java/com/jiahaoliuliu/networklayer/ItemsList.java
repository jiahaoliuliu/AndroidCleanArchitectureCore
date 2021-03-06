package com.jiahaoliuliu.networklayer;

import java.util.List;

public class ItemsList {

    private int page;
    private List<ItemImpl> itemsList;

    public ItemsList(int page, List<ItemImpl> itemsList) {
        this.page = page;
        this.itemsList = itemsList;
    }

    public int getPage() {
        return page;
    }

    public List<ItemImpl> getItemsList() {
        return itemsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemsList that = (ItemsList) o;

        if (page != that.page) return false;
        return itemsList != null ? itemsList.equals(that.itemsList) : that.itemsList == null;
    }

    @Override
    public int hashCode() {
        int result = page;
        result = 31 * result + (itemsList != null ? itemsList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ItemsList{" +
                "page=" + page +
                ", itemsList=" + itemsList +
                '}';
    }
}
