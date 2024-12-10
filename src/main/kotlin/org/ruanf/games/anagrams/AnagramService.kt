package org.ruanf.games.anagrams


object AnagramService {
    private const val MAX_PHRASE_LENGTH = 100
    /* We are dropping punctuation and symbol characters instead of the usual ^a-z matching,
    this is intended to better support multiple languages. */
    private val PHRASE_SANITIZE_REGEX = "[\\p{P}\\p{S}]".toRegex()

    private val history: HashMap<String, MutableSet<String>> = HashMap()

    fun sanitizePhrase(phrase: String): String {
        //We lowercase the input to avoid having the same phrase added to history with a different case.
        return phrase.replace(PHRASE_SANITIZE_REGEX, "").lowercase()
    }

    fun validatePhrase(phrase: String) {
        require(phrase.length <= MAX_PHRASE_LENGTH) {
            "A phrase cannot exceed $MAX_PHRASE_LENGTH characters."
        }
    }

    fun createSignature(phrase: String): String {
        return phrase.replace(" ", "").lowercase().toCharArray().sortedArray().joinToString(separator="")
    }

    fun getAnagramsOfSubject(subject: Subject): List<String> {
        val (phrase, signature) = subject
        return history[signature]?.filter { it.lowercase() != phrase.lowercase() } ?: emptyList()
    }

    fun addToHistory(subject: Subject) {
        val (phrase, signature) = subject

        if (history.containsKey(signature)) {
            history[signature]?.add(phrase)
        } else {
            history[signature] = mutableSetOf(phrase)
        }
    }
}
