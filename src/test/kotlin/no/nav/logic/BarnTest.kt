package no.nav.logic

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class BarnTest : FunSpec({

    context("barnetillegg") {
        test("med 0 barn") {
            val barnetillegg = barnetillegg(0)
            barnetillegg shouldBe 0
        }
        test("med 1 barn"){
            val barnetillegg = barnetillegg(1)
            barnetillegg shouldBe 7020
        }
        test("med mange barn"){
            val barnetillegg = barnetillegg(8)
            barnetillegg shouldBe 56160
        }

    }

})
