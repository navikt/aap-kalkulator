package no.nav

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.util.*

class GTest : FunSpec({
    Locale.setDefault(Locale("nb"))
    context("Hente grunnbeløp") {
        test("Grunnbeløp er 111477") {
            val G = hentG().grunnbeloep
            G shouldBe 111477
        }
    }
})
