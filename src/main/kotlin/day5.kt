import java.io.File

fun removePairs(input: List<String>): List<String> {
    var i = 0
    val newSeq = mutableListOf<String>()
    while (i < input.size) {
        val char1 = input[i]
        val char2 = if (i == input.size - 1) "" else input[i + 1]
        if (char1.equals(char2, true)) {
            i += 2
        } else {
            newSeq.add(char1)
            i++
        }
    }
    if (input.size != newSeq.size) {
        return removePairs(newSeq)
    }
    return newSeq
}

fun main(args: Array<String>) {
    val input = "XXABCDEedcbaXX".chunked(1)
//    val input = File("src/main/resources/day5.txt").readText().chunked(1)
    val condensedList = removePairs(input)
    println(condensedList)
    println(condensedList.size)
}
