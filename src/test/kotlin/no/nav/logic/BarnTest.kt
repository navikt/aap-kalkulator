package no.nav.logic

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import no.nav.endpoints.PersonInfo
import no.nav.endpoints.Respons
import java.util.*

class BarnTest : FunSpec({
    Locale.setDefault(Locale("nb"))
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
    context("Barnetillegg med wrapped funksjon"){
        test("Med 0 barn"){
            val respons = Respons(0.0, PersonInfo(0.0,0.0,0.0,0,0.0))
            respons.barnetillegg()
            respons.logs.size shouldBe 0
        }
        test("med 1 barn"){
            val respons = Respons(100000.0, PersonInfo(0.0,0.0,0.0,1,0.0))
            respons.barnetillegg()
            respons.logs.first() shouldBe "For hvert barn får du 27 kr per dag. Siden du har 1 barn vil du få 7\u00A0020 kr i tillegg."
            respons.resultat shouldBe 107020
        }
        test("8 barn 2g"){
            val respons = Respons(222954.0*0.66, PersonInfo(0.0,0.0,0.0,8,0.0))
            respons.barnetillegg()
            respons.logs.first() shouldBe "For hvert barn får du 27 kr per dag."
            respons.logs[1] shouldBe "Barnetillegg sammen med ytelsen kan ikke være mer enn 90% av grunnlaget. Derfor får du 53 509 kr i tillegg."
            "%.2f".format(respons.resultat) shouldBe "200658,60"
        }
    }
})
