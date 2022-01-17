package com.example.numcompos.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.numcompos.R
import com.example.numcompos.databinding.FragmentGameBinding
import com.example.numcompos.domain.entity.GameResult
import com.example.numcompos.domain.entity.Level

class GameFragment : Fragment() {
    private lateinit var curentLevel : Level

    private var _binding : FragmentGameBinding? = null
    private val binding : FragmentGameBinding
        get() = _binding?: throw RuntimeException("FragmentGameBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parsParams(){
        curentLevel = requireArguments().getSerializable(KEY_LEVEL) as Level
    }

    private fun launchGameFinishedFragment(gameResult: GameResult){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val  NAME = "GameFragment"
        private const val KEY_LEVEL = "level"

        fun newInstance(level : Level) : GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }
    }
}