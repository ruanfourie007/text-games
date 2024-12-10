package org.ruanf.games

class GameRunner {

    fun start(game: Game) {
        println(game.title())
        game.run()
    }
}
