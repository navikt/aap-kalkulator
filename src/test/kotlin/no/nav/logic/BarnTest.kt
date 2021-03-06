package no.nav.logic

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import no.nav.endpoints.PersonInfo
import no.nav.endpoints.Respons
import java.time.LocalDate
import java.util.*

class BarnTest : FunSpec({
    val aar = LocalDate.now().year
    Locale.setDefault(Locale("nb"))
    context("barnetillegg") {
        test("med 0 barn") {
            val barnetillegg = barnetillegg(0)
            barnetillegg shouldBe 0
        }
        test("med 1 barn") {
            val barnetillegg = barnetillegg(1)
            barnetillegg shouldBe 7020
        }
        test("med mange barn") {
            val barnetillegg = barnetillegg(8)
            barnetillegg shouldBe 56160
        }
    }
    context("Barnetillegg med wrapped funksjon") {
        test("Med 0 barn") {
            val respons = Respons(0.0, PersonInfo(0.0, 0.0, 0.0, 0, 0.0, aar, false, "0"))
            respons.barnetillegg()
            respons.logs.size shouldBe 0
        }
        test("med 1 barn") {
            val respons = Respons(100000.0, PersonInfo(0.0, 0.0, 0.0, 1, 0.0, aar, false, "0"))
            respons.barnetillegg()
            respons.logs.first() shouldBe "For hvert barn får du 7 020 kr per år. Siden du har 1 barn vil du få 7 020 kr i tillegg. Dette blir til sammen <strong>107 020 kr</strong>."
            respons.resultat shouldBe 107020
        }
        test("maks barn") {
            val respons = Respons(222954.0 * (2.0/3.0), PersonInfo(0.0, 0.0, 0.0, 12, 0.0, aar, false, "0"))
            respons.barnetillegg()
            respons.logs.first() shouldBe "For hvert barn får du 7 020 kr per år. Barnetillegg sammen med ytelsen kan ikke være mer enn 90% av grunnlaget. Derfor får du 52 023 kr i tillegg. Dette blir til sammen <strong>200 659 kr</strong>."
            respons.resultat.tilKr() shouldBe "200 659"
        }
    }
})
