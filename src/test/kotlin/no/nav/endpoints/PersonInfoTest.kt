package no.nav.endpoints

import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import no.nav.hentG
import no.nav.hentGHistorikk
import java.time.LocalDate
import java.util.*

class PersonInfoTest : FunSpec({
    val aar = LocalDate.now().year
    Locale.setDefault(Locale("nb"))
    val g = runBlocking { hentG() }.grunnbeloep
    
    val historikk = runBlocking {
        hentGHistorikk()
    }

    context("Ytelse") {
        test("ytelse med grunnbeløp 6g, 0 barn og 0 arbeidsgrad") {
            val info = PersonInfo(1_000_000.0, 1_000_000.0, 1_000_000.0, 0, 0.0, aar, false, "0")
            val respons = info.calculate(g, historikk)
            "%.2f".format(respons.resultat) shouldBe "441448,92"
            respons.logs.size shouldBe 2
        }
        test("ytelse med grunnbeløp 2g, 7 barn og 0 arbeidsgrad") {
            val info = PersonInfo(0.0, 0.0, 0.0, 7, 0.0, aar, false, "0")
            val respons = info.calculate(g, historikk)
            respons.resultat shouldBe 272094.0
            respons.logs.size shouldBe 3
            respons.logs[0] shouldBe "Fordi lønnen din er lavere enn grensen for minste utbetaling blir grunnlaget ditt 337\u00A0810 kr."
            respons.logs[1] shouldBe "Ytelsen utgjør 66% av grunnlaget, og blir derfor 222 954 kr."
            respons.logs[2] shouldBe "For hvert barn får du 7 020 kr per år. Siden du har 7 barn vil du få 49 140 kr i tillegg."
        }
        test("ytelse med grunnbeløp 2g, 8 barn og 0 arbeidsgrad") {
            val info = PersonInfo(0.0, 0.0, 0.0, 8, 0.0, aar, false, "0")
            val respons = info.calculate(g, historikk)
            "%.2f".format(respons.resultat) shouldBe "279114,00"
            print(respons.logs)
            respons.logs.size shouldBe 3
        }
        test("ytelse med grunnbeløp 2g, 9 barn og 0 arbeidsgrad") {
            val info = PersonInfo(0.0, 0.0, 0.0, 9, 0.0, aar, false, "0")
            val respons = info.calculate(g, historikk)
            "%.2f".format(respons.resultat) shouldBe "286134,00"
            respons.logs.size shouldBe 3
        }
        test("ytelse med grunnbeløp 6g, 22 barn og 0 arbeidsgrad") {
            val info = PersonInfo(1_000_000.0, 1_000_000.0, 1_000_000.0, 22, 0.0, aar, false, "0")
            val respons = info.calculate(g, historikk)
            "%.2f".format(respons.resultat) shouldBe "595888,92"
            respons.logs.size shouldBe 3
        }
        test("ytelse med grunnbeløp 6g, 23 barn og 0 arbeidsgrad") {
            val info = PersonInfo(1_000_000.0, 1_000_000.0, 1_000_000.0, 23, 0.0, aar, false, "0")
            val respons = info.calculate(g, historikk)
            "%.2f".format(respons.resultat) shouldBe "601975,80"
            respons.logs.size shouldBe 3
        }
        test("ytelse med grunnbeløp 6g, 24 barn og 0 arbeidsgrad") {
            val info = PersonInfo(1_000_000.0, 1_000_000.0, 1_000_000.0, 24, 0.0, aar, false, "0")
            val respons = info.calculate(g, historikk)
            "%.2f".format(respons.resultat) shouldBe "601975,80"
            respons.logs.size shouldBe 3
        }
        test("ytelse med grunnbeløp 2g, 0 barn og 40% arbeidsgrad") {
            val info = PersonInfo(0.0, 0.0, 0.0, 0, 40.0, aar, false, "15")
            val respons = info.calculate(g, historikk)
            "%.2f".format(respons.resultat) shouldBe "133772,40"
            respons.logs.size shouldBe 3
        }
        test("ytelse med grunnbeløp 2g, 15 barn og 50% arbeidsgrad") {
            val info = PersonInfo(0.0, 0.0, 0.0, 15, 50.0, aar, false, "18.75")
            val respons = info.calculate(g, historikk)
            "%.2f".format(respons.resultat) shouldBe "152014,09"
            print(respons.resultat)
            respons.logs.size shouldBe 4
            respons.logs[0] shouldBe "Fordi lønnen din er lavere enn grensen for minste utbetaling blir grunnlaget ditt 337 810 kr."
            respons.logs[1] shouldBe "Ytelsen utgjør 66% av grunnlaget, og blir derfor 222 954 kr."
            respons.logs[2] shouldBe "For hvert barn får du 7 020 kr per år. Barnetillegg sammen med ytelsen kan ikke være mer enn 90% av grunnlaget. Derfor får du 81 075 kr i tillegg."
            respons.logs[3] shouldBe "En vanlig arbeidsuke er 37,5 timer. Siden du jobber 18.75 timer i uken blir ytelsen redusert med 50%, fra 304 028 kr til 152 014 kr. "
        }
        test("ytelse med grunnbeløp 6g, 0 barn og 20% arbeidsgrad") {
            val info = PersonInfo(1_000_000.0, 1_000_000.0, 1_000_000.0, 0, 20.0, aar, false, "7.5")
            val respons = info.calculate(g, historikk)
            "%.2f".format(respons.resultat) shouldBe "353159,14"
            respons.logs.size shouldBe 3
        }
        test("ytelse med grunnbeløp 2g, 5 barn og 61% arbeidsgrad") {
            val info = PersonInfo(0.0, 0.0, 0.0, 5, 61.0, aar, false, "22.875")
            val respons = info.calculate(g, historikk)
            respons.resultat shouldBe 0.0
            respons.logs.size shouldBe 1
        }
    }
})
