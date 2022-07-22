package no.nav

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import no.nav.util.Unbounded


@DelicateCoroutinesApi
class FornyGService(private val g: G) {
    fun startFornying() {
        GlobalScope.launch(Dispatchers.Unbounded) {
            try {
                g.hentGoppgave()
            } catch (e: Exception) {
                log.error("Noe gikk galt ved fornying av grunnbeløp", e.message)
            }
        }
    }
}