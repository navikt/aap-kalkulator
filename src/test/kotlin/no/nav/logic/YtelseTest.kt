package no.nav.logic

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class YtelseTest : FunSpec({

    test("ytelse med grunnbeløp 6g") {
        val ytelse = ytelse(668862.0)
        ytelse shouldBe 441449
    }
})
