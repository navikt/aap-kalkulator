package no.nav.logic

import no.nav.getG

suspend fun inntektsgrunnlag(inntekt1: Double, inntekt2: Double, inntekt3: Double): Double {
    val g = getG()
    val gjennomsnittsInntekt = (inntekt1 + inntekt2 + inntekt3)/3

    val grunnlag = inntekt1
        .coerceAtLeast(gjennomsnittsInntekt)
        .coerceAtLeast(2 * g)
        .coerceAtMost(6 * g)

    return grunnlag
}

