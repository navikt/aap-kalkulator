package no.nav

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.jackson.*
import java.time.LocalDate
//{"dato":"2022-05-01","grunnbeloep":111477,"grunnbeloepPerMaaned":9290,"gjennomsnittPerAar":109784,"omregningsfaktor":1.047726,"virkningstidspunktForMinsteinntekt":"2022-05-23"}
data class Grunnbeløp(
    @JsonProperty("grunnbeloep")
    val grunnbeløp: Double,
    val dato: LocalDate,
    @JsonProperty("grunnbeloepPerMaaned")
    val grunnbeløpPerMåned: Double,
    @JsonProperty("gjennomsnittPerAar")
    val gjennomsnittPerÅr: Double,
    val omregningsfaktor: Double,
    val virkningstidspunktForMinsteinntekt: LocalDate

)

suspend fun getG(): Double {
    val client = HttpClient(){
        install(ContentNegotiation){
            jackson(){
                registerModule(JavaTimeModule())
            }
        }
    }
    val response: Grunnbeløp = client.get("https://g.nav.no/api/v1/grunnbeloep").body()

    return response.grunnbeløp
}