package me.gaojianli.yetanothertiktok.ui.main

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.play_activity.*
import me.gaojianli.yetanothertiktok.BR
import me.gaojianli.yetanothertiktok.R
import me.gaojianli.yetanothertiktok.data.VideoResponse
import me.gaojianli.yetanothertiktok.databinding.PlayActivityBinding

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class PlayActivity : AppCompatActivity() {
    private val hideHandler = Handler()

    @SuppressLint("InlinedApi")
    private val hidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        fullscreen_content.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    private var isFullscreen: Boolean = false
    private lateinit var videoPreview: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding =
            DataBindingUtil.setContentView<PlayActivityBinding>(this, R.layout.play_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val videoInfo = intent.getSerializableExtra("videoInfo") as VideoResponse
        val videoView = findViewById<VideoView>(R.id.videoView)
        videoPreview = findViewById(R.id.video_preview)
        if (intent.hasExtra("previewPicture")) {
            val bitmapByteArray = intent.getByteArrayExtra("previewPicture")
            val bmp = BitmapFactory.decodeByteArray(bitmapByteArray, 0, bitmapByteArray?.size!!)
            videoPreview.setImageBitmap(bmp)
        }
        Glide.with(this)
            .load(videoInfo.avatarUrl)
            .placeholder(R.mipmap.default_avatar)
            .into(findViewById(R.id.avatar_img))
        mBinding.setVariable(BR.videoInfo, videoInfo)
        videoView.setOnPreparedListener { mp ->
            mp?.setOnInfoListener { _, what, _ ->
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    videoPreview.visibility = View.GONE
                }
                return@setOnInfoListener true
            }
        }
        videoView.setOnCompletionListener { mp ->
            mp.start()
            mp.isLooping = true
        }
        videoView.setVideoPath(videoInfo.url)
        videoView.requestFocus()

        videoView.start()
        isFullscreen = true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        hide()
    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
        isFullscreen = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        hideHandler.postDelayed(hidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    override fun onBackPressed() {
        val previewHeight = intent.getIntExtra("previewHeight", 0)
        if (previewHeight < videoPreview.height) {
            videoView.suspend()
            //videoView.visibility = View.GONE
            videoPreview.visibility = View.VISIBLE
        }
        finishAfterTransition()
    }

    companion object {
        private const val UI_ANIMATION_DELAY = 300
    }
}