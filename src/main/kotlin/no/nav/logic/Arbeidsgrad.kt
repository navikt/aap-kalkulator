package no.nav.logic

import no.nav.endpoints.Respons

fun Respons.arbeidsgrad() {
    val gammeltResultat = resultat
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
    logs.add("En vanlig arbeidsuke er 37,5 timer. Siden du jobber %s timer i uken blir ytelsen redusert med %.0f%%, fra %s kr til <strong>%s kr</strong>. "
        .format(personInfo.arbeidstimer, personInfo.arbeidsgrad, gammeltResultat.tilKr(), resultat.tilKr()))
}
