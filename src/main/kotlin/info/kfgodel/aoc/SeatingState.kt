package info.kfgodel.aoc

import com.google.common.base.Splitter
import java.lang.IllegalArgumentException

/**
 * This class represent a possible state for all the plane space
 * Date: 19/12/20 - 18:39
 */
class SeatingState() {
    fun occupiedSeatCount(): Int {
        return 0
    }

}

fun stateFrom(text: String): SeatingState {
    if(text.isEmpty()){
        throw IllegalArgumentException("Seating state cannot be created from an empty string")
    }

    val allowedCharacters = "[L#.\n]+".toRegex()
    if(!allowedCharacters.matches(text)){
        throw IllegalArgumentException("Invalid character found in text. Only 'L': empty seat, '#': occupied seat, or '.': floor are expected")
    }

    val lines = Splitter.on("\n")
        .split(text)
        .toCollection(ArrayList())
    val expectedColumns = lines[0].length
    if(expectedColumns == 0 || lines.any({line -> line.length != expectedColumns})){
        throw IllegalArgumentException("The input text should represent a rectangular space using lines of same length. Length of first line: $expectedColumns")
    }

    return SeatingState()
}
