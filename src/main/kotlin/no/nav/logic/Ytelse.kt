package no.nav.logic

const val ytelsesdager = 260

fun ytelseTilGrunnlag(ytelse: Double): Double {
    val broek = 2.0/3.0
    val grunnlag = ytelse / broek
    return grunnlag
}
