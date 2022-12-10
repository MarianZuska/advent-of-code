data class Node(var name: String, var value: Int, val prev: Node?, val next: MutableList<Node>)

fun addChildSizes(dir: Node): Int {
    var size = dir.value + dir.next.sumOf { addChildSizes(it) }
    dir.value = size
    return size
}


fun main() {
    val root = Node("/", 0, null, mutableListOf())
    var current = root
    val nodes: MutableList<Node> = mutableListOf(root)

    fun processCommand(cmd: String) {
        val (cmd, arg) = cmd.split(" ")
        when (cmd) {
            "cd" -> current = when (arg) {
                ".." -> current.prev!!
                "/" -> root
                else -> current.next.find { sub -> sub.name == arg }!!
            }

            "dir" -> {
                val subDir = Node(arg, 0, current, mutableListOf())
                nodes.add(subDir)
                current.next.add(subDir)
            }

            else -> current.value += cmd.toInt()
        }
    }

    generateSequence(::readlnOrNull)
        .map { it.replace("$ ", "") }
        .filter { it != "ls" }
        .forEach(::processCommand)

    addChildSizes(root)

    val sizes = nodes
        .map { it.value }

    val out1 = sizes
        .filter { it <= 100000 }
        .sum()

    val needed = 30000000 - (70000000 - root.value)
    val out2 = sizes
        .filter { it >= needed }
        .min()

    println(out1)
    println(out2)

    assert(out1 == 1367870) { "First answer is wrong." }
    assert(out2 == 549173) { "Second answer is wrong." }
}
