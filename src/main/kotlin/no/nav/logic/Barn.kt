package no.nav.logic


const val satsPerBarnPerDag:Int = 27

fun barnetillegg(antallBarn: Int):Int = antallBarn* satsPerBarnPerDag* ytelsesdager