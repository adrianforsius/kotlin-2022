import org.assertj.core.api.Assertions.assertThat


data class Monkey(
    val items: MutableList<Long> = mutableListOf(),
    val worry: (Long) -> Long,
    var inspections: Long = 0,
)

data class MonkeyPart1(
    val monkey: Monkey,
    val next: (Long) -> Int,
)

data class MonkeyPart2(
    val monkey: Monkey,
    val dividedBy: Long = 1,
    val destTrue: Int = 0,
    val destFalse: Int = 0,
)

fun main() {
    fun part1(input: String): Long {
        val monkeys = input.split("\n\n").map {
            var worry: (i: Long) -> Long = fun(i: Long): Long { return i }
            var items = mutableListOf<Long>()
            var div = 1
            var destTrue = 0
            var destFalse = 0
            it.split("\n").map {
                when {
                    it.contains("Starting items: ") -> items = it.split(": ")[1].split(", ").map { it.toLong() }.toMutableList()
                    it.contains("Operation: ") -> it.split(" new = ")[1].let {
                        val (_, op, val2) = it.split(" ")
                        val f: (Long, Long) -> Long = when (op) {
                            "+" -> Long::plus
                            "*" -> Long::times
                            else -> error("invalid operator")
                        }
                        worry = fun(i: Long): Long = if (val2 == "old") f(i,i) else f(i, val2.toLong())
                    }
                    it.contains("Test: ") -> div = it.split(" divisible by ")[1].toInt()
                    it.contains("If true: ") -> destTrue = it.split(" throw to monkey ")[1].toInt()
                    it.contains("If false: ") -> destFalse = it.split(" throw to monkey ")[1].toInt()
                }
            }
            MonkeyPart1(
                Monkey(
                    items,
                    worry,
                ),
                fun(i: Long): Int =
                    when (i.toInt() % div == 0) {
                        true -> destTrue
                        false -> destFalse
                    },
            )
        }
        for (round in 1..20) {
            for (monkey in monkeys) {
                repeat(monkey.monkey.items.size) {
                    val item = monkey.monkey.items.removeFirst()
                    val level = Math.floor(monkey.monkey.worry(item) / 3.0).toLong()
                    monkey.monkey.inspections++
                    monkeys[monkey.next(level)].monkey.items.add(level)
                }
            }
        }
        return monkeys.sortedByDescending { it.monkey.inspections }.take(2).flatMap { monkey: MonkeyPart1 -> listOf(monkey.monkey.inspections) }
            .reduce { acc, el -> acc * el }
    }

    fun part2(input: String): Long {
        val monkeys = input.split("\n\n").map {
            var worry: (Long) -> Long = fun(i: Long):Long { return i}
            var items = mutableListOf<Long>()
            var div = 1L
            var destTrue = 0
            var destFalse = 0
            it.split("\n").map {
                when {
                    it.contains("Starting items: ") -> items = it.split(": ")[1].split(", ").map { it.toLong() }.toMutableList()
                    it.contains("Operation: ") -> it.split(" new = ")[1].let {
                        val (_, op, val2) = it.split(" ")
                        val f: (Long, Long) -> Long = when (op) {
                            "+" -> Long::plus
                            "*" -> Long::times
                            else -> error("invalid operator")
                        }
                        worry = fun(i: Long): Long = if (val2 == "old") f(i,i) else f(i, val2.toLong())
                    }
                    it.contains("Test: ") -> div = it.split(" divisible by ")[1].toLong()
                    it.contains("If true: ") -> destTrue = it.split(" throw to monkey ")[1].toInt()
                    it.contains("If false: ") -> destFalse = it.split(" throw to monkey ")[1].toInt()
                }
            }
            MonkeyPart2(Monkey(items, worry), div, destTrue, destFalse)
        }

        val modulo = monkeys
            .map { it.dividedBy }
            .reduce { acc, i -> acc * i }

        for (round in 1..10_000) {
            for (monkey in monkeys) {
                repeat(monkey.monkey.items.size) {
                    val item = monkey.monkey.items.removeLast()
                    monkey.monkey.inspections++
                    val level = monkey.monkey.worry(item).mod(modulo)
                    val next = when (level.mod(monkey.dividedBy) == 0L) {
                        true ->  monkey.destTrue
                        false -> monkey.destFalse
                    }
                    monkeys[next].monkey.items.add(level)
                }
            }
        }
        return monkeys
            .sortedByDescending { it.monkey.inspections }
            .take(2)
            .flatMap { monkey: MonkeyPart2 -> listOf(monkey.monkey.inspections) }
            .reduce{ acc, em -> acc*em }
    }

    // test if implementation meets criteria from the description, like:
    // Test
    val testInput = readText("Day11_test")
    val output1 = part1(testInput)
    assertThat(output1).isEqualTo(10605)

    // Answek
    val input = readText("Day11")
    val outputAnswer1 = part1(input)
    assertThat(outputAnswer1).isEqualTo(90294)

    // Test
    val output2 = part2(testInput)
    assertThat(output2).isEqualTo(2713310158)

    // Answer
    val outputAnswer2 = part2(input)
    assertThat(outputAnswer2).isEqualTo(18170818354L)
}
