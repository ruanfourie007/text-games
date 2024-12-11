package org.ruanf.games.anagrams

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

data class SignatureTestCase(val phrase: String, val expectedSignature: String)
data class AnagramTestCase(val subject: String, val anagram: String)

class AnagramsServiceTest {
    companion object {
        private lateinit var anagramService: AnagramService

        @BeforeAll
        @JvmStatic
        fun setup() {
            anagramService = AnagramService()
        }

        @JvmStatic
        @Suppress("detekt:UnusedPrivateMember")
        private fun signatureTestCaseProvider(): Stream<SignatureTestCase>? {
            return Stream.of(
                SignatureTestCase("Ac b ", "abc"),
                SignatureTestCase(" A Ë eEt", "aeetë"),
                SignatureTestCase("A b C", "abc"),
            )
        }

        @JvmStatic
        @Suppress("detekt:UnusedPrivateMember")
        private fun validAnagramTestCaseProvider(): Stream<AnagramTestCase>? {
            return Stream.of(
                AnagramTestCase("New York Times", "monkeys write"),
                AnagramTestCase("Church of Scientology", "rich chosen goofy cult"),
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
    fun `given a phrase an expected signature is generated for the subject`(signatureTestCase: SignatureTestCase) {
        assertThat(anagramService.createSubject(signatureTestCase.phrase).signature
        ).isEqualTo(signatureTestCase.expectedSignature)
    }

    @ParameterizedTest
    @MethodSource("validAnagramTestCaseProvider")
    fun `given a phrase and a VALID anagram then matching signatures are generated`(anagramTestCase: AnagramTestCase) {
        assertThat(anagramService.createSubject(anagramTestCase.subject).signature).isEqualTo(
            anagramService.createSubject(anagramTestCase.anagram).signature
        )
    }

    @ParameterizedTest
    @MethodSource("invalidAnagramTestCaseProvider")
    fun `given a phrase and an INVALID anagram then matching signatures are NOT generated`
                (anagramTestCase: AnagramTestCase) {
        assertThat(anagramService.createSubject(anagramTestCase.subject).signature).isNotEqualTo(
            anagramService.createSubject(anagramTestCase.anagram).signature
        )
    }

    @Test
    fun `given a phrase is an anagram of subjects entered earlier then the subjects are returned`() {
        anagramService.getAnagramsOfPhrase("a b c")
        anagramService.getAnagramsOfPhrase("b c a")
        anagramService.getAnagramsOfPhrase("d e f")

        assertThat(anagramService.getAnagramsOfPhrase("c a b"))
            .isEqualTo(listOf("a b c", "b c a"))
    }
}
