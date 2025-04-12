package com.salilaev.stop_watch

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.salilaev.stop_watch.databinding.ScreenMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var _binding: ScreenMainBinding? = null
    private val binding: ScreenMainBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.isTimerStarted.observe(viewLifecycleOwner) {
            val icon = if (it) R.drawable.ic_pause else R.drawable.ic_start
            binding.btnStartPause.setImageResource(icon)
        }

        binding.btnStartPause.setOnClickListener {
            clickSound {
                startSound(viewModel.isTimerStarted.value == true)
            }
            if (viewModel.isTimerStarted.value == true) {
                viewModel.stopTimer()
            } else {
                viewModel.startTimer()
            }
        }

        binding.btnReset.setOnClickListener {
            clickSound {  }
            viewModel.reset()
        }

        viewModel.time.observe(viewLifecycleOwner) {
            val formattedTime = getTimeFormat(it)
            binding.tvTime.text = formattedTime
        }
    }

    @SuppressLint("DefaultLocale")
    private fun getTimeFormat(time: Long): String {
        val hour = time / 3600
        val min = time % 3600 / 60
        val sec = time % 60

        return String.format("%02d:%02d:%02d", hour, min, sec)
    }

    private fun startSound(isStart: Boolean) {
        val player = MediaPlayer.create(
            requireContext(),
            if (isStart) R.raw.start_sound else R.raw.stop_sound
        )
        player.start()
    }

    private fun clickSound(block: () -> Unit) {
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.click_sound)
        mediaPlayer.setOnCompletionListener {
            block()
        }
        mediaPlayer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveTime()
        viewModel.stopTimer()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startTimer()
    }
}