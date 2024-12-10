package org.ruanf.anagrams

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.ruanf.games.AnagramService
import org.ruanf.games.Subject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

data class SignatureTestCase(val phrase: String, val expectedSignature: String)

class AnagramsServiceTest {

    private val anagramService: AnagramService = AnagramService

    companion object {
        @JvmStatic
        private fun signatureTestCaseProvider(): Stream<SignatureTestCase>? {
            return Stream.of(
                SignatureTestCase("Ac b ", "abc"),
                SignatureTestCase(" A Ë eEt", "aeetë"),
                SignatureTestCase("A_b;-C'|", "abc"),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("signatureTestCaseProvider")
    fun `given a sanitized subject phrase an expected signature is built`(signatureTestCase: SignatureTestCase) {
        assertThat(anagramService.createSignature(
            anagramService.sanitizePhrase(signatureTestCase.phrase)
        )).isEqualTo(signatureTestCase.expectedSignature)
    }

    @Test
    fun `given an invalid phrase provided to validatePhrase an IllegalArgumentException is thrown`() {
        assertThrows<IllegalArgumentException> { anagramService.validatePhrase("".padEnd(101, 'A')) }
    }

    @Test
    fun `given a phrase is an anagram of phrases entered earlier the matches are returned`() {
        anagramService.addToHistory(Subject("A B C"))
        anagramService.addToHistory(Subject("B C A"))
        anagramService.addToHistory(Subject("D E F"))

        assertThat(anagramService.getAnagramsOfSubject(Subject("C A B")))
            .isEqualTo(listOf("A B C", "B C A"))
    }
}