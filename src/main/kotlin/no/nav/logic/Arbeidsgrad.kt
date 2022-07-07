package no.nav.logic

fun arbeidsgrad(grunnlag: Double, arbeidsgrad: Double): Double {
    if (arbeidsgrad < 0) {
        throw Exception("Arbeidsgrad må være større eller lik 0")
    }
    if (arbeidsgrad > 60) {
        return 0.0
    }
    return grunnlag * ((100 - arbeidsgrad) / 100)
}