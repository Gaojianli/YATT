package me.gaojianli.yetanothertiktok.ui.main.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import de.mpicbg.scicomp.kutils.parmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.gaojianli.yetanothertiktok.R
import me.gaojianli.yetanothertiktok.ui.main.VideoAdapter
import me.gaojianli.yetanothertiktok.ui.main.viewmodels.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() =
            MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        var previewMap = HashMap<String, Bitmap>()
        val videoAdapter = VideoAdapter(viewModel.videoList, previewMap, activity!!)
        recyclerView.adapter = videoAdapter

        // Loading video list finished, refresh the recyclerView
        viewModel.videoList.observe(this, Observer { t ->
            val animateView: LottieAnimationView = view.findViewById(R.id.loading_animate)
            viewModel.viewModelScope.launch {
                // Fetch the preview image of each video
                withContext(Dispatchers.IO) {
                    previewMap = t.parmap { it.id to viewModel.getVideoPreview(it) }
                        .toMap() as HashMap<String, Bitmap>
                }
                videoAdapter.refresh(previewMap)
                // Remove loading view
                animateView.animate().alpha(0f).setDuration(150)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            animateView.visibility = View.GONE
                            recyclerView.apply {
                                alpha = 0f
                                visibility = View.VISIBLE
                                animate().alpha(1f).setDuration(250).setListener(null)
                            }
                        }
                    })
            }
        })
        return view
    }
}