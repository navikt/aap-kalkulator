package no.nav.logic

import no.nav.endpoints.Respons

fun arbeidsgrad(grunnlag: Double, arbeidsgrad: Double): Double {
    if (arbeidsgrad < 0) {
        throw Exception("Arbeidsgrad må være større eller lik 0")
    }
    if (arbeidsgrad > 60) {
        return 0.0
    }
    return grunnlag * ((100 - arbeidsgrad) / 100)
}

fun Respons.arbeidsgrad(){
    if (personInfo.arbeidsgrad < 0) {
        throw Exception("Arbeidsgrad må være større eller lik 0")
    }
    if (personInfo.arbeidsgrad > 60) {
        resultat = 0.0
        logs = mutableListOf("Arbeidsgraden din er høyere enn 60% og du kan derfor ikke få aap.")
        return
    }
    resultat *= ((100 - personInfo.arbeidsgrad) / 100)
    logs.add("Aap vil være redusert med %.0f prosent.".format(personInfo.arbeidsgrad ))
}