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
        2*g -> logs.add("Fordi lønnen din er lavere enn grensen for minste utbetaling blir grunnlaget ditt %s kr.".format(ceil(resultat).toKr()))
        6*g -> logs.add("Fordi lønnen din er høyere enn grensen for største utbetaling blir grunnlaget ditt %s kr.".format(ceil(resultat).toKr()))
        gjennomsnittsInntekt -> logs.add("Grunnlaget er gjennomsnittet av dine tre siste inntektsår: %s kr.".format(ceil(resultat).toKr()))
        else -> logs.add("Grunnlaget er basert på ditt siste inntektsår: %s kr.".format(ceil(resultat).toKr()))
    }

    resultat *= 0.66
    logs.add("Ytelsen etter utregning av grunnlag er %s kr. Ytelsen utgjør 66%% av %s kr.".format(ceil(resultat).toKr(),
        ytelseTilGrunnlag((resultat)).toKr()
    ))
}

fun Double.toKr() = DecimalFormat("###,###").format(this).replace(',', ' ')

