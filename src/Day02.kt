import org.assertj.core.api.Assertions.assertThat

val opponent = mapOf(
    "A" to 1,
    "B" to 2,
    "C" to 3,
)

val player = mapOf(
    "X" to 1,
    "Y" to 2,
    "Z" to 3,
)

fun getWinner(opp: Int): Int = when(opp) {
    1 -> 2
    2 -> 3
    3 -> 1
    else -> error("nope")
}

fun getLoser(opp: Int): Int = when(opp) {
    1 -> 3
    2 -> 1
    3 -> 2
    else -> error("nope")
}

fun main() {
    fun part1(input: List<String>): Int {
        val points = input.map {
            it.split(" ").toTypedArray()
        }.sumOf {
            val played = player.getValue(it[1])
            val faced = opponent.getValue(it[0])
            when {
                played == faced -> 3 + played
                played - faced == 1 -> 6 + played
                played - faced == -2 -> 6 + played
                played - faced == 2 -> played
                played - faced == -1 -> played
                else -> error("should not happen")
            }
        }
        return points
    }

    fun part2(input: List<String>): Int {
        val points = input.map {
            it.split(" ").toTypedArray()
        }.sumOf {
            val strategy = player.getValue(it[1])
            val faced = opponent.getValue(it[0])
            when {
                strategy == 1 -> getLoser(faced)
                strategy == 2 -> 3 + faced
                strategy == 3 -> 6 + getWinner(faced)
                else -> error("should not happen")
            }
        }
        return points
    }

    // test if implementation meets criteria from the description, like:
    // Test
    val testInput = readInput("Day02_test")
    val output1 = part1(testInput)
    assertThat(output1).isEqualTo(15)

    val testInput1 = readInput("Day02_test_1")
    val outputTest1 = part1(testInput1)
    assertThat(outputTest1).isEqualTo(15)

    // Answer
    val input = readInput("Day02")
    val outputAnswer1 = part1(input)
    assertThat(outputAnswer1).isEqualTo(13268)

    // Test
    val output2 = part2(testInput)
    assertThat(output2).isEqualTo(12)

    // Answer
    val outputAnswer2 = part2(input)
    assertThat(outputAnswer2).isEqualTo(15508)
}
