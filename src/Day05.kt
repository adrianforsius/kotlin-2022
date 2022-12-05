import org.assertj.core.api.Assertions.assertThat


fun main() {
    fun part1(input: String): String {
        val parts = input.split("\n\n")
        val crates = parts[0].split("\n").map {
            it.toCharArray().filterIndexed { index, c -> ((index-1)%4) == 0 }
        }

        var stacks: List<ArrayDeque<Char>> = List(crates.size) {ArrayDeque()}
        crates.slice(0..(crates.size-2)).asReversed().map {chars ->
            chars.mapIndexed { index, c ->
                if (c != ' ' && c != '-') {
                    stacks[index].addLast(c)
                }
            }
        }

        var lines = parts[1].split("\n")
        lines = lines.slice(0..lines.size-2)
        lines.map {
            val commandParts = it.split(" from ")
            val move = commandParts[0].split(" ")[1].toInt()
            val directionPart = commandParts[1].split(" to ")
            val src = directionPart[0].toInt()-1
            val dst = directionPart[1].toInt()-1
            var srcStack = stacks[src]
            var dstStack = stacks[dst]

            val moved = srcStack.takeLast(move)
            dstStack.addAll(moved.asReversed())
            stacks[src].dropLast(move)
            for (i in 1..move) {
                stacks[src].removeLast()
            }
        }
        return stacks.map {
            it.takeLast(1).getOrNull(0) ?: ""
        }.joinToString("").trim()
    }

    fun part2(input: String): String {
        val parts = input.split("\n\n")
        val crates = parts[0].split("\n").map {
            it.toCharArray().filterIndexed { index, c -> ((index-1)%4) == 0 }
        }

        var stacks: List<ArrayDeque<Char>> = List(crates.size) {ArrayDeque()}
        crates.slice(0..(crates.size-2)).asReversed().map {chars ->
            chars.mapIndexed { index, c ->
                if (c != ' ' && c != '-') {
                    stacks[index].addLast(c)
                }
            }
        }

        var lines = parts[1].split("\n")
        lines = lines.slice(0..lines.size-2)
        lines.map {
            val commandParts = it.split(" from ")
            val move = commandParts[0].split(" ")[1].toInt()
            val directionPart = commandParts[1].split(" to ")
            val src = directionPart[0].toInt()-1
            val dst = directionPart[1].toInt()-1
            var srcStack = stacks[src]
            var dstStack = stacks[dst]

            val moved = srcStack.takeLast(move)
            dstStack.addAll(moved)
            stacks[src].dropLast(move)
            for (i in 1..move) {
                stacks[src].removeLast()
            }
        }
        return stacks.map {
            it.takeLast(1).getOrNull(0) ?: ""
        }.joinToString("").trim()
    }

    // test if implementation meets criteria from the description, like:
    // Test
    val testInput = readText("Day05_test")
    val output1 = part1(testInput)
    assertThat(output1).isEqualTo("CMZ")

    // Answer
    val input = readText("Day05")
    val outputAnswer1 = part1(input)
    assertThat(outputAnswer1).isEqualTo("NTWZZWHFV")

    // Test
    val output2 = part2(testInput)
    assertThat(output2).isEqualTo("MCD")

    // Answer
    val outputAnswer2 = part2(input)
    assertThat(outputAnswer2).isEqualTo("BRZGFVBTJ")
}
