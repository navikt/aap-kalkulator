package no.nav.logic

import no.nav.endpoints.Respons
import java.text.DecimalFormat
import kotlin.math.ceil

fun Respons.inntektsgrunnlag(g: Double) {

    val minsteGrunnlag = 2 * g / 0.66
    val minsteGrunnlagUnder25 = 2 * g

    val gjennomsnittsInntekt = (personInfo.inntekt1 + personInfo.inntekt2 + personInfo.inntekt3) / 3

    resultat = if (personInfo.under25) {
        personInfo.inntekt1.coerceAtLeast(minsteGrunnlagUnder25)
    } else {
        personInfo.inntekt1.coerceAtLeast(minsteGrunnlag)
    }
        .coerceAtLeast(gjennomsnittsInntekt)
        .coerceAtMost(6 * g)

    when (resultat) {
        minsteGrunnlag -> logs.add("Fordi lønnen din er lavere enn grensen for minste utbetaling blir grunnlaget ditt %s kr.".format(ceil(resultat).tilKr()))
        6 * g -> logs.add("Fordi lønnen din er høyere enn grensen for største utbetaling blir grunnlaget ditt %s kr.".format(ceil(resultat).tilKr()))
        gjennomsnittsInntekt -> logs.add("Grunnlaget er gjennomsnittet av dine tre siste inntektsår: %s kr.".format(ceil(resultat).tilKr()))
        minsteGrunnlagUnder25 -> logs.add("Fordi lønnen din er lavere enn grensen for minste utbetaling for de under 25 blir grunnlaget ditt %s kr.".format(ceil(resultat).tilKr()))
        else -> logs.add("Grunnlaget er basert på ditt siste inntektsår: %s kr.".format(ceil(resultat).tilKr()))
    }

    val resultatEtterFradrag = resultat * 0.66
    logs.add(
        "Ytelsen etter utregning av grunnlag er %s kr. Ytelsen utgjør 66%% av %s kr.".format(
            ceil(resultatEtterFradrag).tilKr(),
            ceil(resultat).tilKr()
        )
    )

    resultat = resultatEtterFradrag
}

fun Double.tilKr() = DecimalFormat("###,###").format(this).replace(',', ' ')
