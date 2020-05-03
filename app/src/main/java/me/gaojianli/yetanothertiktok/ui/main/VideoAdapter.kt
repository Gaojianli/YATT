package me.gaojianli.yetanothertiktok.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import me.gaojianli.yetanothertiktok.BR
import me.gaojianli.yetanothertiktok.R
import me.gaojianli.yetanothertiktok.data.VideoResponse
import me.gaojianli.yetanothertiktok.databinding.VideoItemBinding

class VideoAdapter(private val videoList: List<VideoResponse>) :
    RecyclerView.Adapter<VideoAdapter.Companion.VideoViewHolder>() {
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.binding.setVariable(BR.videoItem, videoList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding: VideoItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.video_item,
                parent,
                false
            )
        return VideoViewHolder(binding)
    }

    override fun getItemCount(): Int = videoList.size

    companion object {
        class VideoViewHolder(val binding: VideoItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        }
    }
}

