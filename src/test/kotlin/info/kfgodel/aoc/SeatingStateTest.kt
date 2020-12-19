package info.kfgodel.aoc

import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.runner.RunWith

/**
 * Based on https://adventofcode.com/2020/day/11
 */
@RunWith(JavaSpecRunner::class)
class SeatingStateTest : KotlinSpec() {
    override fun define() {
        describe("a seating state") {
            val state by let { SeatingState() }

            describe("created from text") {
                val text by let { "" }
                val state by let { stateFrom(text()) }

                it("validates text is not empty") {
                    assertThatThrownBy {
                        stateFrom(text())
                    }.hasMessage("Seating state cannot be created from an empty string")
                }
                it("validates characters in the text") {
                    assertThatThrownBy {
                        stateFrom("a")
                    }.hasMessage("Invalid character found in text. Only 'L': empty seat, '#': occupied seat, or '.': floor are expected")
                }
                it("validates the text represents a rectangular space") {
                    assertThatThrownBy {
                        stateFrom(
                            "LLL\n" +
                            "LLLL\n" +
                            "LLL"
                        )
                    }.hasMessage("The input text should represent a rectangular space using lines of same length. Length of first line: 3")
                }

                it("counts 0 occupied seats if only floor or empty seats are used") {
                    text {
                        "L.L\n" +
                        "L.L\n" +
                        "L.L"
                    }
                    assertThat(state().occupiedSeatCount()).isEqualTo(0)
                }

                it("counts the amount of occupied seats") {
                    text {
                        "L.#\n" +
                        "#.L\n" +
                        "L.#"
                    }
                    assertThat(state().occupiedSeatCount()).isEqualTo(3)
                }

            }
        }
    }
}