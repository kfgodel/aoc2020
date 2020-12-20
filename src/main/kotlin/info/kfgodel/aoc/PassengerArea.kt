package info.kfgodel.aoc

/**
 * This class represent a possible state for all the plane space
 * Date: 19/12/20 - 18:39
 */
class PassengerArea(val spaces: Array<SpaceType>, val columnsPerRow: Int) {

    fun occupiedSeatCount(): Int {
        return spaces.count { space -> SpaceType.OCCUPIED_SEAT.equals(space) }
    }

    /**
     * Returns a string representation for this area
     */
    fun representation(): String {
        val builder = StringBuilder()
        for (i in spaces.indices) {
            if (i != 0 && i % columnsPerRow == 0) {
                builder.append('\n')
            }
            builder.append(spaces[i].charRepresentation)
        }
        return builder.toString()
    }

    /**
     * Creates an exact deep copy that can be mutated without affecting this instance
     */
    fun copy(): PassengerArea {
        return PassengerArea(spaces.copyOf(), columnsPerRow)
    }

    override fun toString(): String {
        return this.representation()
    }

    fun createCursor(): AreaCursor {
        return AreaCursor(this)
    }

}

fun areaFrom(text: String): PassengerArea {
    if (text.isEmpty()) {
        throw IllegalArgumentException("Seating state cannot be created from an empty string")
    }
    var charactersPerLine = 0
    val spaces: MutableList<SpaceType> = ArrayList()
    for (i in text.indices) {
        val character = text[i]
        when (character) {
            '\n' -> { // ending a row
                if (charactersPerLine == 0) { // this is the first row, use it to set the expected columns per row
                    charactersPerLine = i + 1 // i is 0-based
                } else if ((i + 1) % charactersPerLine != 0) { // Let's check that all rows are equal
                    throw IllegalArgumentException("The input text should represent a rectangular space using lines of same length. Length of first line: ${charactersPerLine - 1}. Invalid length at position: $i")
                }
            }
            SpaceType.FLOOR.charRepresentation -> {
                spaces.add(SpaceType.FLOOR)
            }
            SpaceType.EMPTY_SEAT.charRepresentation -> {
                spaces.add(SpaceType.EMPTY_SEAT)
            }
            SpaceType.OCCUPIED_SEAT.charRepresentation -> {
                spaces.add(SpaceType.OCCUPIED_SEAT)
            }
            else -> { // Not an expected character
                throw IllegalArgumentException("Invalid character found in text at position $i: \'$character\'. Only 'L': empty seat, '#': occupied seat, or '.': floor are expected")
            }
        }
    }
    return PassengerArea(spaces.toTypedArray(), charactersPerLine - 1)
}
