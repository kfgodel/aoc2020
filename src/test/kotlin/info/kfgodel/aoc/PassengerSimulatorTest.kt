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

      }


    }
  }
}