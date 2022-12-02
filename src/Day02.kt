
fun main() {
    val scoreChart = mapOf("A" to 1, "B" to 2, "C" to 3, "X" to 1, "Y" to 2, "Z" to 3)

    val winPair = mapOf(3 to 1, 2 to 3, 1 to 2)
    val losePair = winPair.entries.associateBy({ it.value }) { it.key }

    fun roundScore (first: Int, second: Int): Int{
        if( first == second) return 3
        if(winPair[first] == second) {
            return 6
        }

        return 0
    }

    fun part1(input: List<String>): Int {
        return input.map { it.split(" ").map { shape -> scoreChart[shape.trim()]!! } }.sumOf { (first, second) ->
            second + roundScore(first, second)
        }
    }

    fun part2(input: List<String>): Int {

        return input.map { it.split(" ").map { shape -> scoreChart[shape.trim()]!! } }.sumOf { (first, second) ->
            when(second) {
                1 -> losePair[first]!! + roundScore(first, losePair[first]!!) // lose
                2 -> first + roundScore(first, first) // win
                else -> winPair[first]!! + roundScore(first, winPair[first]!!) // draw
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputs/Day02_test")
    println(part1(testInput))
    println(part2(testInput))

    println("----- Real input -------")
    val input = readInput("inputs/Day02")
    println(part1(input))
    println(part2(input))
}

//15
//12
//----- Real input -------
//11063
//10349