package no.nav.logic

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class YtelseTest : FunSpec({
    context("Ytelse") {
        test("ytelse med grunnbeløp 6g, 0 barn og 0 arbeidsgrad") {
            val ytelse = ytelse(668862.0, 0, 0.0)
            ytelse shouldBe 441449
        }
        test("ytelse med grunnbeløp 2g, 7 barn og 0 arbeidsgrad") {
            val ytelse = ytelse(222954.0, 7, 0.0)
            ytelse shouldBe 196290
        }
        test("ytelse med grunnbeløp 2g, 8 barn og 0 arbeidsgrad") {
            val ytelse = ytelse(222954.0, 8, 0.0)
            ytelse shouldBe 200659
        }
        test("ytelse med grunnbeløp 2g, 9 barn og 0 arbeidsgrad") {
            val ytelse = ytelse(222954.0, 9, 0.0)
            ytelse shouldBe 200659
        }
        test("ytelse med grunnbeløp 6g, 22 barn og 0 arbeidsgrad") {
            val ytelse = ytelse(668862.0, 22, 0.0)
            ytelse shouldBe 595889
        }
        test("ytelse med grunnbeløp 6g, 23 barn og 0 arbeidsgrad") {
            val ytelse = ytelse(668862.0, 23, 0.0)
            ytelse shouldBe 601976
        }
        test("ytelse med grunnbeløp 6g, 24 barn og 0 arbeidsgrad") {
            val ytelse = ytelse(668862.0, 24, 0.0)
            ytelse shouldBe 601976
        }
        test("ytelse med grunnbeløp 2g, 0 barn og 40% arbeidsgrad") {
            val ytelse = ytelse(222954.0, 0, 40.0)
            ytelse shouldBe 88290
        }
        test("ytelse med grunnbeløp 2g, 8 barn og 50% arbeidsgrad") {
            val ytelse = ytelse(222954.0, 8, 50.0)
            ytelse shouldBe 100330
        }
        test("ytelse med grunnbeløp 6g, 0 barn og 20% arbeidsgrad") {
            val ytelse = ytelse(668862.0, 0, 20.0)
            ytelse shouldBe 353160
        }
        test("ytelse med grunnbeløp 2g, 5 barn og 61% arbeidsgrad") {
            val ytelse = ytelse(222954.0, 5, 61.0)
            ytelse shouldBe 0
        }
    }
})
