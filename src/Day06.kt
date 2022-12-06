
fun main() {

    fun findMarker(chars: String, countUniq: Int = 4): Pair<Int, List<Char>> {
        val windows = chars.asSequence().windowed(countUniq, 1)

        val foundMarker = windows.mapIndexed(){ idx, value ->
            Pair(idx+1, value)
        }.filter { (_, marker) -> marker.toSet().size == countUniq }

        return foundMarker.first()
    }

    fun part1(input: List<String>): Int {
        return findMarker(input.first()).first + 3 // because we are always stepping up by one
    }

    fun part2(input: List<String>): Int {
        return findMarker(input.first(), 14).first + 13
    }


    println("----- Test input -------")
    val testInput = readInput("inputs/Day06_test")
    println(part1(testInput))
    println(part2(testInput))

    println("----- Real input -------")
    val input = readInput("inputs/Day06")
    println(part1(input))
    println(part2(input))
}

//----- Test input -------
//7
//19
//----- Real input -------
//1909
//3380
