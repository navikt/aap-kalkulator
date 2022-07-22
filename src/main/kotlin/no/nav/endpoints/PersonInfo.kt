package no.nav.endpoints

import kotlinx.coroutines.runBlocking
import no.nav.G
import no.nav.logic.arbeidsgrad
import no.nav.logic.barnetillegg
import no.nav.logic.inntektsgrunnlag

data class PersonInfo (
    val inntekt1: Double,
    val inntekt2: Double,
    val inntekt3: Double,
    val antallBarn: Int = 0,
    val arbeidsgrad: Double,
    val sykmeldtAar: Int = 0,
)

data class Respons (
    var resultat: Double = 0.0,
    val personInfo: PersonInfo,
    var logs: MutableList<String> = mutableListOf(),
)

fun PersonInfo.calculate(g: Double): Respons =
    wrapWithRespons(this).apply {
        inntektsgrunnlag(g)
        barnetillegg()
        arbeidsgrad()
    }

fun wrapWithRespons(personInfo: PersonInfo): Respons {
    return Respons(personInfo = personInfo)
}
