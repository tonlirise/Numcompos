package com.example.numcompos.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.example.numcompos.R
import com.example.numcompos.databinding.FragmentGameFinishedBinding
import com.example.numcompos.domain.entity.GameResult

class GameFinishedFragment : Fragment() {
    private lateinit var gameResult: GameResult

    private var _binding : FragmentGameFinishedBinding? = null
    private val binding : FragmentGameFinishedBinding
        get() = _binding?: throw RuntimeException("FragmentGameFinishedBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsParams()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object
            : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                restartGame()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parsParams(){
        gameResult = requireArguments().getSerializable(KEY_GAME_RESULT) as GameResult
    }

    private fun restartGame(){
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME
            , FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }



    companion object {
        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult : GameResult): GameFinishedFragment{
            return GameFinishedFragment().apply {
                arguments = Bundle().apply{
                    putSerializable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}