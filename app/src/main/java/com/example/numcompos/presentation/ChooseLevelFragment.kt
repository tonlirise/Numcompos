package com.example.numcompos.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.numcompos.databinding.FragmentChooseLevelBinding
import com.example.numcompos.domain.entity.Level


class ChooseLevelFragment : Fragment() {
    private var _binding : FragmentChooseLevelBinding? = null
    private val binding : FragmentChooseLevelBinding
        get() = _binding?: throw RuntimeException("FragmentChooseLevelBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLevelBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonLevelTest.setOnClickListener {
                lunchGame(Level.TEST)
            }

            buttonLevelEasy.setOnClickListener {
                lunchGame(Level.EASY)
            }

            buttonLevelNormal.setOnClickListener {
                lunchGame(Level.NORMAL)
            }

            buttonLevelHard.setOnClickListener {
                lunchGame(Level.HARD)
            }
        }
    }

    private fun lunchGame(level: Level){
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}