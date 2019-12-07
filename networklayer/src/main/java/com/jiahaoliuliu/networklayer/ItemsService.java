package com.jiahaoliuliu.networklayer;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ItemsService {

    @GET("")
    Single<ItemsList> getItemsList();

}
