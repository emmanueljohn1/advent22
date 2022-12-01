fun main() {
    fun getCalories(input: List<String>) = buildList {
        var currentSum = 0L
        for ((idx, value) in input.withIndex()) {
            if (value.isEmpty()) {
                add(currentSum)
                currentSum = 0
            } else {
                currentSum += value.toLong()
                if (idx == input.size - 1) {
                    add(currentSum)
                }
            }
        }
    }

    fun part1(input: List<String>): Long {
        val calories = getCalories(input)
        return calories.asSequence().sorted().drop(calories.size - 1).sum()
    }

    fun part2(input: List<String>): Long {
        val calories = getCalories(input)
        return calories.asSequence().sorted().drop(calories.size - 3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputs/Day01_test")
    println(part1(testInput))
    println(part2(testInput))

    println("----- Real input -------")
    val input = readInput("inputs/Day01")
    println(part1(input))
    println(part2(input))
}

// Output:
// 24000
// 45000
// ----- Real input -------
// 67633
// 199628
