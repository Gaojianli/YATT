package me.gaojianli.yetanothertiktok.data

import androidx.databinding.BaseObservable
import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("feedurl") val url: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("description") val description: String,
    @SerializedName("likecount") val likecount: Int,
    @SerializedName("avatar") val avatarUrl: String
) : BaseObservable()