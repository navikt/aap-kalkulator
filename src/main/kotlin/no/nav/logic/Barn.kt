package no.nav.logic

import no.nav.endpoints.Respons
import kotlin.math.ceil


const val satsPerBarnPerDag:Int = 27

fun barnetillegg(antallBarn: Int):Int = antallBarn* satsPerBarnPerDag* ytelsesdager

fun Respons.barnetillegg(){
    if(personInfo.antallBarn == 0){
        return
    }

    val maksBarnetillegg = ytelseTilGrunnlag(resultat) * 0.9
    val faktiskBarnetillegg = barnetillegg(personInfo.antallBarn)
    val muligBarnetillegg = faktiskBarnetillegg + resultat
    val maksBarnetilleggUtenGrunnlag = ceil(maksBarnetillegg - resultat)

    resultat = muligBarnetillegg.coerceAtMost(maksBarnetillegg)
    when(resultat) {
        muligBarnetillegg -> logs.add("For hvert barn får du %s kr per dag. Siden du har %s barn vil du få %s kr i tillegg.".format(
            satsPerBarnPerDag, personInfo.antallBarn, faktiskBarnetillegg))
        else -> {logs.add("For hvert barn får du %s kr per dag.".format(
            satsPerBarnPerDag))
        logs.add("Barnetillegg sammen med ytelsen kan ikke være mer enn 90%% av grunnlaget. Derfor får du %s kr i tillegg.".format(maksBarnetilleggUtenGrunnlag))}
    }
}