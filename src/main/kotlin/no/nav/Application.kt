package no.nav


import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import kotlinx.coroutines.DelicateCoroutinesApi
import no.nav.endpoints.PersonInfo
import no.nav.endpoints.calculate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.Locale

val client = HttpClient(CIO){
    install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation){
        jackson(){
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
            registerModule(JavaTimeModule())
        }
    }
}
val log: Logger = LoggerFactory.getLogger("nav.aap.kalkulator")
@OptIn(DelicateCoroutinesApi::class)
fun main() {
    Locale.setDefault(Locale("nb"))
    val g = G()
    FornyGService(g).startFornying()
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson()
        }
        install(CORS){
            allowCredentials = true
            allowHeader(HttpHeaders.ContentType)
            allowHeader(HttpHeaders.AccessControlAllowOrigin)
            allowNonSimpleContentTypes = true
            allowSameOrigin = true
            anyHost()

        }
        routing {
            get("/") {
                call.respondText("Hello, world!")
            }
            post("/beregning"){
                val personInfo = call.receive<PersonInfo>()
                call.respond(personInfo.calculate(g.data.grunnbeloep))
            }
        }
    }.start(wait = true)
}