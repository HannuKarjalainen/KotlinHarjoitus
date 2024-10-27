package com.example.numberguesser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberguesser.model.Card
import com.example.numberguesser.model.GameModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val gameModel = GameModel()

    private val _currentCard = MutableStateFlow<Card?>(null)
    val currentCard: StateFlow<Card?> = _currentCard.asStateFlow()

    private val _score = MutableStateFlow(gameModel.score)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _guesses = MutableStateFlow(gameModel.guesses)
    val guesses: StateFlow<Int> = _guesses.asStateFlow()

    private val _gameActive = MutableStateFlow(gameModel.gameActive)
    val gameActive: StateFlow<Boolean> = _gameActive.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            startApiGame(1, false)
            resetGame(1, false)
        }
    }

    fun startApiGame(deckCount: Int, jokersIncluded: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            gameModel.startApiGame(deckCount, jokersIncluded)
            updateState()
            _isLoading.value = false
        }
    }

    fun makeGuess(isBigger: Boolean) {
        viewModelScope.launch {
            if (gameModel.makeGuess(isBigger)) {
                println("Arvaus tehty: $isBigger")
                updateState()
            } else {
                endGame()
            }
        }
    }

    private fun endGame() {
        _gameActive.value = false
        println("Peli päättynyt.")
    }

    private fun updateState() {
        _currentCard.value = gameModel.currentCard
        _score.value = gameModel.score
        _gameActive.value = gameModel.gameActive
        _guesses.value = gameModel.guesses
    }

    fun resetGame(deckCount: Int, jokersIncluded: Boolean) {
        startApiGame(deckCount, jokersIncluded)
    }
}
