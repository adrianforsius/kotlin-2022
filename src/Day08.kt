import org.assertj.core.api.Assertions.assertThat


fun main() {
    fun part1(input: List<String>): Int {
        var treeMap = input.map {
            it.map {
                it.digitToInt()
            }
        }

        val size = treeMap.size
        class Point(val x: Int, val y: Int) {
            val tree: Int = treeMap[y][x]
            fun toLeft(): Boolean {
                if (x == 0) {
                    return true
                }
                for (i in 0..x-1) {
                    if(treeMap[y][i] >= tree) {
                        return false
                    }
                }
                return true
            }
            fun toRight(): Boolean {
                if (x == treeMap.size) {
                    return true
                }
                for (i in x+1..treeMap.size-1) {
                    if(treeMap[y][i] >= tree) {
                        return false
                    }
                }
                return true
            }
            fun toTop(): Boolean {
                if (y == 0) {
                    return true
                }
                for (i in 0..y-1) {
                    if(treeMap[i][x] >= tree) {
                        return false
                    }
                }
                return true
            }
            fun toBottom(): Boolean {
                if (y == treeMap.size) {
                    return true
                }
                for (i in y+1..treeMap.size-1) {
                    if(treeMap[i][x] >= tree) {
                        return false
                    }
                }
                return true
            }
        }

        var count = 0
        var trees = listOf<Point>()
        for ((rowIdx, row) in treeMap.withIndex()) {
            for ((colIdx, col) in row.withIndex()) {
                val p = Point(colIdx, rowIdx)
                if (p.toTop()) {
                    count++
                    continue
                }
                if (p.toBottom()) {
                    count++
                    continue
                }
                if (p.toRight()) {
                    count++
                    continue
                }
                if (p.toLeft()) {
                    count++
                    continue
                }
                trees += p
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var treeMap = input.map {
            it.map {
                it.digitToInt()
            }
        }

        val size = treeMap.size
        class Point(val x: Int, val y: Int) {
            val tree: Int = treeMap[y][x]
            fun toLeft(): Int {
                var count = 0
                if (x == 0) {
                    return count
                }
                for  (i in x-1 downTo 0) {
                    count++
                    if(treeMap[y][i] >= tree) {
                        return count
                    }
                }
                return count
            }
            fun toRight(): Int {
                var count = 0
                if (x == treeMap.size) {
                    return count
                }
                for (i in x+1..treeMap.size-1) {
                    count++
                    if(treeMap[y][i] >= tree) {
                        return count
                    }
                }
                return count
            }
            fun toTop(): Int {
                var count = 0
                if (y == 0) {
                    return count
                }
                for (i in y-1 downTo 0) {
                    count++
                    if(treeMap[i][x] >= tree) {
                        return count
                    }
                }
                return count
            }
            fun toBottom(): Int {
                var count = 0
                if (y == treeMap.size) {
                    return count
                }
                for (i in y+1..treeMap.size-1) {
                    count++
                    if(treeMap[i][x] >= tree) {
                        return count
                    }
                }
                return count
            }
        }

        var bestViewScore = 0
        var trees = listOf<Point>()
        for ((rowIdx, row) in treeMap.withIndex()) {
            for ((colIdx, col) in row.withIndex()) {
                val p = Point(colIdx, rowIdx)
                var count = listOf<Int>()
                count+= p.toTop()
                count+= p.toBottom()
                count+= p.toRight()
                count+= p.toLeft()
                val viewScore = count.reduce{prev, next -> prev * next}
                if (bestViewScore < viewScore) {
                    bestViewScore = viewScore
                }
            }
        }
        println(trees)
        return bestViewScore
    }

    // test if implementation meets criteria from the description, like:
    // Test
    val testInput = readInput("Day08_test")
    val output1 = part1(testInput)
    assertThat(output1).isEqualTo(21)

    // Answer
    val input = readInput("Day08")
    val outputAnswer1 = part1(input)
    assertThat(outputAnswer1).isEqualTo(1690)

    // Test
    val output2 = part2(testInput)
    assertThat(output2).isEqualTo(8)

    // Answer
    val outputAnswer2 = part2(input)
    assertThat(outputAnswer2).isEqualTo(535680)
}
