package no.nav.logic

import no.nav.endpoints.Respons
import no.nav.hentG
import java.text.DecimalFormat
import kotlin.math.ceil

suspend fun Respons.inntektsgrunnlag(){
    val g = hentG()
    val gjennomsnittsInntekt = (personInfo.inntekt1 + personInfo.inntekt2 + personInfo.inntekt3)/3

    resultat = personInfo.inntekt1
        .coerceAtLeast(gjennomsnittsInntekt)
        .coerceAtLeast(2 * g)
        .coerceAtMost(6 * g)

    when(resultat) {
        2*g -> logs.add("Fordi lønnen din er lavere enn grensen for minste utbetaling blir grunnlaget ditt %s kr.".format(ceil(resultat).tilKr()))
        6*g -> logs.add("Fordi lønnen din er høyere enn grensen for største utbetaling blir grunnlaget ditt %s kr.".format(ceil(resultat).tilKr()))
        gjennomsnittsInntekt -> logs.add("Grunnlaget er gjennomsnittet av dine tre siste inntektsår: %s kr.".format(ceil(resultat).tilKr()))
        else -> logs.add("Grunnlaget er basert på ditt siste inntektsår: %s kr.".format(ceil(resultat).tilKr()))
    }

    resultat *= 0.66
    logs.add("Ytelsen etter utregning av grunnlag er %s kr. Ytelsen utgjør 66%% av %s kr.".format(ceil(resultat).tilKr(),
        ytelseTilGrunnlag((resultat)).tilKr()
    ))
}

fun Double.tilKr() = DecimalFormat("###,###").format(this).replace(',', ' ')

