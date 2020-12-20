package info.kfgodel.aoc

import java.lang.IllegalStateException

class Day11Solver {
    fun solve(initialState: PassengerArea): PassengerArea {
        val simulator = PassengerSimulator(initialState)
        var iterationLimit = 1000 // Guard against infinite loop
        while(iterationLimit > 0){
            iterationLimit--
            val currentState = simulator.currentState()
            val nextState = simulator.simulateNextStep()
            if(currentState == nextState){ // It's stabilized. We are guaranteed that the same object is returned if no change
                return currentState
            }
        }
        throw IllegalStateException("Too many iterations?")
    }

}
