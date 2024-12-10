package org.ruanf

class GameRunner {

    fun start(game: Game) {
        println(game.title())
        game.run()
    }
}