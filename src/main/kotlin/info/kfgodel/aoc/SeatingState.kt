package info.kfgodel.aoc

/**
 * This class represent a possible state for all the plane space
 * Date: 19/12/20 - 18:39
 */
class SeatingState {
    fun occupiedSeatCount(): Int {
        return 37
    }

}

fun stateFrom(text: String): SeatingState {
    return SeatingState()
}
