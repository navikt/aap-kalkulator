package no.nav.logic

import no.nav.endpoints.Respons
import no.nav.GrunnbeloepHistorikk
import java.text.DecimalFormat
import java.time.LocalDate
import kotlin.math.round
import no.nav.forAar

private fun Double.juster(g: Double, historikk: List<GrunnbeloepHistorikk>, soeknadsAar: Int, inntektsAar: Int): Double {
    val grunnbeloepForInntektsAar = historikk.forAar(inntektsAar)!!
    val gammelG = grunnbeloepForInntektsAar.grunnbeloep.toDouble()
    var inntekt = this
        .coerceAtLeast(2*gammelG)
        .coerceAtMost(6*gammelG)

    return (inntekt*historikk.forAar(soeknadsAar)!!.grunnbeloep/grunnbeloepForInntektsAar.gjennomsnittPerAar!!)
        .coerceAtMost(6*g)
}


fun Respons.inntektsjustering(g: Double, historikk: List<GrunnbeloepHistorikk>, inntektsIndeks: Int): Double {
    val naavaerendeAar = LocalDate.now().year
    val inntektsAar = personInfo.sykmeldtAar-(4-inntektsIndeks)
    var inntekt = listOf(personInfo.inntekt1, personInfo.inntekt2, personInfo.inntekt3)[inntektsIndeks-1]
    inntekt = inntekt.juster(g, historikk, naavaerendeAar, inntektsAar)
    logs.add("Justerer inntekt for %s fra %s kr til %s kr"
        .format(inntektsAar, personInfo.inntekt1.tilKr(), inntekt))

    return inntekt
}

fun Respons.inntektsgrunnlag(g: Double, historikk: List<GrunnbeloepHistorikk>) {

    val minsteGrunnlag = 2 * g / 0.66
    val minsteGrunnlagUnder25 = 2 * g
    val inntekt1 = personInfo.inntekt1
    val inntekt2 = personInfo.inntekt2
    val inntekt3 = personInfo.inntekt3

    /*
    val inntekt1 = inntektsjustering(g, historikk, 1)
    val inntekt2 = inntektsjustering(g, historikk, 2)
    val inntekt3 = inntektsjustering(g, historikk, 3)
    */
    val gjennomsnittsInntekt = (inntekt1 + inntekt2 + inntekt3) / 3

    resultat = if (personInfo.under25) {
        inntekt1.coerceAtLeast(minsteGrunnlagUnder25)
    } else {
        inntekt1.coerceAtLeast(minsteGrunnlag)
    }
        .coerceAtLeast(gjennomsnittsInntekt)
        .coerceAtMost(6 * g)

    when (resultat) {
        minsteGrunnlag -> logs.add("Fordi lønnen din er lavere enn grensen for minste utbetaling blir grunnlaget ditt %s kr.".format(round(resultat).tilKr()))
        6 * g -> logs.add("Fordi lønnen din er høyere enn grensen for største utbetaling blir grunnlaget ditt %s kr.".format(round(resultat).tilKr()))
        gjennomsnittsInntekt -> logs.add("Grunnlaget er gjennomsnittet av dine tre siste inntektsår: %s kr.".format(round(resultat).tilKr()))
        minsteGrunnlagUnder25 -> logs.add("Fordi lønnen din er lavere enn grensen for minste utbetaling for de under 25 blir grunnlaget ditt %s kr.".format(round(resultat).tilKr()))
        else -> logs.add("Grunnlaget er basert på ditt siste inntektsår: %s kr.".format(round(resultat).tilKr()))
    }

    val resultatEtterFradrag = resultat * 0.66
    logs.add(
        "Ytelsen utgjør 66%% av grunnlaget, og blir derfor %s kr.".format(
            round(resultatEtterFradrag).tilKr()
        )
    )

    resultat = resultatEtterFradrag
}

fun Double.tilKr() = DecimalFormat("###,###").format(this).replace(',', ' ')
