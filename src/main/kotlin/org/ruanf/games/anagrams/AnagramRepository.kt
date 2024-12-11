package org.ruanf.games.anagrams

class AnagramRepository {
    private val storage: HashMap<String, MutableSet<String>> = HashMap()

    fun findPhrasesForSignature(signature: String): MutableSet<String>? {
        return storage[signature]
    }

    fun save(subject: Subject) {
        val (phrase, signature) = subject
        if (storage.containsKey(signature)) {
            storage[signature]?.add(phrase)
        } else {
            storage[signature] = mutableSetOf(phrase)
        }
    }
}
