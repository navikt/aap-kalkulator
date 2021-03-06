package no.nav.endpoints

import no.nav.logic.arbeidsgrad
import no.nav.logic.barnetillegg
import no.nav.logic.inntektsgrunnlag
import no.nav.GrunnbeloepHistorikk

data class PersonInfo(
    val inntekt1: Double,
    val inntekt2: Double,
    val inntekt3: Double,
    val antallBarn: Int = 0,
    val arbeidsgrad: Double,
    val sykmeldtAar: Int = 0,
    val over25: Boolean,
    val arbeidstimer: String = "0",
)

data class Respons(
    var resultat: Double = 0.0,
    val personInfo: PersonInfo,
    var logs: MutableList<String> = mutableListOf(),
)

fun PersonInfo.calculate(g: Double, historikk: List<GrunnbeloepHistorikk>): Respons =
    wrapWithRespons(this).apply {
        inntektsgrunnlag(g, historikk)
        barnetillegg()
        arbeidsgrad()
    }

fun wrapWithRespons(personInfo: PersonInfo): Respons {
    return Respons(personInfo = personInfo)
}
