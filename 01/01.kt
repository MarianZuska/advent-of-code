fun main() {
    val list = generateSequence(::readlnOrNull).toList()

    val groups = list.scan(0) { x, s -> if (s == "") x + 1 else x }

    val sortedElves = list
        .asSequence()
        .map { it.toIntOrNull() }
        .withIndex()
        .filter { it != null }
        .groupBy { groups[it.index] }
        .map { it.value.sumOf { it.value ?: 0 }}
        .sortedDescending()

    println(sortedElves.take(1).first())
    println(sortedElves.take(3).sum())
}