import org.assertj.core.api.Assertions.assertThat


fun main() {
    fun part1(input: List<String>): Int {
        return input.map  {
            val lines = it.split("\n")
            lines.map {
                val parts = it.split(",")
                val part1 = parts[0].split("-")
                val part2 = parts[1].split("-")
                val range1 = part1[0].toInt().rangeTo(part1[1].toInt())
                val range2 = part2[0].toInt().rangeTo(part2[1].toInt())
                val match1 = range1.all{
                    it in range2
                }
                val match2 = range2.all{
                    it in range1
                }
                if (match1 || match2) 1 else 0
            }.sum()
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map  {
            val lines = it.split("\n")
            lines.map {
                val parts = it.split(",")
                val part1 = parts[0].split("-")
                val part2 = parts[1].split("-")
                val range1 = part1[0].toInt().rangeTo(part1[1].toInt())
                val range2 = part2[0].toInt().rangeTo(part2[1].toInt())
                val match1 = range1.any{
                    it in range2
                }
                val match2 = range2.any{
                    it in range1
                }
                if (match1 || match2) 1 else 0
            }.sum()
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    // Test
    val testInput = readInput("Day04_test")
    val output1 = part1(testInput)
    assertThat(output1).isEqualTo(2)

    // Answer
    val input = readInput("Day04")
    val outputAnswer1 = part1(input)
    assertThat(outputAnswer1).isEqualTo(657)

    // Test
    val output2 = part2(testInput)
    assertThat(output2).isEqualTo(4)

    // Answer
    val outputAnswer2 = part2(input)
    assertThat(outputAnswer2).isEqualTo(938)
}
