package org.ruanf.games.anagrams

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

data class SignatureTestCase(val phrase: String, val expectedSignature: String)
data class AnagramTestCase(val subject: String, val anagram: String)

class AnagramsServiceTest {

    private val anagramService: AnagramService = AnagramService

    companion object {
        @JvmStatic
        @Suppress("detekt:UnusedPrivateMember")
        private fun signatureTestCaseProvider(): Stream<SignatureTestCase>? {
            return Stream.of(
                SignatureTestCase("Ac b ", "abc"),
                SignatureTestCase(" A Ë eEt", "aeetë"),
                SignatureTestCase("A_b;-C'|", "abc"),
            )
        }

        @JvmStatic
        @Suppress("detekt:UnusedPrivateMember")
        private fun validAnagramTestCaseProvider(): Stream<AnagramTestCase>? {
            return Stream.of(
                AnagramTestCase("New York Times", "monkeys write"),
                AnagramTestCase("Church of Scientology", "rich-chosen goofy cult"),
                AnagramTestCase("McDonald's restaurants", "Uncle Sam's standard rot"),
                AnagramTestCase("coronavirus", "carnivorous")
            )
        }

        @JvmStatic
        @Suppress("detekt:UnusedPrivateMember")
        private fun invalidAnagramTestCaseProvider(): Stream<AnagramTestCase>? {
            return Stream.of(
                AnagramTestCase("New York Times", "monkeys rite"),
                AnagramTestCase("Church of Scientology", "rich-choen goofy cult"),
                AnagramTestCase("McDonald's restaurants", "Uncle Sm's standard rot"),
                AnagramTestCase("coronavirus", "carnivorou")
            )
        }
    }

    @ParameterizedTest
    @MethodSource("signatureTestCaseProvider")
    fun `given a sanitized subject phrase an expected signature is built`(signatureTestCase: SignatureTestCase) {
        assertThat(anagramService.createSignature(signatureTestCase.phrase).sanitize()
        ).isEqualTo(signatureTestCase.expectedSignature)
    }

    @ParameterizedTest
    @MethodSource("validAnagramTestCaseProvider")
    fun `given a subject phrase and a VALID anagram a match is returned`(anagramTestCase: AnagramTestCase) {
        assertThat(AnagramService.createSignature(anagramTestCase.subject.sanitize())).isEqualTo(
            anagramService.createSignature(anagramTestCase.anagram.sanitize())
        )
    }

    @ParameterizedTest
    @MethodSource("invalidAnagramTestCaseProvider")
    fun `given a subject phrase and INVALID anagram a match is NOT returned`(anagramTestCase: AnagramTestCase) {
        assertThat(AnagramService.createSignature(anagramTestCase.subject.sanitize())).isNotEqualTo(
            anagramService.createSignature(anagramTestCase.anagram.sanitize())
        )
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

fun String.sanitize() = AnagramService.sanitizePhrase(phrase = this)
