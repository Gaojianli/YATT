package me.gaojianli.yetanothertiktok.data

import retrofit2.http.GET

interface ApiService {
    @GET("/api/invoke/video/invoke/video")
    suspend fun getList(): List<VideoResponse>

}