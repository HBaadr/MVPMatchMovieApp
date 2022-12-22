package com.hourimeche.mvpmatchmovieapp.presentation.main

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hourimeche.mvpmatchmovieapp.R
import com.hourimeche.mvpmatchmovieapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Whenever the view is resumed, subscribe to our viewmodel's view state StateFlow
        lifecycleScope.launchWhenResumed {
            viewModel.viewState.collect { viewState ->
                processViewState(viewState)
            }
        }
    }

    private fun processViewState(viewState: MainState) {

        lifecycleScope.launch {
            binding.progressCircular.visibility =
                if (viewState.showProgressBar) View.VISIBLE else View.INVISIBLE
            binding.text.text = viewState.searchResponse.toString()
            binding.text.visibility =
                if (viewState.searchResponse != null) View.VISIBLE else View.INVISIBLE
            binding.search.visibility =
                if (viewState.searchResponse == null) View.VISIBLE else View.INVISIBLE
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main, container, false)
        binding.text.movementMethod = ScrollingMovementMethod()
        binding.search.setOnClickListener {
            viewModel.searchMovies("Titanic")
        }
        return binding.root
    }

}