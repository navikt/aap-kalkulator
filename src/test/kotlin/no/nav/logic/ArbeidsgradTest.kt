package no.nav.logic

import io.kotest.assertions.exceptionToMessage
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows

class ArbeidsgradTest : FunSpec({

    context("arbeidsgrad") {
        test("arbeidsgrad 40% med 100000 i grunnlag") {
            val arbeidsytelse = arbeidsgrad(100000.0, 40.0)
            arbeidsytelse shouldBe 60000.0
        }
        test("arbeidsgrad 70% med 100000 i grunnlag") {
            val arbeidsytelse = arbeidsgrad(100000.0, 70.0)
            arbeidsytelse shouldBe 0.0
        }
        test("arbeidsgrad er -10%") {
            val exception = shouldThrow<Exception> { arbeidsgrad(100000.0, -10.0)}
            exception.message shouldBe("Arbeidsgrad må være større eller lik 0")
        }
    }
})
