package no.nav

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.jackson.*

suspend fun getG(): Number {
    val client = HttpClient(){
        install(ContentNegotiation){
            jackson()
        }
    }
    val response = client.get("https://g.nav.no/api/v1/grunnbeloep")

    return 0
}