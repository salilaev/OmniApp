package com.salilaev.stopwatch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.salilaev.stopwatch.R
import com.salilaev.stopwatch.databinding.ScreenMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {

    private val viewModel: MainViewModel by viewModels()
    private var _binding: ScreenMainBinding? = null
    private val binding: ScreenMainBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ScreenMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.isTimerStarted.observe(viewLifecycleOwner) {
            val icon = if (it) R.drawable.ic_pause else R.drawable.ic_start
            binding.btnStartPause.setImageResource(icon)
        }

        binding.btnStartPause.setOnClickListener {
            if (viewModel.isTimerStarted.value == true) {
                viewModel.stopTimer()
            } else {
                viewModel.startTimer()
            }
        }

        binding.btnReset.setOnClickListener {
            viewModel.reset()
        }

        viewModel.time.observe(viewLifecycleOwner) {
            val formattedTime = getTimeFormat(it)
            binding.tvTime.text = formattedTime
        }
    }

    private fun getTimeFormat(time: Long): String {

        val hour = time / 3600
        val min = time % 3600 / 60
        val sec = time % 60

        val hourTime = if (hour < 10) {
            "0$hour"
        } else {
            hour.toString()
        }

        val minFormat = if (min < 10) {
            "0$min"
        } else {
            min.toString()
        }

        val secFormat = if (sec < 10) {
            "0$sec"
        } else {
            "$sec"
        }

        return "$hourTime:$minFormat:$secFormat"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveTime()
        viewModel.stopTimer()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startTimer()
    }

}
