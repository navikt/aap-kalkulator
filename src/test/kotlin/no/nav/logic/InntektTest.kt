package no.nav.logic

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class InntektTest : FunSpec ({
    context("kalkulere inntektsgrunnlag") {
        test("inntektsgrunnlag med 0 i inntekt") {
            val grunnlag = inntektsgrunnlag(0.0,0.0,0.0)
            grunnlag shouldBe 222954
        }
    }
})