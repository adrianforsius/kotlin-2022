import org.assertj.core.api.Assertions.assertThat


fun main() {
    fun part1(input: List<String>): Int =
        input
            .first()
            .windowed(4)
            .indexOfFirst {
                it.toSet().size >= 4
            }.plus(4)

    fun part2(input: List<String>): Int =
        input
            .first()
            .windowed(14)
            .indexOfFirst {
                it.toSet().size >= 14
            }.plus(14)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    // Test
    val output1 = part1(testInput)
    assertThat(output1).isEqualTo(7)

    // Answer
    val input = readInput("Day06")
    val outputAnswer1 = part1(input)
    assertThat(outputAnswer1).isEqualTo(1300)

    // Test
    val output2 = part2(testInput)
    assertThat(output2).isEqualTo(19)

    // Answer
    val outputAnswer2 = part2(input)
    assertThat(outputAnswer2).isEqualTo(3986)
}
