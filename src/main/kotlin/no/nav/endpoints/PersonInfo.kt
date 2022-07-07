package no.nav.endpoints

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
    val resultat: Int,
)

suspend fun PersonInfo.beregn(): Respons {
    val grunnlag = inntektsgrunnlag(inntekt1, inntekt2, inntekt3)
    return Respons(ytelse(grunnlag, antallBarn, arbeidsgrad))

}