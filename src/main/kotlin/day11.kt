private val GRID_SERIAL_NUMBER: Int = 8444
private val MAX_SQUARE_SIZE: Int = 300

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

// Nice but too slow
private fun calculateGridSum(grid: List<List<Int>>, topLeft: Coordinate, squareSize: Int): Int {
    return grid.subList(topLeft.y - 1, topLeft.y + squareSize - 1).map {
        it.subList(topLeft.x - 1, topLeft.x + squareSize - 1)
    }.flatten().sum()
}

private fun calculateGridSum2(grid: List<List<Int>>, topLeft: Coordinate, squareSize: Int): Int {
    var total = 0
    for (i in topLeft.y - 1 until topLeft.y + squareSize - 1) {
        for (j in topLeft.x - 1 until topLeft.x + squareSize - 1) {
            total += grid[i][j]
        }
    }

    return total
}

fun run() {
    var maxPower = 0
    var maxPowerCoords = Coordinate(0, 0)
    var maxSquareSize = 0
    val grid = createSquare(Coordinate(1, 1), MAX_SQUARE_SIZE)
    IntRange(1, 300).toList().parallelStream().forEach{ squareSize ->
        for (y in 1..MAX_SQUARE_SIZE - squareSize) {
            for (x in 1..MAX_SQUARE_SIZE - squareSize) {
                val topLeft = Coordinate(x, y)
                val totalPower = calculateGridSum2(grid, topLeft, squareSize)
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

fun main(args: Array<String>) {
    run()
}
