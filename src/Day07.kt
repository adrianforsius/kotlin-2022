
import org.assertj.core.api.Assertions.assertThat

//fun part1(path: Path): Int = path.useLines{ lines ->
//    lines
//        .count()

data class Node(var children:List<Node>, val name: String, var size: Int = 0, val marked: Boolean=false) {
    var parent: Node? = null
}

fun cd(curr: Node, root: Node, cmd: String): Node =
    when (cmd) {
        "/" -> root
        ".." -> curr.parent!!
        else -> curr.children.first{ it.name == cmd }
    }

fun addFolder(curr: Node, name: String) {
    if (name in curr.children.filter{ it.size == 0}.map{ it.name }.toList()) return
    val folder = Node(children=emptyList(), name=name)
    folder.parent = curr
    curr.children = curr.children + folder
    return
}

fun addFile(curr: Node, size: Int, name: String) {
    val file = Node(emptyList(), name, size)
    file.parent = curr
    curr.children = curr.children + file
    curr.children.toSet().toList()
    return
}

fun ls(curr: Node, files: List<String>) {
    for (f in files) {
        if (f == "") continue
        if (f.startsWith("dir")) {
            addFolder(curr, f.split(" ").last())
        } else {
            val (size, name) = f.split(" ")
            addFile(curr, size.toInt(), name )
        }
    }
}

data class Pair(var current: Int, var total: Int)

data class PairV2(var current: Int, var fit: Int)
fun traverse(n: Node, pair: Pair): Pair {
    if (n.children.size == 0) {
        return Pair(n.size, pair.total)
    }

    var current = 0
    var p = pair
    for (n in n.children) {
        p = traverse(n, p)
        current += p.current
    }

    var out = Pair(current, p.total)
    if (current < 100_000) {
        out.total += current
    }
    return out
}

fun traverseTotal(n: Node, total: Int): Int {
    if (n.children.size == 0) {
        return n.size
    }

    var current = 0
    for (n in n.children) {
        current += traverseTotal(n, total)
    }

    return current
}

fun traverse_v2(n: Node, pair: PairV2, missing: Int): PairV2 {
    if (n.children.size == 0) {
        return PairV2(n.size, pair.fit)
    }

    var current = 0
    var p = pair
    for (n in n.children) {
        p = traverse_v2(n, p, missing)
        current += p.current
    }

    var fit = p.fit
    if (current >= missing && current < fit) {
        fit = current
    }
    return PairV2(current, fit)
}



fun main() {
    fun part1(input: String): Int {
        val root = Node(emptyList(), "/", 0)
        var curr = root

        val cmd = input.split("$ ").drop(1)
        for (i in cmd.drop(1)) {
            val start = i.slice(0..1)
            when (start) {
                "ls" -> ls(curr, i.split("\n").drop(1))
                "cd" -> curr = cd(curr, root, i.trimEnd('\n').split(" ").last())
                else -> error("invalid command")
            }
        }

        return traverse(root, Pair(0,0)).total
    }

    fun part2(input: String): Int {
        val root = Node(emptyList(), "/", 0)
        var curr = root

        val cmd = input.split("$ ").drop(1)
        for (i in cmd.drop(1)) {
            val start = i.slice(0..1)
            when (start) {
                "ls" -> ls(curr, i.split("\n").drop(1))
                "cd" -> curr = cd(curr, root, i.trimEnd('\n').split(" ").last())
                else -> error("invalid command")
            }
        }

        val total = traverseTotal(root, 0)
        val used = 70_000_000 - total
        val missing = 30_000_000 - used
        return traverse_v2(root, PairV2(0, Int.MAX_VALUE), missing).fit
    }

    // test if implementation meets criteria from the description, like:
    // Tes
    val testInput = readText("Day07_test")
    val output1 = part1(testInput)
    assertThat(output1).isEqualTo(95437)

    // Answer
    val input = readText("Day07")
    val outputAnswer1 = part1(input)
    assertThat(outputAnswer1).isEqualTo(2104783)

    // Test
//    val output2 = part2(testInput)
//    assertThat(output2).isEqualTo(24933642)

    // Answer
    val outputAnswer2 = part2(input)
    assertThat(outputAnswer2).isEqualTo(5883165)
}
