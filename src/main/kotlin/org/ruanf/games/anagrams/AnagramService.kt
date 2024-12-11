package org.ruanf.games.anagrams

data class Subject(val phrase: String, val signature: String)

class AnagramService {
    companion object {
        private val PHRASE_SANITIZE_REGEX = "[\\p{P}\\p{S}]".toRegex()
        private const val MAX_PHRASE_LENGTH = 3

        private val repository = AnagramRepository()
    }

    fun createSubject(phrase: String): Subject {
        val sanitizedPhrase = phrase.replace(PHRASE_SANITIZE_REGEX, "").lowercase()

        require(sanitizedPhrase.length <= MAX_PHRASE_LENGTH) {
            "A phrase cannot exceed $MAX_PHRASE_LENGTH characters."
        }

        return Subject(
            sanitizedPhrase,
            sanitizedPhrase.replace(" ", "").lowercase().toCharArray().sortedArray().joinToString(separator="")
        )
    }

    fun getAnagramsOfPhrase(phrase: String): List<String> {
        val subject = createSubject(phrase)
        repository.save(subject)
        return repository.findPhrasesForSignature(subject.signature)?.filter {
            it.lowercase() != subject.phrase.lowercase()
        } ?: emptyList()
    }
}
