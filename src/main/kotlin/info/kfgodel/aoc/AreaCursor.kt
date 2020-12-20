package info.kfgodel.aoc

import java.lang.IllegalStateException

/**
 * This class represents a cursor to each space on the passenger area that can be moved forward until reaching the end.
 * At each position it allows getting and changing the space, as well as interacting with the adjacent spaces
 * Date: 20/12/20 - 00:00
 */
class AreaCursor(private val area: PassengerArea) {
    var currentPosition = 0

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
        TODO("Not yet implemented")
    }
}