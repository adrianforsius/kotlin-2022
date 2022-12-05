import org.assertj.core.api.Assertions.assertThat


fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    // Test
    val testInput = readInput("Day01_test")
    val output1 = part1(testInput)
    assertThat(output1).isEqualTo(24000)

//    // Answer
//    val input = readInput("Day01")
//    val outputAnswer1 = part1(input)
//    assertThat(outputAnswer1).isEqualTo(67450)
//
//    // Test
//    val output2 = part2(testInput)
//    assertThat(output2).isEqualTo(45000)
//
//    // Answer
//    val outputAnswer2 = part2(input)
//    assertThat(outputAnswer2).isEqualTo(199357)
}
