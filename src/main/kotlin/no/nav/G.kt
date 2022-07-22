package no.nav

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.jackson.*
import kotlinx.coroutines.delay
import java.time.LocalDate
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

data class Grunnbeloep(
    @JsonProperty("grunnbeloep")
    val grunnbeloep: Double,
    val dato: LocalDate,
    @JsonProperty("grunnbeloepPerMaaned")
    val grunnbeloepPerMaaned: Double,
    @JsonProperty("gjennomsnittPerAar")
    val gjennomsnittPerAar: Double,
    val omregningsfaktor: Double,
    val virkningstidspunktForMinsteinntekt: LocalDate

)
data class Gdata(
    var grunnbeloep: Double
)

suspend fun hentG(): Double {
    val client = HttpClient(CIO){
        install(ContentNegotiation){
            jackson(){
                registerModule(JavaTimeModule())
            }
        }
    }
    val response: Grunnbeloep = client.get("https://g.nav.no/api/v1/grunnbeloep").body()

    return response.grunnbeloep
}

class G {
    var gData = Gdata(0.0)
    suspend fun hentGoppgave() {

        while (true) {
            log.info("henter G")
            gData.apply {
                grunnbeloep = hentG()

            }
            delay(1.days)
        }
    }
}