package org.ruanf.games.anagrams

import org.ruanf.Game
import kotlin.system.exitProcess

class AnagramGame: Game {

    override fun title(): String {
        return "${Emojis.books} Anagram Checker ${Emojis.books}".inCyan()
    }

    override fun run() {
        var previousPhrase = ""

        while (true) {

            if (previousPhrase.isBlank()) {
                print("Enter the subject:")
            } else {
                print("Enter an anagram of ${previousPhrase.inBold()}:")
            }

            val phrase = AnagramService.sanitizePhrase(readln())

            try {
                AnagramService.validatePhrase(phrase)
            } catch (exception: IllegalArgumentException) {
                println("Whoops, something went wrong: ".inCyan())
                println(exception.message?.inRed())
                continue
            }

            if (phrase.isEmpty()) {
                if (previousPhrase.isEmpty()) {
                    println("Thanks for playing!".inCyan())
                    exitProcess(0)
                }
                previousPhrase = ""
                continue
            }

            if (phrase == previousPhrase) continue

            val subject = Subject(phrase)

            AnagramService.addToHistory(subject)

            val anagrams = AnagramService.getAnagramsOfSubject(subject)

            if (previousPhrase.isNotEmpty() && anagrams.contains(previousPhrase)) {
                val otherPhrases = anagrams.filter { it != previousPhrase }
                if (otherPhrases.isEmpty()) {
                    println("${phrase.inGreen()} is an anagram of ${previousPhrase.inGreen()} ${Emojis.party}")
                } else {
                    println("${phrase.inGreen()} is an anagram of ${previousPhrase.inGreen()} as well as (${otherPhrases.joinToString(separator = ";").inGreen()}) entered earlier ${Emojis.party}")
                }
            } else if (anagrams.isNotEmpty()) {
                println("${phrase.inGreen()} is an anagram of (${anagrams.joinToString(separator = ";").inGreen()}) entered earlier ${Emojis.party}")
            } else if (previousPhrase.isNotEmpty()) {
                println("${phrase.inRed()} is not an anagram of ${previousPhrase.inRed()}.")
            }

            if (previousPhrase.isBlank()) {
                previousPhrase = phrase
            }
        }
    }
}

class Subject(private val phrase: String) {
    private val signature = AnagramService.createSignature(phrase)
    operator fun component1(): String {
        return phrase
    }
    operator fun component2(): String {
        return signature
    }
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