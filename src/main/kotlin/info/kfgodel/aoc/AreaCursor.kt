package info.kfgodel.aoc

import java.lang.IllegalStateException

/**
 * This class represents a cursor to each space on the passenger area that can be moved forward until reaching the end.
 * At each position it allows getting and changing the space, as well as interacting with the adjacent spaces
 * Date: 20/12/20 - 00:00
 */
class AreaCursor(private val area: PassengerArea) {
    var currentPosition = 0
    val relativeAdjacentPositions = arrayOf(
        -area.columnsPerRow - 1, -area.columnsPerRow, -area.columnsPerRow + 1,
        -1, +1,
        +area.columnsPerRow -1, area.columnsPerRow, area.columnsPerRow +1
    )

    fun hasSpace(): Boolean {
        return currentPosition < area.spaces.size
    }

    fun space(): SpaceType {
        if (!hasSpace()) {
            throw IllegalStateException("Cursor has been consumed and has no space")
        }
        return area.spaces[currentPosition]
    }

    fun changeSpaceTo(newSpace: SpaceType) : AreaCursor {
        if (!hasSpace()) {
            throw IllegalStateException("Cursor has been consumed and has no space to change")
        }
        area.spaces[currentPosition] = newSpace
        return this
    }

    fun advance(positions: Int = 1): AreaCursor {
        if(currentPosition + positions <= area.spaces.size){
            currentPosition += positions
        }else{
            // Sanity check
            throw IllegalStateException("Cursor cannot be advanced after the last position")
        }
        return this
    }

    fun countOccupiedAdjacent(): Int {
        var occupiedCount = 0
        for(i in relativeAdjacentPositions.indices){
            if(adjacentWillFallOutsideHorizontalArea(i)){
                // Skip a space that is out of the left or right area limits
                continue
            }
            val adjacentPosition = relativeAdjacentPositions[i] + currentPosition
            if (adjacentWillFallOutsideVerticalArea(adjacentPosition)) {
                // Skip adjacent that is above or below the area
                continue
            }
            val adjacentSpace = area.spaces[adjacentPosition]
            if(adjacentSpace == SpaceType.OCCUPIED_SEAT){
                occupiedCount++
            }
        }
        return occupiedCount
    }

    private fun adjacentWillFallOutsideVerticalArea(adjacentPosition: Int) =
        adjacentPosition < 0 || adjacentPosition >= area.spaces.size

    private fun adjacentWillFallOutsideHorizontalArea(relativeIndex: Int): Boolean {
        val columnIndex = currentPosition % area.columnsPerRow
        if(columnIndex == 0){ // We are on the leftmost spaces
            // Spaces to the left of the current will fall outside the area
            if(relativeIndex == 0 || relativeIndex == 3 || relativeIndex == 5){
                return true
            }
        }
        if(columnIndex == (area.columnsPerRow-1)){ // We are on the rightmost spaces
            // Spaces to the right of the current will fall outside the area
            if(relativeIndex == 2 || relativeIndex == 4 || relativeIndex == 7){
                return true
            }
        }
        return false
    }

}