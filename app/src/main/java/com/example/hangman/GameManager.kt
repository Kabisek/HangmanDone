// GameManager.kt
package com.example.hangman

import kotlin.random.Random

class GameManager {

    private var lettersUsed: String = ""
    private lateinit var underscoreWord: String
    private lateinit var wordToGuess: String
    private var currentScore = 1000
    private var highScore = 1000
    private val maxTries = 7
    private var currentTries = 0
    private var drawable: Int = R.drawable.game0

    // Define scoring intervals
    private val SCORE_CORRECT_GUESS = 50
    private val SCORE_INCORRECT_GUESS = 100

    // Define starting score
    private val STARTING_SCORE = 1000

    fun startNewGame(): GameState {
        lettersUsed = ""
        currentTries = 0
        drawable = R.drawable.game7
        currentScore = STARTING_SCORE
        val randomIndex = Random.nextInt(0, GameConstants.words.size)
        wordToGuess = GameConstants.words[randomIndex]
        generateUnderscores(wordToGuess)
        return getGameState()
    }

    fun generateUnderscores(word: String) {
        val sb = StringBuilder()
        word.forEach { char ->
            if (char == '/') {
                sb.append('/')
            } else {
                sb.append("_")
            }
        }
        underscoreWord = sb.toString()
    }

    fun play(letter: Char): GameState {
        if (lettersUsed.contains(letter, ignoreCase = true)) {
            return GameState.Running(lettersUsed, underscoreWord, drawable, false)
        }

        lettersUsed += letter
        val indexes = mutableListOf<Int>()

        wordToGuess.forEachIndexed { index, char ->
            if (char.equals(letter, true)) {
                indexes.add(index)
            }
        }

        if (indexes.isEmpty()) {
            currentTries++
            currentScore -= SCORE_INCORRECT_GUESS
        } else {
            indexes.forEach { _ ->
                currentScore += SCORE_CORRECT_GUESS
            }
        }

        underscoreWord = updateUnderscoreWord(indexes, letter)
        drawable = getHangmanDrawable()
        return getGameState()
    }

    private fun updateUnderscoreWord(indexes: List<Int>, letter: Char): String {
        var finalUnderscoreWord = underscoreWord
        indexes.forEach { index ->
            finalUnderscoreWord = finalUnderscoreWord.substring(0, index) + letter + finalUnderscoreWord.substring(index + 1)
        }
        return finalUnderscoreWord
    }

    fun getScore(): Int {
        return currentScore
    }

    fun getHighScore(): Int {
        return highScore
    }

    private fun getHangmanDrawable(): Int {
        return when (currentTries) {
            0 -> R.drawable.game0
            1 -> R.drawable.game1
            2 -> R.drawable.game2
            3 -> R.drawable.game3
            4 -> R.drawable.game4
            5 -> R.drawable.game5
            6 -> R.drawable.game6
            7 -> R.drawable.game7
            else -> R.drawable.game7
        }
    }


    private fun getGameState(): GameState {
        if (underscoreWord.equals(wordToGuess, true)) {
            updateHighScore()
            return GameState.Won(wordToGuess)
        }

        if (currentTries == maxTries) {
            return GameState.Lost(wordToGuess)
        }

        drawable = getHangmanDrawable()
        return GameState.Running(lettersUsed, underscoreWord, drawable, false)
    }

    private fun updateHighScore() {
        if (currentScore > highScore) {
            highScore = currentScore
        }
    }
}
