package org.ruanf.games

abstract class Game {
    abstract fun title(): String

    abstract fun nextUserPrompt(): String

    // Return false if the game should exit.
    abstract fun onUserInputReceived(inputText: String): Boolean

}

