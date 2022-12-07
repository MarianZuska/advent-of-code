
data class node(var name: String, var value: Int, val prev: node?, val next: MutableList<node>)

fun addChildSizes(dir: node): Int {
    var size = dir.value
    size += dir.next.sumOf { addChildSizes(it) }
    dir.value = size
    return size
}

fun main() {
    val root = node("/", 0, null, mutableListOf())
    var current = root
    val nodes: MutableList<node> = mutableListOf(root)

    generateSequence(::readlnOrNull)
        .map {it.split(" ")}
        .forEach {
            when {
                it == listOf("$", "cd", "..") -> current = current.prev!!
                it == listOf("$", "cd", "/") -> current = root
                it[1] == "cd" -> current = current.next.find { sub -> sub.name == it[2] }!!
                it[0].toIntOrNull() != null -> current.value += it[0].toInt()
                it[0] == "dir" -> {
                    val subDir = node(it[1], 0, current, mutableListOf())
                    nodes.add(subDir)
                    current.next.add(subDir)
                }
            }
        }

    addChildSizes(root)
    
    val out1 = nodes.map{it.value}.filter { it < 100000 }.sum()
    println(out1)

    val needed = 30000000 - (70000000 - root.value)
    val out2 = nodes.map{it.value}.sorted().first { it > needed }
    println(out2)

}
