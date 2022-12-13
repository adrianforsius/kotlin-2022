import org.assertj.core.api.Assertions.assertThat




fun main() {
    fun part1(input: List<String>): Int {
        data class Point(var x: Int, var y: Int)
        var visited = listOf(Point(0,0))


        fun visit(x: Int, y: Int): Point {
            val p = Point(x, y)
            visited += p
            return p
        }
        var head = Point(0,0)
        var tail = Point(0,0)
        input.map{
            val part = it.split(" ")
            val step = part.last().toInt()
            var start = tail.copy()
            when (part.first()) {
                "R" -> head.x = step +  head.x
                "L"->  head.x = (step*-1) + head.x
                "U"-> head.y = step + head.y
                "D"-> head.y = (step*-1) + head.y
            }

            while (true) {
                var deltaX = head.x - start.x
                var deltaY = head.y - start.y
                var delta = Point(deltaX, deltaY)
                when {
                    delta.y > 1 -> start.x = head.x
                    delta.y < -1 -> start.x = head.x
                    delta.x > 1 -> start.y =  head.y
                    delta.x < -1 -> start.y = head.y
                }
                when {
                    delta.y > 1 -> tail = visit(start.x, start.y + 1)
                    delta.y < -1 -> tail = visit(start.x, start.y - 1)
                    delta.x > 1 -> tail = visit(start.x + 1, start.y)
                    delta.x < -1 -> tail = visit(start.x - 1, start.y)
                   else -> break
                }
                when {
                    delta.y > 1 -> start.y++
                    delta.y < -1 -> start.y--
                    delta.x > 1 -> start.x++
                    delta.x < -1 -> start.x--
                }
             }
        }
        return visited.toSet().count()
    }

    fun part2(input: List<String>): Int {
        data class Point(var x: Int, var y: Int, val visited: List<Point> = emptyList())

        var rope: MutableList<Point> = MutableList(10) { Point(0, 0) }
        fun visit(p: Point, x: Int, y: Int): Point {
            return Point(x, y, p.visited+Point(x, y))
        }
        input.map {
            val part = it.split(" ")
            val step = part.last().toInt()
            val direction = part.first()
            repeat(step) {
                rope.windowed(2).withIndex().forEach {(index, _) ->
                    var head = rope[index]
                    var tail = rope[index+1]
                    if (index == 0 ) {
                        when (direction) {
                            "R" -> head.x++
                            "L" -> head.x--
                            "U" -> head.y++
                            "D" -> head.y--
                        }
                    }

                    var deltaX = head.x - tail.x
                    var deltaY = head.y - tail.y
                    when {
                        deltaY > 1 -> tail = visit(tail, head.x, tail.y + 1)
                        deltaY < -1 -> tail = visit(tail, head.x, tail.y - 1)
                        deltaX > 1 -> tail = visit(tail, tail.x + 1, head.y)
                        deltaX < -1 -> tail = visit(tail, tail.x - 1, head.y)
                    }

                    rope[index+1] = tail
                }
            }
        }
        val out = rope.last().visited.toSet().toList().sortedBy { it.x }
        return out.count() // center
    }

    // test if implementation meets criteria from the description, like:
    // Test
    val testInput = readInput("Day09_test")
//    val output1 = part1(testInput)
//    assertThat(output1).isEqualTo(13)


//    // Answer
    val input = readInput("Day09")
//    val outputAnswer1 = part1(input)
//    assertThat(outputAnswer1).isEqualTo(5874)

    // Test
    val output2 = part2(testInput)
    assertThat(output2).isEqualTo(0)

    // Test 1
    val testInput1 = readInput("Day09_test_1")
    assertThat(part2(testInput1)+1).isEqualTo(36)


    // Answer
    val outputAnswer2 = part2(input)
    assertThat(outputAnswer2+1).isEqualTo(2467)
}
