package no.nav

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.jackson.*
import java.time.LocalDate
//{"dato":"2022-05-01","grunnbeloep":111477,"grunnbeloepPerMaaned":9290,"gjennomsnittPerAar":109784,"omregningsfaktor":1.047726,"virkningstidspunktForMinsteinntekt":"2022-05-23"}
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