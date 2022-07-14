package no.nav.logic

import no.nav.endpoints.Respons
import no.nav.getG
import java.text.DecimalFormat
import kotlin.math.ceil

suspend fun inntektsgrunnlag(inntekt1: Double, inntekt2: Double, inntekt3: Double): Double {
    val g = getG()
    val gjennomsnittsInntekt = (inntekt1 + inntekt2 + inntekt3)/3

    val grunnlag = inntekt1
        .coerceAtLeast(gjennomsnittsInntekt)
        .coerceAtLeast(2 * g)
        .coerceAtMost(6 * g)

    return grunnlag
}

suspend fun Respons.inntektsgrunnlag(){
    val g = getG()
    val gjennomsnittsInntekt = (personInfo.inntekt1 + personInfo.inntekt2 + personInfo.inntekt3)/3

    resultat = personInfo.inntekt1
        .coerceAtLeast(gjennomsnittsInntekt)
        .coerceAtLeast(2 * g)
        .coerceAtMost(6 * g)

    when(resultat) {
        2*g -> logs.add("Siden lønnen din er lavere enn grensen for minste utbetaling blir grunnlaget ditt %s kr.".format(DecimalFormat("###,###").format(g*2).replace(',', ' ')))
        6*g -> logs.add("Siden lønnen din er høyere enn grensen for største utbetaling blir grunnlaget ditt %s kr.".format(DecimalFormat("###,###").format(g*6).replace(',', ' ')))
        gjennomsnittsInntekt -> logs.add("Grunnlaget er basert på dine tre siste inntektsår.")
        else -> logs.add("Grunnlaget er basert på ditt siste inntektsår.")
    }

    resultat *= 0.66
    logs.add("Ytelsen etter utregning av grunnlag er %s kr. Ytelsen utgjør 66%% av grunnlaget.".format(DecimalFormat("###,###").format(
        ceil(resultat)).replace(',', ' ')))
}


