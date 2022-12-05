import org.assertj.core.api.Assertions.assertThat

fun code(it: Char): Int = when (it) {
    in 'a'..'z' -> it - 'a' + 1
    in 'A'..'Z' -> it - 'A' + 27
    else -> throw error("nope")
}


fun main() {
    fun part1(input: List<String>): Int = input.map{
            it.split("\n")
            listOf(it.substring(0, it.length/2), it.substring(it.length/2, it.length))
        }.map{ that ->
            that.first().filter{
                it in (that[1])
            }
        }.sumOf{
           code(it.first())
        }

    fun part2(input: List<String>): Int = input.map {
        it.split("\n")
    }.map{
        it.first().toSet()
    }.chunked(3).flatMap{
        it.reduce { acc, next -> acc.intersect(next) }
    }.sumOf { code(it) }


    // test if implementation meets criteria from the description, like:
    // Test
    val testInput = readInput("Day03_test")
    val output1 = part1(testInput)
    assertThat(output1).isEqualTo(157)

    // Answer
    val input = readInput("Day03")
    val outputAnswer1 = part1(input)
    assertThat(outputAnswer1).isEqualTo(8088)

    // Test
    val output2 = part2(testInput)
    assertThat(output2).isEqualTo(70)

    // Answer
    val outputAnswer2 = part2(input)
    assertThat(outputAnswer2).isEqualTo(2522)
}
