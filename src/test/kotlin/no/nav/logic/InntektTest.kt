package no.nav.logic

import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import no.nav.endpoints.PersonInfo
import no.nav.endpoints.Respons
import java.text.DecimalFormat

class InntektTest : FunSpec ({
    context("kalkulere inntektsgrunnlag") {
        test("inntektsgrunnlag med 0 i inntekt") {
            val grunnlag = Respons(personInfo = PersonInfo(0.0,0.0,0.0,0,0.0))
            runBlocking { grunnlag.inntektsgrunnlag() }
            grunnlag.resultat shouldBe 147149.64
            grunnlag.logs.size shouldBe 2
            grunnlag.logs.first() shouldBe "Siden lønnen din er lavere enn grensen for minste utbetaling blir grunnlaget ditt %s kr.".format(DecimalFormat("###,###").format(ytelseTilGrunnlag(147149.64)).replace(",", " "))
        }
        test("inntektsgrunnlag med en million i inntekt"){
            val enMill = 1_000_000.0
            val grunnlag = Respons(personInfo = PersonInfo(enMill, enMill, enMill,0,0.0))
            runBlocking { grunnlag.inntektsgrunnlag() }
            "%.2f".format(grunnlag.resultat) shouldBe "441448.92"
            grunnlag.logs.size shouldBe 2
            grunnlag.logs.first() shouldBe "Siden lønnen din er høyere enn grensen for største utbetaling blir grunnlaget ditt %s kr.".format(
                DecimalFormat("###,###").format(ytelseTilGrunnlag(441448.92)).replace(",", " ")
            )
        }
        test("inntektsgrunnlag med variert lønn"){
            val grunnlag = Respons(personInfo = PersonInfo(350000.0, 450000.0, 550000.0,0,0.0))
            runBlocking { grunnlag.inntektsgrunnlag() }
            grunnlag.resultat shouldBe 297000.0
            grunnlag.logs.size shouldBe 2
            grunnlag.logs.first() shouldBe "Grunnlaget er basert på dine tre siste inntektsår."
        }
        test("inntektsgrunnlag med mest lønn siste år"){
            val grunnlag = Respons(personInfo = PersonInfo(600000.0, 100000.0, 200000.0,0,0.0))
            runBlocking { grunnlag.inntektsgrunnlag() }
            grunnlag.resultat shouldBe 396000.0
            grunnlag.logs.size shouldBe 2
            grunnlag.logs.first() shouldBe "Grunnlaget er basert på ditt siste inntektsår."
            grunnlag.logs.last() shouldBe "Ytelsen etter utregning av grunnlag er 396 000 kr. Ytelsen utgjør 66% av grunnlaget."
        }


    }
})