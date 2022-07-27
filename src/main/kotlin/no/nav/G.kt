package no.nav

import com.fasterxml.jackson.annotation.JsonProperty
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import java.time.LocalDate
import kotlin.time.Duration.Companion.days

data class Grunnbeloep(
    @JsonProperty("grunnbeloep")
    val grunnbeloep: Double,
)

data class GrunnbeloepHistorikk(
    @JsonProperty("grunnbeløp")
    val grunnbeloep: Int,
    val dato: LocalDate,
    @JsonProperty("gjennomsnittPerÅr")
    val gjennomsnittPerAar: Int?,
)

suspend fun hentG(): Grunnbeloep {
    return client.get("https://g.nav.no/api/v1/grunnbeloep").body()
}

suspend fun hentGHistorikk(): List<GrunnbeloepHistorikk> {
    return client.get("https://g.nav.no/api/v1/historikk").body()
}

class G {
    var data = Grunnbeloep(0.0)
    var historikk = emptyList<GrunnbeloepHistorikk>()
    suspend fun hentGoppgave() {
        while (true) {
            log.info("henter G")
            data = hentG()
            historikk = hentGHistorikk()
            delay(1.days)
        }
    }
}

fun List<GrunnbeloepHistorikk>.forAar(aar: LocalDate) = this.find { it.dato.year == aar.year }
