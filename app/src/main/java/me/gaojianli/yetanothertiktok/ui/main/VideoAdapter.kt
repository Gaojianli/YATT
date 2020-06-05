package me.gaojianli.yetanothertiktok.ui.main

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.gaojianli.yetanothertiktok.BR
import me.gaojianli.yetanothertiktok.R
import me.gaojianli.yetanothertiktok.data.VideoResponse
import me.gaojianli.yetanothertiktok.databinding.VideoItemBinding
import java.io.ByteArrayOutputStream
import android.util.Pair as UtilPair

class VideoAdapter(
    private val liveVideoList: LiveData<List<VideoResponse>>,
    previewMap: Map<String, Bitmap>,
    private val mContext: Activity
) :
    RecyclerView.Adapter<VideoAdapter.Companion.VideoViewHolder>() {
    private var videoList: List<VideoResponse> = ArrayList<VideoResponse>()
    private var mPreviewMap: Map<String, Bitmap> = previewMap
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.binding.setVariable(BR.videoItem, videoList[position])
        val cardView: CardView = holder.itemView.findViewById(R.id.list_item_cardview)
        val avatarImage: ImageView = holder.itemView.findViewById(R.id.avatar_img)
        // Click event cards
        cardView.setOnClickListener {
            val avatarView: View = holder.itemView.findViewById(R.id.avatar_view)
            val titleText: View = holder.itemView.findViewById(R.id.title_text)
            val backgroundImg: View = holder.itemView.findViewById(R.id.video_preview)
            val nicknameText: View = holder.itemView.findViewById(R.id.nickname_text_view)

            val intent = Intent(mContext, PlayActivity::class.java)
            val avatarShareElements = UtilPair.create(avatarView, "avatar_img")
            val descriptionShareElements = UtilPair.create(titleText, "title_text")
            val backgroundShareElements = UtilPair.create(backgroundImg, "background_img")
            val nicknameShareElements = UtilPair.create(nicknameText, "nickname_text")

            val options = ActivityOptions.makeSceneTransitionAnimation(
                mContext,
                avatarShareElements,
                descriptionShareElements,
                backgroundShareElements,
                nicknameShareElements
            )
            intent.putExtra("videoInfo", videoList[position])
            intent.putExtra("previewHeight", backgroundImg.measuredHeight)
            if (mPreviewMap.containsKey(videoList[position].id) && mPreviewMap[videoList[position].id] != null) {
                val byteArrayOutputStream = ByteArrayOutputStream()
                mPreviewMap[videoList[position].id]?.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    byteArrayOutputStream
                )
                intent.putExtra("previewPicture", byteArrayOutputStream.toByteArray())
            }
            mContext.startActivity(intent, options.toBundle())
            mContext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        Glide.with(mContext)
            .load(videoList[position].avatarUrl)
            .placeholder(R.drawable.default_avatar)
            .into(avatarImage)
        // Set preview image
        if (mPreviewMap.containsKey(videoList[position].id) && mPreviewMap[videoList[position].id] != null)
            holder.itemView.findViewById<ImageView>(R.id.video_preview)
                .setImageBitmap(mPreviewMap[videoList[position].id])
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

    fun refresh(previewMap: Map<String, Bitmap>) {
        videoList = liveVideoList.value ?: ArrayList()
        mPreviewMap = previewMap
        notifyDataSetChanged()
    }

    companion object {
        class VideoViewHolder(val binding: VideoItemBinding) :
            RecyclerView.ViewHolder(binding.root)
    }
}

