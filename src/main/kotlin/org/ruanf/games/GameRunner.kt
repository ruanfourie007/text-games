package org.ruanf.games

class GameRunner {

    fun run(game: Game) {

        println(game.title())

        var shouldKeepRunning = true
        while (shouldKeepRunning) {
            print(game.nextUserPrompt())

            try {
                shouldKeepRunning = game.onUserInputReceived(readln())
            } catch (exception: IllegalArgumentException) {
                println("Whoops, we weren't expecting that: ".inCyan())
                println(exception.message?.inRed())
            }
        }
    }
}
