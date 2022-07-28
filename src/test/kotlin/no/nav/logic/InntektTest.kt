package no.nav.logic

import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import no.nav.endpoints.PersonInfo
import no.nav.endpoints.Respons
import no.nav.hentG
import no.nav.hentGHistorikk
import java.time.LocalDate
import java.util.*

class InntektTest : FunSpec({
    val aar = LocalDate.now().year
    Locale.setDefault(Locale("nb"))
    val g = runBlocking {
        hentG()
    }.grunnbeloep

    val historikk = runBlocking {
        hentGHistorikk()
    }

    context("kalkulere inntektsgrunnlag") {
        test("inntektsgrunnlag med 0 i inntekt") {
            val grunnlag = Respons(personInfo = PersonInfo(0.0, 0.0, 0.0, 0, 0.0, aar, true, "0"))
            runBlocking { grunnlag.inntektsgrunnlag(g, historikk) }
            grunnlag.resultat.tilKr() shouldBe "225 206"
            grunnlag.logs.size shouldBe 2
            grunnlag.logs.first() shouldBe "Fordi lønnen din er lavere enn grensen for minste utbetaling blir grunnlaget ditt 337 809 kr."
        }
        test("inntektsgrunnlag med en million i inntekt") {
            val enMill = 1_000_000.0
            val grunnlag = Respons(personInfo = PersonInfo(enMill, enMill, enMill, 0, 0.0, aar, true, "0"))
            runBlocking { grunnlag.inntektsgrunnlag(g, historikk) }
            grunnlag.resultat.tilKr() shouldBe "445 908"
            grunnlag.logs.size shouldBe 2
            grunnlag.logs.first() shouldBe "Fordi lønnen din er høyere enn grensen for største utbetaling blir grunnlaget ditt %s kr.".format(
                (ytelseTilGrunnlag(445908.0)).tilKr()
            )
        }
        test("inntektsgrunnlag med variert lønn") {
            val grunnlag = Respons(personInfo = PersonInfo(350000.0, 450000.0, 550000.0, 0, 0.0, aar, true, "0"))
            runBlocking { grunnlag.inntektsgrunnlag(g, historikk) }
            grunnlag.resultat.tilKr() shouldBe "331 146"
            grunnlag.logs.size shouldBe 2
            grunnlag.logs.first() shouldBe "Grunnlaget er gjennomsnittet av dine tre siste inntektsår: %s kr.".format((496719.0).tilKr())
        }
        test("inntektsgrunnlag med mest lønn siste år") {
            val grunnlag = Respons(personInfo = PersonInfo(600000.0, 100000.0, 200000.0, 0, 0.0, aar, true, "0"))
            runBlocking { grunnlag.inntektsgrunnlag(g, historikk) }
            grunnlag.resultat.tilKr() shouldBe "425 826"
            grunnlag.logs.size shouldBe 2
            grunnlag.logs.first() shouldBe "Grunnlaget er basert på ditt siste inntektsår: %s kr.".format((638739.0).tilKr())
            grunnlag.logs.last() shouldBe "Ytelsen utgjør 66% av grunnlaget, og blir derfor 425 826 kr."
        }
        test("Inntektsgrunn med minstelønn og under 25år") {
            val grunnlag = Respons(personInfo = PersonInfo(0.0, 0.0, 0.0, 0, 0.0, aar, false, "0"))
            runBlocking { grunnlag.inntektsgrunnlag(g, historikk) }
            grunnlag.resultat.tilKr() shouldBe "151 025"
            grunnlag.logs.size shouldBe 2
            grunnlag.logs.first() shouldBe "Grunnlaget er basert på ditt siste inntektsår: 226 537 kr."
        }

        test("Inntektsgrunnlag med oppjustering fra 2018") {
            val grunnlag = Respons(personInfo = PersonInfo(400000.0, 400000.0, 400000.0, 0, 0.0, 2021, false, "0"))
            runBlocking { grunnlag.inntektsgrunnlag(g, historikk) }
            grunnlag.resultat.tilKr() shouldBe "301 915"
            grunnlag.logs.size shouldBe 2
        }

    }
})
