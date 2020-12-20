package info.kfgodel.aoc

import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.runner.RunWith

/**
 * Test teh area cursor created to navigate and change the passenger area
 */
@RunWith(JavaSpecRunner::class)
class AreaCursorTest : KotlinSpec() {
    override fun define() {
        describe("an area cursor") {

            val area by let { areaFrom(".L\n#.") }
            val cursor by let { area().createCursor() }

            it("indicates that points to a space when created") {
                assertThat(cursor().hasSpace()).isTrue()
            }

            it("starts at the top left of the area") {
                assertThat(cursor().space()).isEqualTo(SpaceType.FLOOR)
            }

            it("allows changing the space where it points to") {
                cursor().changeSpaceTo(SpaceType.OCCUPIED_SEAT)
                assertThat(area().representation()).isEqualTo("#L\n#.")
            }

            it("moves to the right when advanced") {
                cursor().advance()
                assertThat(cursor().space()).isEqualTo(SpaceType.EMPTY_SEAT)
            }

            it("moves down when advanced after reaching the end of a row") {
                cursor().advance(2)
                assertThat(cursor().space()).isEqualTo(SpaceType.OCCUPIED_SEAT)
            }
            it("indicates that points to the last space") {
                cursor().advance(3)
                assertThat(cursor().hasSpace()).isTrue()
                assertThat(cursor().space()).isEqualTo(SpaceType.FLOOR)
            }
            it("indicates that points to no space when advanced after the last space") {
                cursor().advance(4)
                assertThat(cursor().hasSpace()).isFalse()
            }

            it("fails if tried to move out of space") {
                assertThatThrownBy {
                    cursor().advance(5)
                }.hasMessage("Cursor cannot be advanced after the last position")
            }

            it("fails if it has no space and it's asked to return one") {
                cursor().advance(4)
                assertThatThrownBy {
                    cursor().space()
                }.hasMessage("Cursor has been consumed and has no space")
            }

            it("fails if it has no space and it's asked to change it") {
                cursor().advance(4)
                assertThatThrownBy {
                    cursor().changeSpaceTo(SpaceType.OCCUPIED_SEAT)
                }.hasMessage("Cursor has been consumed and has no space to change")
            }

            describe("when counting adjacent occupied seats") {
                val counted by let { cursor().countOccupiedAdjacent() }
                area {
                    areaFrom(
                        "#L#\n" +
                        "#.L\n" +
                        "###"
                    )
                }

                it("considers the 8 spaces around the current one") {
                    cursor().advance(4)
                    assertThat(counted()).isEqualTo(5)
                }
                it("ignores spaces outside the area in the corners") {
                    assertThat(counted()).isEqualTo(1)
                    cursor().advance(2)
                    assertThat(counted()).isEqualTo(0)
                    cursor().advance(4)
                    assertThat(counted()).isEqualTo(2)
                    cursor().advance(2)
                    assertThat(counted()).isEqualTo(1)
                }

            }
        }
    }
}