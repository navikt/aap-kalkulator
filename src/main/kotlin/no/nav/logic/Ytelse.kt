package no.nav.logic

import kotlin.math.ceil

const val ytelsesdager = 260

fun ytelse(grunnlag: Double, antallBarn:Int): Int {
    val ytelse = ceil(grunnlag * 0.66).toInt()
    return (barnetillegg(antallBarn) + ytelse).coerceAtMost(ceil(grunnlag * 0.9).toInt())
    /*val barnetillegg = barnetillegg(antallBarn)
        .coerceAtLeast((grunnlag*0.9).toInt())*/

}