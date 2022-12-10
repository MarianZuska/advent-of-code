// much of this is needlessly functional, but I wanted to try to use this feature as much as possible on this day
fun main() {
    val (tower, moves) = generateSequence(::readlnOrNull).joinToString("\n").split("\n\n")

    val stacks: MutableList<MutableList<Char>> = MutableList(9) { mutableListOf() }

    tower
        .split("\n")
        .reversed()
        .drop(1)
        .forEach { for (i in 1..it.length step 4) if (it[i] != ' ') stacks[i / 4].add(it[i]) }

    val nums = moves
        .split("\n")
        .map { move -> move.filter { !it.isLetter() } }
        .map { it.split("  ") }
        .map { move -> move.map { it.trim().toInt() } }

    for ((n, start, end) in nums) {
        val startStack = stacks[start - 1]
        val endStack = stacks[end - 1]
        val len = startStack.size

        // use this line instead of next for star 1
        // endStack.addAll(startStack.subList(len-n,len).reversed())
        endStack.addAll(startStack.subList(len - n, len))
        stacks[start - 1] = startStack.dropLast(n).toMutableList()
    }

    val arrangement = stacks.map { it.last() }.joinToString("")

    println(arrangement)

    // assert(arrangement == "TQRFCBSJJ") { "First answer is wrong." }
    assert(arrangement == "RMHFJNVFP") { "Second answer is wrong." }
}