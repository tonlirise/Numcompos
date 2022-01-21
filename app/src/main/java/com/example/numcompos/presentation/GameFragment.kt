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
import com.example.numcompos.R
import com.example.numcompos.databinding.FragmentGameBinding
import com.example.numcompos.domain.entity.GameResult
import com.example.numcompos.domain.entity.Level

class GameFragment : Fragment() {
    private val gameViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
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

    private lateinit var curentLevel: Level

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsParams()
    }

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
        gameViewModel.startGame(curentLevel)
        setOnClickListenerForOptions()
    }

    private fun observeViewModel(){
        gameViewModel.gameStrTimer.observe(viewLifecycleOwner){
            binding.tvTimer.text = it.toString()
        }

        gameViewModel.currQuestion.observe(viewLifecycleOwner){
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for (i in 0 until tvOptions.size){
                tvOptions[i].text = it.options[i].toString()
            }
        }

        gameViewModel.percentOfRightAnswer.observe(viewLifecycleOwner){
            binding.progressBar.progress = it
        }

        gameViewModel.answersProgress.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.text = it
        }

        gameViewModel.enoughContOfRightAsnswers.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.setTextColor(getColorByState(it))
        }

        gameViewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner){
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }

        gameViewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }

        gameViewModel.answersProgress.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.text = it
        }

        gameViewModel.gameResult.observe(viewLifecycleOwner){
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, GameFinishedFragment.newInstance(it))
                .commit()
        }
    }

    private fun setOnClickListenerForOptions(){
        for(tvOption in tvOptions){
            tvOption.setOnClickListener(){
                gameViewModel.choseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    private fun getColorByState(state : Boolean) : Int{
        val idColor = when(state){
            true -> android.R.color.holo_green_light
            false -> android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), idColor)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parsParams() {
        curentLevel = requireArguments().getSerializable(KEY_LEVEL) as Level
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }
    }
}