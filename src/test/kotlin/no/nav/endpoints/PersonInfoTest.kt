package no.nav.endpoints

import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import no.nav.hentG
import no.nav.hentGHistorikk
import no.nav.logic.tilKr
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
            val info = PersonInfo(1_000_000.0, 1_000_000.0, 1_000_000.0, 0, 0.0, aar, true, "0")
            val respons = info.calculate(g, historikk)
            respons.resultat shouldBe 445908
            respons.logs.size shouldBe 2
        }
        test("ytelse med grunnbeløp 2g, 7 barn og 0 arbeidsgrad") {
            val info = PersonInfo(0.0, 0.0, 0.0, 7, 0.0, aar, true, "0")
            val respons = info.calculate(g, historikk)
            respons.resultat.tilKr() shouldBe "274 346"
            respons.logs.size shouldBe 3
            respons.logs[0] shouldBe "Fordi lønnen din er lavere enn grensen for minste utbetaling blir grunnlaget ditt 337\u00A0809 kr."
            respons.logs[1] shouldBe "Ytelsen utgjør 66% av grunnlaget, og blir derfor 225 206 kr."
            respons.logs[2] shouldBe "For hvert barn får du 7 020 kr per år. Siden du har 7 barn vil du få 49 140 kr i tillegg."
        }
        test("ytelse med grunnbeløp 2g, 8 barn og 0 arbeidsgrad") {
            val info = PersonInfo(0.0, 0.0, 0.0, 8, 0.0, aar, true, "0")
            val respons = info.calculate(g, historikk)
            respons.resultat.tilKr() shouldBe "281 366"
            print(respons.logs)
            respons.logs.size shouldBe 3
        }

        test("ytelse med grunnbeløp 6g, 22 barn og 0 arbeidsgrad") {
            val info = PersonInfo(1_000_000.0, 1_000_000.0, 1_000_000.0, 22, 0.0, aar, true, "0")
            val respons = info.calculate(g, historikk)
            respons.resultat.tilKr() shouldBe "600 348"
            respons.logs.size shouldBe 3
        }
        test("ytelse med grunnbeløp 6g, 23 barn og 0 arbeidsgrad") {
            val info = PersonInfo(1_000_000.0, 1_000_000.0, 1_000_000.0, 23, 0.0, aar, true, "0")
            val respons = info.calculate(g, historikk)
            respons.resultat.tilKr() shouldBe "601 976"
            respons.logs.size shouldBe 3
        }
        test("ytelse med grunnbeløp 6g, 24 barn og 0 arbeidsgrad") {
            val info = PersonInfo(1_000_000.0, 1_000_000.0, 1_000_000.0, 24, 0.0, aar, true, "0")
            val respons = info.calculate(g, historikk)
            respons.resultat.tilKr() shouldBe "601 976"
            respons.logs.size shouldBe 3
        }
        test("ytelse med grunnbeløp 2g, 0 barn og 40% arbeidsgrad") {
            val info = PersonInfo(0.0, 0.0, 0.0, 0, 40.0, aar, true, "15")
            val respons = info.calculate(g, historikk)
            respons.resultat.tilKr() shouldBe "135 124"
            respons.logs.size shouldBe 3
        }
        test("ytelse med grunnbeløp 2g, 15 barn og 50% arbeidsgrad") {
            val info = PersonInfo(0.0, 0.0, 0.0, 15, 50.0, aar, true, "18.75")
            val respons = info.calculate(g, historikk)
            respons.resultat.tilKr() shouldBe "152 014"
            print(respons.resultat)
            respons.logs.size shouldBe 4
            respons.logs[0] shouldBe "Fordi lønnen din er lavere enn grensen for minste utbetaling blir grunnlaget ditt 337 809 kr."
            respons.logs[1] shouldBe "Ytelsen utgjør 66% av grunnlaget, og blir derfor 225 206 kr."
            respons.logs[2] shouldBe "For hvert barn får du 7 020 kr per år. Barnetillegg sammen med ytelsen kan ikke være mer enn 90% av grunnlaget. Derfor får du 78 823 kr i tillegg."
            respons.logs[3] shouldBe "En vanlig arbeidsuke er 37,5 timer. Siden du jobber 18.75 timer i uken blir ytelsen redusert med 50%, fra 304 028 kr til 152 014 kr. "
        }
        test("ytelse med grunnbeløp 6g, 0 barn og 20% arbeidsgrad") {
            val info = PersonInfo(1_000_000.0, 1_000_000.0, 1_000_000.0, 0, 20.0, aar, true, "7.5")
            val respons = info.calculate(g, historikk)
            respons.resultat.tilKr() shouldBe "356 726"
            respons.logs.size shouldBe 3
        }
        test("ytelse med grunnbeløp 2g, 5 barn og 61% arbeidsgrad") {
            val info = PersonInfo(0.0, 0.0, 0.0, 5, 61.0, aar, true, "22.875")
            val respons = info.calculate(g, historikk)
            respons.resultat shouldBe 0.0
            respons.logs.size shouldBe 1
        }
    }
})
