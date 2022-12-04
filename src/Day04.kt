fun main() {
    data class ElfPair(val range1: IntRange, val range2: IntRange){
        private val set1 = range1.toSet()
        private val set2 = range2.toSet()
        fun isFullyContained(): Boolean {
            return set1.containsAll(set2) || set2.containsAll(set1)
        }

        fun isOverlap(): Boolean {
            return set1.intersect(set2).isNotEmpty()
        }
    }

    fun elfPairs(input: List<String>): List<ElfPair> {
        val data = input.map {
            it.split(",").map { it1 ->
                it1.split("-").map { it2 -> it2.toInt() }
            }.map { (first, second) ->
                IntRange(first, second)
            }
        }.map { (first, second) -> ElfPair(first, second) }
        return data
    }

    fun part1(input: List<String>): Int {
        val data = elfPairs(input)
        return data.count { it.isFullyContained() }
    }

    fun part2(input: List<String>): Int {
        val data = elfPairs(input)
        return data.count { it.isOverlap() }
    }

    println("----- Test input -------")
    val testInput = readInput("inputs/Day04_test")
    println(part1(testInput))
    println(part2(testInput))

    println("----- Real input -------")
    val input = readInput("inputs/Day04")
    println(part1(input))
    println(part2(input))
}

//2
//4
//----- Real input -------
//573
//867