
import org.assertj.core.api.Assertions.assertThat
import java.lang.Integer.max
import java.lang.Integer.min

fun rangeBetween(a: Int, b: Int) = min(a, b) .. max(a, b)

fun main() {
    fun part1(input: List<String>): Int {
        val lines = input.map {
            it.split(" -> ")
                .map {
                    val (x, y) = it.split(",").map {
                        it.toInt()
                    }
                    Point(y, x)
                }
                .windowed(2)
                .map { it ->
                        val (first, second) = it
                        var points = mutableListOf<Point>()
                        for (y in rangeBetween(first.y, second.y)) {
                            for (x in rangeBetween(first.x, second.x)) {
                                points.add(Point(y,x))
                            }
                        }
                        points
                }.flatten()
            }
        val points = lines.flatten().toSet().toList()

        val sandStart = Point(0, 500)
        val pointsWithSand = points + sandStart
        val maxX = pointsWithSand.sortedByDescending { it.x }.take(1).first().x
        val maxY = pointsWithSand.sortedByDescending { it.y }.take(1).first().y
        val minX = pointsWithSand.sortedBy { it.x }.take(1).first().x
        val minY = pointsWithSand.sortedBy { it.y }.take(1).first().y

        val grid = MutableList(maxY - minY + 1) { MutableList(maxX - minX + 1) { "." } }
        for (row in grid) {
            println(row)
        }


        for (y in (minY.rangeTo(maxY))) {
            for (x in (minX.rangeTo(maxX))) {
                val p = points.firstOrNull { it.x == x && it.y == y }
                if (p != null) {
                    grid[y - minY][x - minX] = "#"
                }
            }
        }
        grid[sandStart.y - minY][sandStart.x - minX] = "+"

        var activeSandRel = Point(sandStart.y - minY, sandStart.x - minX)
        var activeSand = activeSandRel.copy()
        var count = 0
        while(true) {
            when {
                activeSand.y+1 > maxY-minY -> break
                activeSand.x-1 < 0 -> break
                activeSand.x+1 > maxX-minX -> break
                grid[activeSand.y+1][activeSand.x] == "." -> activeSand.y++
//                grid[activeSand.y+1][activeSand.x] == "#" -> {}
                grid[activeSand.y+1][activeSand.x-1] == "." ->  { activeSand.y++ ; activeSand.x-- }
//                grid[activeSand.y+1][activeSand.x-1] == "#" ->  {}
                grid[activeSand.y+1][activeSand.x+1] == "." ->  { activeSand.y++ ; activeSand.x++ }
//                grid[activeSand.y+1][activeSand.x+1] == "#" ->  {
                else -> {
                    grid[activeSand.y][activeSand.x] = "#"
                    activeSand = activeSandRel.copy()
                    count++
                }
            }
//            for (row in grid) {
//                println(row)
//            }
//            println("--------------------")
        }


        return count
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    // Test
//    val testInput = readInput("Day14_test")
//    val output1 = part1(testInput)
//    assertThat(output1).isEqualTo(24)

    // Answer
    val input = readInput("Day14")
    val outputAnswer1 = part1(input)
    assertThat(outputAnswer1).isEqualTo(897)

//    // Test
//    val output2 = part2(testInput)
//    assertThat(output2).isEqualTo(45000)
//
//    // Answer
//    val outputAnswer2 = part2(input)
//    assertThat(outputAnswer2).isEqualTo(199357)
}
