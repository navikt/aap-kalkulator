package no.nav.logic

import io.kotest.assertions.exceptionToMessage
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import no.nav.endpoints.PersonInfo
import no.nav.endpoints.Respons
import org.junit.jupiter.api.assertThrows
import java.util.*

class ArbeidsgradTest : FunSpec({
    Locale.setDefault(Locale("nb"))
    context("arbeidsgrad") {
        test("arbeidsgrad 40% med 100000 i grunnlag") {
            val output = Respons(100000.0, PersonInfo(0.0, 0.0, 0.0, 0, 40.0), mutableListOf())
            output.arbeidsgrad()
            output.resultat shouldBe 60000.0
            output.logs.first() shouldBe "Siden du jobber 40% vil arbeidsavklaringspengene være redusert med 40%."
        }
        test("arbeidsgrad 70% med 100000 i grunnlag") {
            val output = Respons(100000.0, PersonInfo(0.0, 0.0, 0.0, 0, 70.0), mutableListOf())
            output.arbeidsgrad()
            output.resultat shouldBe 0.0
            output.logs.first() shouldBe "Arbeidsgraden din er høyere enn 60% og du kan derfor ikke få arbeidsavklaringspenger."
        }
        test("arbeidsgrad er -10%") {
            val output = Respons(100000.0, PersonInfo(0.0, 0.0, 0.0, 0, -10.0), mutableListOf())
            val exception = shouldThrow<Exception> {output.arbeidsgrad()}
            exception.message shouldBe("Arbeidsgrad må være større eller lik 0")
        }
    }
})
