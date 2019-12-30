package com.jiahaoliuliu.networklayer

import com.jiahaoliuliu.entity.Item

data class ItemImpl(
        private val id: String,
        private val title: String,
        private val description: String,
        private val imageUrl: String) : Item {

    override fun getId(): String {
        return id
    }

    override fun getTitle(): String {
        return title
    }

    override fun getDescription(): String {
        return description
    }

    override fun getImageUrl(): String {
        return imageUrl
    }
}

data class ItemsList(val page: Int, val itemsList: List<ItemImpl>?)