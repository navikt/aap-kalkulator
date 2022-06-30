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
        test("inntektsgrunnlag med en million i inntekt"){
            val enMill = 1_000_000.0
            val grunnlag = inntektsgrunnlag(enMill, enMill, enMill)
            grunnlag shouldBe 668862
        }
        test("inntektsgrunnlag med variert lønn"){
            val grunnlag = inntektsgrunnlag(350000.0, 450000.0, 550000.0)
            grunnlag shouldBe 450000
        }
        test("inntektsgrunnlag med mest lønn siste år"){
            val grunnlag = inntektsgrunnlag(600000.0, 100000.0, 200000.0)
            grunnlag shouldBe 600000
        }


    }
})