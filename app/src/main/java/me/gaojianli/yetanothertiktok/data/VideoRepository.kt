package me.gaojianli.yetanothertiktok.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VideoRepository {
    private val baseUrl: String = "https://beiyou.bytedance.com"
    private val apiService: ApiService =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

    suspend fun getVideoList() = apiService.getList()
}