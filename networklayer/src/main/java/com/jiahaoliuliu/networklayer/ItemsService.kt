package com.jiahaoliuliu.networklayer

import io.reactivex.Single
import retrofit2.http.GET

interface ItemsService {
    @GET("")
    fun getItemsList(): Single<ItemsList?>
}