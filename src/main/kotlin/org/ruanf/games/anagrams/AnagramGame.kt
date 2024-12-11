package org.ruanf.games.anagrams

import org.ruanf.games.Emojis
import org.ruanf.games.Game
import org.ruanf.games.inBold
import org.ruanf.games.inCyan
import org.ruanf.games.inGreen
import org.ruanf.games.inRed

class AnagramGame(private val anagramService: AnagramService): Game() {
    private var previousPhrase = ""

    override fun title(): String {
        return "${Emojis.books} Anagram Checker ${Emojis.books}".inCyan()
    }

    override fun nextUserPrompt(): String {
        if (previousPhrase.isBlank()) {
            return "Enter the subject:"
        } else {
            return "Enter an anagram of ${previousPhrase.inBold()}:"
        }
    }

    override fun onUserInputReceived(inputText: String): Boolean {
        if (inputText.isEmpty()) {
            if (previousPhrase.isEmpty()) {
                return false
            }
            previousPhrase = ""
        }

        val anagrams = anagramService.getAnagramsOfPhrase(inputText)

        if (previousPhrase.isNotEmpty() && anagrams.contains(previousPhrase)) {
            val otherPhrases = anagrams.filter { it != previousPhrase }
            if (otherPhrases.isEmpty()) {
                println("${inputText.inGreen()} is an anagram of ${previousPhrase.inGreen()} ${Emojis.party}")
            } else {
                println("${inputText.inGreen()} is an anagram of ${previousPhrase.inGreen()} as well as " +
                        "(${otherPhrases.joinToString(separator = ";").inGreen()}) entered earlier ${Emojis.party}")
            }
        } else if (anagrams.isNotEmpty()) {
            println("${inputText.inGreen()} is an anagram of (${anagrams.joinToString(separator = ";").inGreen()}) " +
                    "entered earlier ${Emojis.party}")
        } else if (previousPhrase.isNotEmpty()) {
            println("${inputText.inRed()} is not an anagram of ${previousPhrase.inRed()}.")
        }

        if (previousPhrase.isBlank()) {
            previousPhrase = inputText
        }

        return true
    }
}

