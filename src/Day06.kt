import org.assertj.core.api.Assertions.assertThat


fun main() {
    fun part1(input: List<String>): Int {
        val lines = input
        var splitAt = lines
            .withIndex()
            .filter { it.value == "" }
            .map { it.index }
        splitAt = listOf(0) + splitAt
        splitAt = splitAt + listOf(lines.size)


        val split = splitAt
            .windowed(2)
            .map {
                lines.subList(
                    it[0] + 1,
                    it[1],
                )
            }

        val max = split.map {
            it.map {
                it.toInt()
            }.sum()
        }.max()
        return max
    }

    fun part2(input: List<String>): Int {
        val lines = input
        var splitAt = lines
            .withIndex()
            .filter { it.value == "" }
            .map { it.index }
        splitAt = listOf(0) + splitAt
        splitAt = splitAt + listOf(lines.size)


        val split = splitAt
            .windowed(2)
            .map {
                lines.subList(
                    it[0],
                    it[1],
                )
            }

        val sorted = split.map {
            it.map{
                it.toIntOrNull() ?: 0
            }.sum()
        }.sortedDescending()
        return sorted.slice(0..2).sum()
    }

    // test if implementation meets criteria from the description, like:
    // Test
    val testInput = readInput("Day01_test")
    val output1 = part1(testInput)
    assertThat(output1).isEqualTo(24000)

    // Answer
    val input = readInput("Day01")
    val outputAnswer1 = part1(input)
    assertThat(outputAnswer1).isEqualTo(67450)

    // Test
    val output2 = part2(testInput)
    assertThat(output2).isEqualTo(45000)

    // Answer
    val outputAnswer2 = part2(input)
    assertThat(outputAnswer2).isEqualTo(199357)
}
