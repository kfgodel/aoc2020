package info.kfgodel.aoc

import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith

/**
 * Based on https://adventofcode.com/2020/day/11
 */
@RunWith(JavaSpecRunner::class)
class Day11SolverTest : KotlinSpec() {
  override fun define() {
    describe("a Day11Solver") {
      val solver by let { Day11Solver() }

      it("answers the amount of occupied seats after stabilization for the given initial state") {
        val finalState = solver().solve(areaFrom(
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
        ))
        assertThat(finalState.occupiedSeatCount()).isEqualTo(37)
      }
      it("answers the same amount of occupied seats after stabilization for the given final state") {
        val finalState = solver().solve(areaFrom(
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
        ))
        assertThat(finalState.occupiedSeatCount()).isEqualTo(37)
      }
      it("answers the amount of occupied seats after stabilization for the minimum state") {
        val finalState = solver().solve(areaFrom(
          "L"
        ))
        assertThat(finalState.occupiedSeatCount()).isEqualTo(1)
      }
    }
  }
}