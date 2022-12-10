fun main() {
    val lines = generateSequence(::readlnOrNull).toList()

    val groups = lines.scan(0) { elf, line -> if (line == "") elf + 1 else elf }

    val sortedElves = lines
        .asSequence()
        .map { it.toIntOrNull() }
        .withIndex()
        .groupBy { groups[it.index] }
        .map { elf -> elf.value.sumOf { it.value ?: 0 } }
        .sortedDescending()

    val out1 = sortedElves.first()
    println(out1)

    val out2 = sortedElves.take(3).sum()
    println(out2)

    assert(out1 == 69883) { "First answer is wrong." }
    assert(out2 == 207576) { "Second answer is wrong." }
}