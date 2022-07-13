package no.nav.logic

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import no.nav.endpoints.PersonInfo
import no.nav.endpoints.Respons
import kotlin.math.round

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
    context("Barnetillegg med wrapped funksjon"){
        test("Med 0 barn"){
            val respons = Respons(0.0, PersonInfo(0.0,0.0,0.0,0,0.0))
            respons.barnetillegg()
            respons.logs.size shouldBe 0
        }
        test("med 1 barn"){
            val respons = Respons(100000.0, PersonInfo(0.0,0.0,0.0,1,0.0))
            respons.barnetillegg()
            respons.logs.first() shouldBe "For hvert barn du har får du 27 kr per dag. Siden du har 1 barn vil du få 7020 kr i tillegg til ytelsen din."
            respons.resultat shouldBe 107020
        }
        test("8 barn 2g"){
            val respons = Respons(222954.0*0.66, PersonInfo(0.0,0.0,0.0,8,0.0))
            respons.barnetillegg()
            respons.logs.first() shouldBe "maks barn help me pls"
            "%.2f".format(respons.resultat) shouldBe "200658.60"
        }
    }
})
