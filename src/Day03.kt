fun main() {

    fun getScore(ch: Char): Int {
        if(ch.isLowerCase()) {
            return (ch.code - 97) + 1
        }
        return (ch.code - 65) + 27
    }

    fun part1(input: List<String>): Int {
        return input.flatMap { line ->
            val (chest1, chest2) = line.chunked(line.length / 2)
            chest1.toSet().intersect(chest2.toSet())
        }.sumOf(::getScore)
    }

    fun badgeScore(chunk: List<String>): Int {
        return chunk.map { it.toSet() }
            .reduce{ acc, cur -> acc.intersect(cur) }
            .map{ getScore(it) }.first()
    }

    fun part2(input: List<String>): Int {
        return input.windowed(3, 3).sumOf { badgeScore(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputs/Day03_test")
    println(part1(testInput))
    println(part2(testInput))

    println("----- Real input -------")
    val input = readInput("inputs/Day03")
    println(part1(input))
    println(part2(input))
}

// 157
// 70
// ----- Real input -------
// 7990
// 2602