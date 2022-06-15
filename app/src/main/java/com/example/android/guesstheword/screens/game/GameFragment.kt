/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment(R.layout.game_fragment) {

    //variable viewModel para asociar el fragmento con el viewModel
   /* private val viewModel: GameViewModel by activityViewModels<GameViewModel>()*/
    //Variable viewBinding para acceder a los elementos del xml
    private lateinit var viewModel : GameViewModel
    //Variable que contiene referencias a las vistas.
    // Esta variable se usa para inflar el diseño,
    // configurar los detectores de clics y mostrar l
    // os datos en la pantalla, responsabilidades del fragment
    private lateinit var binding: GameFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Se infla la vista
        binding = GameFragmentBinding.bind(view)

        Log.i("GameFragment", "Called ViewModelProvider.get")
        //Se inicializa el viewModel
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        // Se usa el método ViewModelProvider.get() y se pasa el contexto asociado con GameFragment y
        // la clase GameViewModel

        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }
        updateScoreText()
        updateWordText()
    }



    /** Methods for buttons presses **/

    private fun onSkip() {
        viewModel.onSkip()
        updateWordText()
        updateScoreText()
    }

    private fun onCorrect() {
       viewModel.onCorrect()
       updateWordText()
       updateScoreText()
    }




    /** Methods for updating the UI **/

    private fun updateWordText() {
        binding.wordText.text = viewModel.word
    }

    private fun updateScoreText() {
        binding.scoreText.text = viewModel.score.toString()
    }

    private fun onEndGame() {
        gameFinished()
    }

    private fun gameFinished() {
        Toast.makeText(activity,"Game has just finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameToScore().apply {
            score =viewModel.score
        }
        findNavController().navigate(action)
    }
}
