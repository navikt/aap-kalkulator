package no.nav.logic

import kotlin.math.ceil

const val ytelsesdager = 260

fun ytelse(grunnlag: Double, antallBarn:Int, arbeidsgrad:Double): Int {
    var ytelse = (grunnlag * 0.66)
    ytelse = (barnetillegg(antallBarn) + ytelse).coerceAtMost(grunnlag * 0.9)
    ytelse = ceil(arbeidsgrad(ytelse, arbeidsgrad))
    return ytelse.toInt()

}