import org.assertj.core.api.Assertions.assertThat


fun main() {
    fun part1(input: List<String>): Int {
        val instructions = ArrayDeque(input)
        val effects = MutableList(input.size * 2) { "" }

        var sum = 1
        var cycleCount = 20
        var cycleSum = 0
        for (cycle in 0..220) {
            val effect = effects[cycle]
            if (effect != "" || cycle == 0) {
                when (val ins = instructions.removeFirst()) {
                    "noop" -> effects[cycle + 1] = ins
                    else -> effects[cycle + 2] = ins
                }
            }
            if (cycleCount % 40 == 0) {
                cycleSum += (cycle * sum)
            }
            when (effect) {
                "" -> {}
                "noop" -> {}
                else -> sum += effect.split(" ")[1].toInt()
            }
            cycleCount += 1
        }
        return cycleSum
    }

    fun part2(input: List<String>): List<String> {
        val instructions = ArrayDeque(input)
        val effects = MutableList(input.size * 2) { "" }

        var sum = 1
        var row = ""
        var rows = emptyList<String>()
        for (cycle in 0..6 * 40) {
            val effect = effects[cycle]
            if (effect != "" || cycle == 0) {
                if (instructions.size > 0) {
                    when (val ins = instructions.removeFirst()) {
                        "noop" -> effects[cycle + 1] = ins
                        else -> effects[cycle + 2] = ins
                    }
                }
            }
            when (effect) {
                "" -> {}
                "noop" -> {}
                else -> sum += effect.split(" ")[1].toInt()
            }
            if (cycle % 40 == 0 && cycle != 0) {
                rows += row
                row = ""
            }
            if (cycle % 40 in listOf(sum - 1, sum, sum + 1)) {
                row += "#"
            } else {
                row += "."
            }
        }
        return rows
    }

    // test if implementation meets criteria from the description, like:
    // Test
    val testInput = readInput("Day10_test")
    val output1 = part1(testInput)
    assertThat(output1).isEqualTo(13140)

    // Answer
    val input = readInput("Day10")
    val outputAnswer1 = part1(input)
    assertThat(outputAnswer1).isEqualTo(13440)

    // Test
    val output2 = part2(testInput)
    assertThat(output2).isEqualTo(
        listOf(
            "##..##..##..##..##..##..##..##..##..##..",
            "###...###...###...###...###...###...###.",
            "####....####....####....####....####....",
            "#####.....#####.....#####.....#####.....",
            "######......######......######......####",
            "#######.......#######.......#######....."
        )
    )

    // Answer
    val outputAnswer2 = part2(input)
    assertThat(outputAnswer2).isEqualTo(
        listOf(
            "###..###..####..##..###...##..####..##..",
            "#..#.#..#....#.#..#.#..#.#..#....#.#..#.",
            "#..#.###....#..#....#..#.#..#...#..#..#.",
            "###..#..#..#...#.##.###..####..#...####.",
            "#....#..#.#....#..#.#.#..#..#.#....#..#.",
            "#....###..####..###.#..#.#..#.####.#..#.",
        )
    )
}
