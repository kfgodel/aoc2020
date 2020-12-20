package info.kfgodel.aoc

class PassengerSimulator(private var currentState: PassengerArea) {

    var nextState = currentState.copy()

    fun currentState(): PassengerArea {
        return currentState
    }

    fun simulateNextStep(): PassengerArea {
        var stateChanged = false
        val currentCursor = currentState.createCursor()
        val nextCursor = nextState.createCursor()
        while(currentCursor.hasSpace()){
            val currentSpace = currentCursor.space()
            val nextSpace = currentSpace.calculateChangeOn(currentCursor)
            if(currentSpace != nextSpace){
                stateChanged = true
            }
            nextCursor.changeSpaceTo(nextSpace)
            currentCursor.advance().also { nextCursor.advance() }
        }
        if(stateChanged){
            currentState = nextState.also { nextState = currentState }
        }
        return currentState
    }

}
