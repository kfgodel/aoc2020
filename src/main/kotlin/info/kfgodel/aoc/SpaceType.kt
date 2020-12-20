package info.kfgodel.aoc

/**
 * This enum represents the 3 available types of space usage that can be found on the plane.
 * It's either an empty seat, occupied seat, or plain floor
 * Date: 19/12/20 - 19:54
 */
enum class SpaceType(val charRepresentation: Char) {
    FLOOR('.'),
    EMPTY_SEAT('L') {
        override fun calculateChangeOn(cursor: AreaCursor): SpaceType {
            val adjacentOccupied = cursor.countOccupiedAdjacent()
            if(adjacentOccupied == 0){
                return OCCUPIED_SEAT
            }
            return super.calculateChangeOn(cursor)
        }
    },
    OCCUPIED_SEAT('#') {
        override fun calculateChangeOn(cursor: AreaCursor): SpaceType {
            val adjacentOccupied = cursor.countOccupiedAdjacent()
            if(adjacentOccupied >= 4){
                return EMPTY_SEAT
            }
            return super.calculateChangeOn(cursor)
        }
    };

    open fun calculateChangeOn(cursor: AreaCursor): SpaceType {
        return this // By default, any space doesn't change
    }
}
