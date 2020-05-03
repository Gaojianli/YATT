package me.gaojianli.yetanothertiktok.ui.main

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import me.gaojianli.yetanothertiktok.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.videoList.observe(this, Observer { t ->
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewList)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = VideoAdapter(t, context!!)

            val animateView: LottieAnimationView = view.findViewById(R.id.loading_animate)
            val fadeOutAnimator = AnimatorInflater.loadAnimator(context, R.animator.fade_out)
            val fadeInAnimator = AnimatorInflater.loadAnimator(context, R.animator.fade_in)
            fadeOutAnimator.setTarget(animateView)
            fadeInAnimator.setTarget(recyclerView)
            fadeOutAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    fadeInAnimator.start()
                    val parentContainer = (animateView.parent as ViewGroup)
                    parentContainer.removeView(animateView)
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            })
            fadeOutAnimator.start()
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}