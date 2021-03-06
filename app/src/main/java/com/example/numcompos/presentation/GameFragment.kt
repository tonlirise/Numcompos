package com.example.numcompos.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.numcompos.databinding.FragmentGameBinding
import com.example.numcompos.domain.entity.GameResult


class GameFragment : Fragment() {
    private val inArgs by navArgs<GameFragmentArgs>()

    private val gameViewModel by lazy {
        ViewModelProvider(
            this,
            GameViewModelFactory(requireActivity().application, inArgs.level)
        )[GameViewModel::class.java]
    }

    private val tvOptions by lazy{
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel(){
        binding.gameViewModel = gameViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        gameViewModel.currQuestion.observe(viewLifecycleOwner){
            for (i in 0 until tvOptions.size){
                tvOptions[i].text = it.options[i].toString()
            }
        }

        gameViewModel.gameResult.observe(viewLifecycleOwner){
            launchGameFinishedFragment(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult))
    }

}