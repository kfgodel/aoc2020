package info.kfgodel.aoc

import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith

/**
 * Based on https://adventofcode.com/2020/day/11
 */
@RunWith(JavaSpecRunner::class)
class PassengerSimulatorTest : KotlinSpec() {
  override fun define() {
    describe("a passenger simulator") {
      val initialState by let { areaFrom("L") }
      val simulator by let { PassengerSimulator(initialState()) }

      it("uses the initial state as its current state"){
        assertThat(simulator().currentState()).isSameAs(initialState())
      }

      describe("when simulating next step") {
        val nextState by let { simulator().simulateNextStep() }

        it("applies the passenger rules to calculate the next state of the current area"){
          assertThat(nextState().representation()).isEqualTo("#")
        }

        it("returns a different instance and changes the current state if there were changes in the area"){
          assertThat(nextState()).isNotSameAs(initialState())
        }

        it("changes the current state instance if there were changes in the area"){
          assertThat(nextState()).isEqualTo(simulator().currentState())
        }

        it("returns the same instance as the previous state if there where no changes in the area"){
          initialState { areaFrom("#") }
          assertThat(nextState()).isSameAs(initialState())
        }

        describe("main rules") {
          it("occupies a seat if there are no adjacent occupied seats") {
            initialState { areaFrom(
              ".LL\n"+
              "LLL\n"+
              "LL."
            ) }
            assertThat(nextState().representation()).isEqualTo(
              ".##\n"+
              "###\n"+
              "##."
            )
          }
          it("does not occupy a seat if there are is at least 1 adjacent occupied seat") {
            initialState { areaFrom(
              ".L#\n"+
              "LLL\n"+
              "#L."
            ) }
            assertThat(nextState().representation()).isEqualTo(
              ".L#\n"+
              "LLL\n"+
              "#L."
            )
          }
          it("does not empty a seat if there are less than 4 adjacent occupied seat") {
            initialState { areaFrom(
              ".L#\n"+
              "L##\n"+
              "#L."
            ) }
            assertThat(nextState().representation()).isEqualTo(
              ".L#\n"+
              "L##\n"+
              "#L."
            )
          }
          it("empties a seat if there are 4 or more adjacent occupied seat") {
            initialState { areaFrom(
              ".L#\n"+
              "###\n"+
              "#L."
            ) }
            assertThat(nextState().representation()).isEqualTo(
              ".L#\n"+
              "#L#\n"+
              "#L."
            )
          }

        }

        describe("based on problem examples") {
          it("correctly calculates the 2nd round") {
            initialState { areaFrom(
              "L.LL.LL.LL\n" +
              "LLLLLLL.LL\n" +
              "L.L.L..L..\n" +
              "LLLL.LL.LL\n" +
              "L.LL.LL.LL\n" +
              "L.LLLLL.LL\n" +
              "..L.L.....\n" +
              "LLLLLLLLLL\n" +
              "L.LLLLLL.L\n" +
              "L.LLLLL.LL"
            )}
            assertThat(nextState().representation()).isEqualTo(
              "#.##.##.##\n" +
              "#######.##\n" +
              "#.#.#..#..\n" +
              "####.##.##\n" +
              "#.##.##.##\n" +
              "#.#####.##\n" +
              "..#.#.....\n" +
              "##########\n" +
              "#.######.#\n" +
              "#.#####.##"
            )
          }
          it("correctly calculates the 3rd round") {
            initialState { areaFrom(
              "#.##.##.##\n" +
              "#######.##\n" +
              "#.#.#..#..\n" +
              "####.##.##\n" +
              "#.##.##.##\n" +
              "#.#####.##\n" +
              "..#.#.....\n" +
              "##########\n" +
              "#.######.#\n" +
              "#.#####.##"
            )}
            assertThat(nextState().representation()).isEqualTo(
              "#.LL.L#.##\n" +
              "#LLLLLL.L#\n" +
              "L.L.L..L..\n" +
              "#LLL.LL.L#\n" +
              "#.LL.LL.LL\n" +
              "#.LLLL#.##\n" +
              "..L.L.....\n" +
              "#LLLLLLLL#\n" +
              "#.LLLLLL.L\n" +
              "#.#LLLL.##"
            )
          }
          it("correctly calculates the 4th round") {
            initialState { areaFrom(
              "#.LL.L#.##\n" +
              "#LLLLLL.L#\n" +
              "L.L.L..L..\n" +
              "#LLL.LL.L#\n" +
              "#.LL.LL.LL\n" +
              "#.LLLL#.##\n" +
              "..L.L.....\n" +
              "#LLLLLLLL#\n" +
              "#.LLLLLL.L\n" +
              "#.#LLLL.##"
            )}
            assertThat(nextState().representation()).isEqualTo(
              "#.##.L#.##\n" +
              "#L###LL.L#\n" +
              "L.#.#..#..\n" +
              "#L##.##.L#\n" +
              "#.##.LL.LL\n" +
              "#.###L#.##\n" +
              "..#.#.....\n" +
              "#L######L#\n" +
              "#.LL###L.L\n" +
              "#.#L###.##"
            )
          }
          it("correctly calculates the 5th round") {
            initialState { areaFrom(
              "#.##.L#.##\n" +
              "#L###LL.L#\n" +
              "L.#.#..#..\n" +
              "#L##.##.L#\n" +
              "#.##.LL.LL\n" +
              "#.###L#.##\n" +
              "..#.#.....\n" +
              "#L######L#\n" +
              "#.LL###L.L\n" +
              "#.#L###.##"
            )}
            assertThat(nextState().representation()).isEqualTo(
              "#.#L.L#.##\n" +
              "#LLL#LL.L#\n" +
              "L.L.L..#..\n" +
              "#LLL.##.L#\n" +
              "#.LL.LL.LL\n" +
              "#.LL#L#.##\n" +
              "..L.L.....\n" +
              "#L#LLLL#L#\n" +
              "#.LLLLLL.L\n" +
              "#.#L#L#.##"
            )
          }
          it("correctly calculates the 6th round") {
            initialState { areaFrom(
              "#.#L.L#.##\n" +
              "#LLL#LL.L#\n" +
              "L.L.L..#..\n" +
              "#LLL.##.L#\n" +
              "#.LL.LL.LL\n" +
              "#.LL#L#.##\n" +
              "..L.L.....\n" +
              "#L#LLLL#L#\n" +
              "#.LLLLLL.L\n" +
              "#.#L#L#.##"
            )}
            assertThat(nextState().representation()).isEqualTo(
              "#.#L.L#.##\n" +
              "#LLL#LL.L#\n" +
              "L.#.L..#..\n" +
              "#L##.##.L#\n" +
              "#.#L.LL.LL\n" +
              "#.#L#L#.##\n" +
              "..L.L.....\n" +
              "#L#L##L#L#\n" +
              "#.LLLLLL.L\n" +
              "#.#L#L#.##"
            )
          }
        }
      }


    }
  }
}