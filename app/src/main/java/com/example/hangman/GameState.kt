package com.example.hangman

sealed class GameState {
    class Running(val lettersUsed: String,
                  val underscoreWord: String,
                  val drawable: Int,
                  val correctGuess: Boolean) : GameState()
    class Lost(val wordToGuess: String) : GameState()
    class Won(val wordToGuess: String) : GameState()
}

