package com.salilaev.stopwatch.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.salilaev.stopwatch.R
import org.w3c.dom.Text

class MainScreen : Fragment(R.layout.screen_main) {

    private val viewModel: MainScreenViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

//        val formatter = String.format("%02d:$02d:$02d")

}