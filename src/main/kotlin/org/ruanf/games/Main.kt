package org.ruanf.games

import org.ruanf.games.anagrams.AnagramGame
import org.ruanf.games.anagrams.AnagramService

fun main() {
    //A menu system could be added to navigate games added in the future.
    GameRunner().run(AnagramGame(AnagramService()))
    println("Thanks for playing!".inCyan())
}

object Emojis {
    const val books = "\uD83D\uDCDA"
    const val party = "\uD83C\uDF89"
}

fun String.inGreen() : String {
    return "\u001B[92m$this\u001B[39m"
}

fun String.inRed() : String {
    return "\u001B[91m$this\u001B[39m"
}

fun String.inCyan() : String {
    return "\u001B[96m$this\u001B[39m"
}

fun String.inBold() : String {
    return "\u001B[1m$this\u001B[0m"
}
