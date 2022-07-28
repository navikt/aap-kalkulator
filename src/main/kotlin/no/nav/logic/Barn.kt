package no.nav.logic

import no.nav.endpoints.Respons
import java.text.DecimalFormat
import kotlin.math.ceil

const val satsPerBarnPerDag: Int = 27

fun barnetillegg(antallBarn: Int): Double = (antallBarn * satsPerBarnPerDag * ytelsesdager).toDouble()

fun Respons.barnetillegg() {
    if (personInfo.antallBarn == 0) {
        return
    }

    val maksBarnetillegg = ytelseTilGrunnlag(resultat) * 0.9
    val faktiskBarnetillegg = barnetillegg(personInfo.antallBarn)
    val muligBarnetillegg = faktiskBarnetillegg + resultat
    val maksBarnetilleggUtenGrunnlag = ceil(maksBarnetillegg - resultat)

    resultat = muligBarnetillegg.coerceAtMost(maksBarnetillegg)

    when (resultat) {
        muligBarnetillegg -> logs.add(
           "For hvert barn får du %s kr per år. Siden du har %s barn vil du få %s kr i tillegg. Dette blir til sammen <strong>%s kr</strong>.".format(
                barnetillegg(1).tilKr(), personInfo.antallBarn, faktiskBarnetillegg.tilKr(), resultat.tilKr()
            )
        )
        else -> {

            logs.add("For hvert barn får du %s kr per år. Barnetillegg sammen med ytelsen kan ikke være mer enn 90%% av grunnlaget. Derfor får du %s kr i tillegg. Dette blir til sammen <strong>%s kr</strong>."
                .format(barnetillegg(1).tilKr(), maksBarnetilleggUtenGrunnlag.tilKr(), resultat.tilKr()))
        }
    }
}
