package no.nav.logic

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class YtelseTest : FunSpec({
    context("Ytelse") {
        test("ytelse med grunnbeløp 6g") {
            val ytelse = ytelse(668862.0, 0)
            ytelse shouldBe 441449
        }
        test("ytelse med grunnbeløp 2g og 7 barn") {
            val ytelse = ytelse(222954.0, 7)
            ytelse shouldBe 196290
        }
        test("ytelse med grunnbeløp 2g og 8 barn") {
            val ytelse = ytelse(222954.0, 8)
            ytelse shouldBe 200659
        }
        test("ytelse med grunnbeløp 2g og 9 barn") {
            val ytelse = ytelse(222954.0, 9)
            ytelse shouldBe 200659
        }
        test("ytelse med grunnbeløp 6g og 22 barn") {
            val ytelse = ytelse(668862.0, 22)
            ytelse shouldBe 595889
        }
        test("ytelse med grunnbeløp 6g og 23 barn") {
            val ytelse = ytelse(668862.0, 23)
            ytelse shouldBe 601976
        }
        test("ytelse med grunnbeløp 6g og 24 barn") {
            val ytelse = ytelse(668862.0, 24)
            ytelse shouldBe 601976
        }
    }
})
