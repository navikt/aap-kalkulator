package no.nav.logic

import no.nav.endpoints.Respons


const val satsPerBarnPerDag:Int = 27

fun barnetillegg(antallBarn: Int):Int = antallBarn* satsPerBarnPerDag* ytelsesdager

fun Respons.barnetillegg(){
    if(personInfo.antallBarn == 0){
        return
    }

    val maksBarnetillegg = ytelseTilGrunnlag(resultat) * 0.9
    val faktiskBarnetillegg = barnetillegg(personInfo.antallBarn)
    val muligBarnetillegg = faktiskBarnetillegg + resultat

    resultat = muligBarnetillegg.coerceAtMost(maksBarnetillegg)
    when(resultat) {
        muligBarnetillegg -> logs.add("For hvert barn du har får du %s kr per dag. Siden du har %s barn vil du få %s kr i tillegg til ytelsen din.".format(
            satsPerBarnPerDag, personInfo.antallBarn, faktiskBarnetillegg))
        else -> logs.add("maks barn help me pls")
    }
}