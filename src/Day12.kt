import org.assertj.core.api.Assertions.assertThat


fun level(c: Char): Int = when (c) {
    'S' -> 0
    'E' -> 0
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

class Queue(var q: MutableList<MutableList<Point>>) {
    fun pop(): MutableList<Point> {
        return q.removeFirst()
    }

    fun push(p: MutableList<Point>)  {
        var index: Int = q.indexOfFirst{
            p.size < it.size
        }
        if (index == -1) {
            q.add(p)
        } else {
            q.add(index+1, p)
        }
    }
    fun peek(): Boolean {
        return q.size > 0
    }
}



fun main() {
    fun part1(input: List<String>): Int {
        val start = findCoord(input, 'S')
        val end = findCoord(input, 'E')
        val height = input.size
        val width = input.first().length

        fun pathFind(): MutableList<Point> {
            val q = Queue(arrayListOf(arrayListOf(start)))
            while(q.peek()) {
                val track = q.pop()
                val p = track.last()

                val down = Point(p.y-1, p.x)
                val up = Point(p.y+1, p.x)
                val left = Point(p.y, p.x-1)
                val right = Point(p.y, p.x+1)
                for (pp in listOf(down, up, left, right)) {
                    if (pp.y >= 0 && pp.x >= 0 && pp.y < height && pp.x < width) {
                        if (pp !in track) {
                            val currentStep = input[p.y][p.x]
                            val nextStep = input[pp.y][pp.x]
                            val standingLevel = level(currentStep)
                            val nextLevelMax = level(nextStep)+1
                            if (standingLevel <= nextLevelMax ) {
                                val next = track.toMutableList()
                                next.add(pp)

                                if (pp == end) {
                                    return next
                                }

                                q.push(next)
                            }
                        }
                    }
                }
            }
            return arrayListOf()
        }
        return pathFind().size
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    // Test
    val testInput = readInput("Day12_test")
    val output1 = part1(testInput)
    assertThat(output1).isEqualTo(31)

//    // Answer
//    val input = readInput("Day12")
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
