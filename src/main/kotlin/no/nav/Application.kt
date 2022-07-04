package no.nav


import io.ktor.serialization.jackson.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import no.nav.endpoints.PersonInfo
import no.nav.endpoints.beregn

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson()
        }
        routing {
            get("/") {
                call.respondText("Hello, world!")
            }
            post("/beregning"){
                val personInfo = call.receive<PersonInfo>()
                call.respondText("%s".format(personInfo.beregn()))
            }
        }
    }.start(wait = true)
}