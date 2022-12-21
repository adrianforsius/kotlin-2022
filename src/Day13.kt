import org.assertj.core.api.Assertions.assertThat

fun order(a: List<Any>, b: List<Any>): Int {
    for ((index, _) in a.withIndex()) {
        val r = b.getOrNull(index) ?: return -1
        val l = a.get(index)

        if (l is Int && r is Int) {
            if (l == r) continue
            return if (l < r) 1 else -1
        }
        if (l is List<*> && r is Int) {
            val v = order(l as List<Any>, listOf(r))
            if (v == 0) continue
            return v
        }
        if (l is Int && r is List<*>) {
            val v = order(listOf(l), r as List<Any>)
            if (v == 0) continue
            return v
        }
        if (l is List<*> && r is List<*>) {
            val v = order(l as List<Any>, r as List<Any>)
            if (v == 0) continue
            return v
        }
    }
    if (b.size == a.size) return 0
    return 1
}

fun parse(s: MutableList<String>, n: MutableList<Any>): MutableList<Any> {
    if (s.isEmpty()) return mutableListOf()
    do {
        when (val next = s.removeFirst()) {
            "[" -> {
                val nn = parse(s, mutableListOf())
                n.add(nn)
            }
            "]" -> return n
            "," -> {}
            "" -> {}
            else -> n.add(next.toInt())
        }
    } while (s.size > 0)
    return n
}

fun ordered(left: MutableList<Any>, right: MutableList<Any>): Boolean? {
    while (left.size > 0) {
        val r = right.removeFirstOrNull() ?: return false
        val l = left.removeFirst()


        if (l is Int && r is Int) {
            if (l == r) continue
            return l < r
        }
        if (l is List<*> && r is Int) return ordered(l as MutableList<Any>, mutableListOf(r)) ?: continue
        if (l is Int && r is List<*>) return ordered(mutableListOf(l), r as MutableList<Any>) ?: continue
        if (l is List<*> && r is List<*>) {
            return ordered(l as MutableList<Any>, r as MutableList<Any>) ?: continue
        }
    }
    if (right.size == 0) return null
    return true
}

fun main() {
    fun part1(input: String): Int {
        val packets = input
            .split("\n\n")
            .map {
                val (left, right) = it
                    .split("\n")
                    .map{
                        var s = it
                        var new: MutableList<String> = mutableListOf()
                        for ((index, c) in it.withIndex()) {
                            when {
                                c in "0123456789".toCharArray() -> {
                                    val rest = s.slice(index..s.length-1)
                                    val numEnd = rest.indexOfFirst { it in "[],".toCharArray() }
                                    val num = s.slice(index..index+numEnd-1)
                                    new.add(num)
                                }
                                else -> new.add(c.toString())
                            }
                        }
                        new
                    }

                Pair(parse(left, mutableListOf()), parse(right, mutableListOf()))
            }

        var count = 0
        var indice = mutableListOf<Int>()
        var index = 1
        packets.map { (left, right) ->
            if (ordered(left, right)!!) {
                count += index

            }
            index++
        }
        println(indice)
        return count
    }

    fun part2(input: String): Int {
        val inputWithDelim = input + "[[2]]\n" + "[[6]]\n"

        val packets = inputWithDelim
            .replace("\n\n", "\n")
            .split("\n")
            .map {
                var s = it
                var new: MutableList<String> = mutableListOf()
                for ((index, c) in s.withIndex()) {
                    when {
                        c in "0123456789".toCharArray() -> {
                            val rest = s.slice(index..s.length-1)
                            val numEnd = rest.indexOfFirst { it in "[],".toCharArray() }
                            val num = s.slice(index..index+numEnd-1)
                            new.add(num)
                        }
                        else -> new.add(c.toString())
                    }
                }
                parse(new, mutableListOf())
            }.filter{ it.isNotEmpty() }.map{
                it.first()
            }


        val s = packets.sortedWith{a, b -> order(b as List<Any> , a as List<Any>) }
//        for (ss in s) {
//            println(ss.toString())
//        }
        val divider = Pair("[[2]]","[[6]]")
        val index = Pair(s.indexOfFirst { it.toString() == divider.first }+1, s.indexOfFirst { it.toString() == divider.second }+1)

        return index.first*index.second
    }

    // test if implementation meets criteria from the description, like:
    // Test
    val testInput = readText("Day13_test")
    val output1 = part1(testInput)
    assertThat(output1).isEqualTo(13)

    // Answer
    val input = readText("Day13")
    val outputAnswer1 = part1(input)
    assertThat(outputAnswer1).isEqualTo(6187)
    // Test
    val output2 = part2(testInput)
    assertThat(output2).isEqualTo(140)

    // Answer
    // too high - 24720
    val outputAnswer2 = part2(input)
    assertThat(outputAnswer2).isEqualTo(23520)
}
