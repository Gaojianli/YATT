package me.gaojianli.yetanothertiktok.ui.main

import retrofit2.http.Url

class Image{
    init {
        fun Image(){}
    }
    var imageUrl:Url = TODO()

    public fun setIamgeUrl(setImageUrl: Url){
        imageUrl = setImageUrl

    }
    public fun getIamgeUrl(): Url {
        return imageUrl
    }

}