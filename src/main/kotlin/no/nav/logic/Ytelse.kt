package no.nav.logic

import kotlin.math.ceil

const val ytelsesdager = 260

fun ytelseTilGrunnlag(ytelse: Double): Double = ytelse/66 * 100