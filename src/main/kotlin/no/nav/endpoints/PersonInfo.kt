package no.nav.endpoints

import no.nav.logic.inntektsgrunnlag
import no.nav.logic.ytelse

data class PersonInfo (
    val inntekt1: Double,
    val inntekt2: Double,
    val inntekt3: Double,
)

suspend fun PersonInfo.beregn(): Int {
    val grunnlag = inntektsgrunnlag(inntekt1, inntekt2, inntekt3)
    return ytelse(grunnlag)

}