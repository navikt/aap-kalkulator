package no.nav

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GTest: FunSpec ({
    context("Hente grunnbeløp"){
        test("Grunnbeløp er 111477"){
            val G = getG()
            G shouldBe 111477
        }
    }
})