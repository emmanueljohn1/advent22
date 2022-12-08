fun main() {
    data class File(
        val parentPath: String,
        val name: String,
        val size: Long? = null
    )

    fun joinPath(parent: String, path: String): String {
        if (parent.endsWith("/")) return parent + path
        return "$parent/$path"
    }

    fun getParentFromPath(path: String): String {
        if (path == "/" || path.count { it == '/' } == 1) return "/"
        return path.split("/").dropLast(1).joinToString("/")
    }

    fun listDir(
        restInput: List<String>,
        fullPath: String,
        files: Map<String, List<File>>
    ): Pair<Map<String, List<File>>, List<String>> {
        val content = restInput
            .takeWhile { !it.startsWith("$") }
            .map { f ->
                val parts = f.split(" ")
                if (f.startsWith("dir")) {
                    return@map File(joinPath(fullPath, parts[1]), parts[1])
                }

                return@map File(joinPath(fullPath, parts[1]), parts[1], parts[0].toLong())
            }
        return Pair(
            files.plus(mapOf(Pair(fullPath, content))),
            restInput.drop(content.size)
        )
    }

    tailrec fun walkDirectory(
        input: List<String>,
        files: Map<String, List<File>>,
        cwd: String
    ): Map<String, List<File>> {
        if (input.isEmpty()) {
            return files
        }
        val line = input.first()
        var fullPath = cwd
        var filesCurrent = files
        var restInput = input.drop(1)
        if (line.startsWith("$")) {
            if (line.contains("cd")) {
                val cmdArg = line.split(" ").last()
                fullPath = when (cmdArg) {
                    ".." -> getParentFromPath(cwd)
                    "/" -> "/"
                    else -> joinPath(fullPath, cmdArg)
                }
                if (!files.containsKey(fullPath)) {
                    filesCurrent = files.plus(mapOf(Pair(fullPath, listOf())))
                }
            } else if (line.contains("ls")) {
                val (first, second) = listDir(restInput, fullPath, files)
                filesCurrent = first
                restInput = second
            }
        }

        return walkDirectory(restInput, filesCurrent, fullPath)
    }

    fun part1(input: List<String>): Long {
        val directories = walkDirectory(input, mapOf(), "")
        val sizes = directories.map {
            directories.filter { entry -> entry.key.startsWith(it.key) }
                .flatMap { entry ->
                    entry.value.map { f -> f.size ?: 0 }
                }.sum()
        }

        return sizes.map { it }.filter { it < 100000 }.sum()
    }

    fun part2(input: List<String>): Long {
        val directories = walkDirectory(input, mapOf(), "")
        val sizes = directories.map {
            Pair(it.key, directories.filter { entry -> entry.key.startsWith(it.key) }
                .flatMap { entry ->
                    entry.value.map { f -> f.size ?: 0 }
                }.sum())
        }

        val unused = 70000000L - sizes.maxBy { it.second }.second
        return sizes.filter { it.second >= 30000000 - unused }.minBy { it.second }.second
    }


    println("----- Test input -------")
    val testInput = readInput("inputs/Day07_test")
    println(part1(testInput))
    println(part2(testInput))

    println("----- Real input -------")
    val input = readInput("inputs/Day07")
    println(part1(input))
    println(part2(input))
}

//
//----- Test input -------
//95437
//24933642
//----- Real input -------
//1297159
//3866390
