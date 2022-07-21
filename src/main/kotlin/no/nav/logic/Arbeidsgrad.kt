package no.nav.logic

import no.nav.endpoints.Respons

fun Respons.arbeidsgrad(){
    if (personInfo.arbeidsgrad < 0.0) {
        throw Exception("Arbeidsgrad må være større eller lik 0")
    }
    if (personInfo.arbeidsgrad > 60.0) {
        resultat = 0.0
        logs = mutableListOf("Arbeidsgraden din er høyere enn 60% og du kan derfor ikke få arbeidsavklaringspenger.")
        return
    }
    if (personInfo.arbeidsgrad == 0.0) {
        return
    }
    resultat *= ((100 - personInfo.arbeidsgrad) / 100)
    logs.add("Siden du jobber %.0f%% vil arbeidsavklaringspengene være redusert med %.0f%%.".format(personInfo.arbeidsgrad, personInfo.arbeidsgrad))
}