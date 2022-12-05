fun buildStacks (stackList: List<List<Char>>, size: Int): ArrayList<ArrayDeque<Char>> {
    val stacks = ArrayList<ArrayDeque<Char>>()
    for(i in 0 until size){
        val stack = ArrayDeque<Char>()
        for (j in stackList.indices){
            if(stackList[j][i] != ' '){
                stack.add(stackList[j][i])
            }
        }
        stacks.add(stack)
    }
    return stacks
}

fun main() {
    data class Model(val stacks: ArrayList<ArrayDeque<Char>>, val moves: List<List<Int>>)

    fun parseInput(input: List<String>): Model {
        val maxLength = input.takeWhile { it.contains("[") }.maxBy { it.length }.length
        val stacksRaw = input.takeWhile { it.contains("[") }.map {
            val lineStr = it.padEnd(maxLength, ' ')
            return@map buildList<Char> {
                var i = 1
                while (i < maxLength+1){
                    add(lineStr[i])
                    i +=4
                }
            }
        }

        val stacks = buildStacks(stacksRaw, (maxLength + 1)/4)
        val moves = input.drop(stacks.size)
            .filter { it.contains("move") }
            .map {
                """\d+""".toRegex().findAll(it).map{ it.value.toInt() }
                    .toList()
            }

        return Model(stacks, moves)
    }

    fun part1(input: List<String>): String {
        val (stacks, moves) = parseInput(input)
        for ((count, from, to) in moves){
            for(i in count downTo 1) {
                stacks[to - 1].addFirst(stacks[from - 1].removeFirst())
            }
        }
        return stacks.map { it.first() }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val (stacks, moves) = parseInput(input)
        for ((count, from, to) in moves){
            val temp = stacks[from - 1].take(count)
            stacks[to - 1].addAll(0,temp)
            stacks[from - 1] = ArrayDeque(stacks[from - 1].drop(count))
        }
        return stacks.map { it.first() }.joinToString("")
    }

    println("----- Test input -------")
    val testInput = readInput("inputs/Day05_test")
    println(part1(testInput))
    println(part2(testInput))

    println("----- Real input -------")
    val input = readInput("inputs/Day05")
    println(part1(input))
    println(part2(input))
}

//----- Test input -------
//CMZ
//MCD
//----- Real input -------
//QPJPLMNNR
//BQDNWJPVJ