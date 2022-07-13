package no.nav.endpoints

import kotlinx.coroutines.runBlocking
import no.nav.logic.arbeidsgrad
import no.nav.logic.barnetillegg
import no.nav.logic.inntektsgrunnlag
import no.nav.logic.ytelse

data class PersonInfo (
    val inntekt1: Double,
    val inntekt2: Double,
    val inntekt3: Double,
    val antallBarn: Int = 0,
    val arbeidsgrad: Double,
)

data class Respons (
    var resultat: Double = 0.0,
    val personInfo: PersonInfo,
    var logs: MutableList<String> = mutableListOf(),
)

suspend fun PersonInfo.beregn(): Respons =
    wrapWithOutput(this).apply {
        runBlocking { inntektsgrunnlag() }
        barnetillegg()
        arbeidsgrad()
    }

fun wrapWithOutput(personInfo: PersonInfo): Respons {
    return Respons(personInfo = personInfo)
}
