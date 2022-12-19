import org.assertj.core.api.Assertions.assertThat


fun level(c: Char): Int = when (c) {
    'S' -> 0
    'E' -> 'z' - 'a' + 1
    else -> c - 'a'
}


fun findCoord(input: List<String>, char: Char): Point = input
    .withIndex()
    .map {(index, it) ->
        Point(index, it.indexOf(char))
    }.find{
        it.x != -1
    } ?: Point(0, 0)


data class Point(val y: Int, val x: Int)



fun main() {
    fun part1(input: List<String>): Int {
        val start = findCoord(input, 'S')
        val end = findCoord(input, 'E')
        val height = input.size
        val width = input.first().length

        var distance = 0
        fun pathFind(): Int {
            var path: MutableList<Point> = arrayListOf()

            val q: ArrayDeque<Pair<Point, Int>> = ArrayDeque(0)
            q.add(Pair(start, 0))
            var visited = mutableSetOf<Point>(end)
            while(q.isNotEmpty()) {
                val (p, distance) = q.removeFirst()

                val up = Point(p.y-1, p.x)
                val down = Point(p.y+1, p.x)
                val left = Point(p.y, p.x-1)
                val right = Point(p.y, p.x+1)
                for (pp in listOf(down, up, left, right)) {
                    if (pp.y >= 0 && pp.x >= 0 && pp.y < height && pp.x < width) {
                        val currentStep = input[p.y][p.x]
                        val nextStep = input[pp.y][pp.x]
                        val standingLevel = level(currentStep)
                        val nextLevelMax = level(nextStep)
                        if (nextLevelMax <= standingLevel+1) {
                            if (pp == end) {
                                return distance +1
                            }
                            if (visited.add(pp)) {
                                q.add(Pair(pp, distance+1))
                            }
                        }
                    }
                }
            }
            return distance
        }
        return pathFind()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    // Test
    val testInput = readInput("Day12_test")
    val output1 = part1(testInput)
    assertThat(output1).isEqualTo(31)

    // Answer
    val input = readInput("Day12")
    val outputAnswer1 = part1(input)
    assertThat(outputAnswer1).isEqualTo(383)
//
//    // Test
//    val output2 = part2(testInput)
//    assertThat(output2).isEqualTo(45000)
//
//    // Answer
//    val outputAnswer2 = part2(input)
//    assertThat(outputAnswer2).isEqualTo(199357)
}
