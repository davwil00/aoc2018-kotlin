import kotlin.math.max

private val GRID_SERIAL_NUMBER: Int = 8444
//private val GRID_SERIAL_NUMBER: Int = 57
private val MAX_SQUARE_SIZE: Int = 200

data class Coordinate(val x: Int, val y: Int)

private fun calculatePowerLevel(coordinates: Coordinate): Int {
    val rackId = coordinates.x + 10
    var powerLevel = rackId * coordinates.y
    powerLevel += GRID_SERIAL_NUMBER
    powerLevel *= rackId
    val powerLevelStr = powerLevel.toString()
    powerLevel = if (powerLevelStr.length >= 3) Integer.parseInt(powerLevelStr.reversed()[2].toString()) else 0
    powerLevel -= 5
    return powerLevel
}

private fun createSquare(topLeft: Coordinate, squareSize: Int): List<List<Int>> {
    val grid = mutableListOf<List<Int>>()
    for (y in topLeft.y until topLeft.y + squareSize) {
        val row = mutableListOf<Int>()
        for (x in topLeft.x until topLeft.x + squareSize) {
            row.add(calculatePowerLevel(Coordinate(x, y)))
        }
        grid.add(row)
    }

    return grid
}

private fun calculateGridSum(grid: List<List<Int>>, topLeft: Coordinate, squareSize: Int): Int {
    return grid.subList(topLeft.y - 1, topLeft.y + squareSize - 1).map {
        it.subList(topLeft.x - 1, topLeft.x + squareSize - 1)
    }.flatten().sum()
}

fun main(args: Array<String>) {
    run()
}

fun run() {
    var maxPower = 0
    var maxPowerCoords = Coordinate(0, 0)
     var maxSquareSize = 0
    val grid = createSquare(Coordinate(1, 1), MAX_SQUARE_SIZE)
    IntRange(1, 300).toList().parallelStream().forEach{ squareSize ->
        println(squareSize)
        for (y in 1..MAX_SQUARE_SIZE - squareSize) {
            for (x in 1..MAX_SQUARE_SIZE - squareSize) {
                val topLeft = Coordinate(x, y)
                val totalPower = calculateGridSum(grid, topLeft, squareSize)
                if (totalPower > maxPower) {
                    maxPower = totalPower
                    maxPowerCoords = topLeft
                    maxSquareSize = squareSize
                }
            }
        }
    }
    println("Max power: ${maxPower}")
    println("Coords: ${maxPowerCoords}")
    println("Square size: ${maxSquareSize}")
}