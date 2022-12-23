private operator fun Pair<Int, Int>.plus(o: Pair<Int, Int>) = Pair(this.first + o.first, this.second + o.second)

fun main() {
    val terrain = generateSequence { readlnOrNull() }.toList()
    val elves = mutableListOf<Pair<Int, Int>>()
    for (y in terrain.indices) for (x in terrain[0].indices) if (terrain[y][x] == '#') elves.add(Pair(x, y))

    val dirs = listOf(
        listOf(Pair(1, -1), Pair(0, -1), Pair(-1, -1)),
        listOf(Pair(1, 1), Pair(0, 1), Pair(-1, 1)),
        listOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1)),
        listOf(Pair(1, -1), Pair(1, 0), Pair(1, 1)),
    )

    var out1 = 0
    val out2: Int
    var round = 0

    while (true) {
        round += 1
        val nextPositions = mutableMapOf<Pair<Int, Int>, MutableList<Int>>().withDefault { mutableListOf() }

        for ((elfIndex, elf) in elves.withIndex()) {
            val foundAnyElf = dirs.any { it.any { offset -> (elf + offset) in elves } }
            if (foundAnyElf) {
                var nextPos = elf
                for (i in 0 until 4) {
                    val offsets = dirs[(round - 1 + i) % 4]
                    val foundElf = offsets.any { (elf + it) in elves }
                    if (!foundElf) {
                        nextPos = elf + offsets[1]
                        break
                    }
                }

                if (nextPos !in nextPositions) nextPositions[nextPos] = mutableListOf()
                nextPositions[nextPos]!!.add(elfIndex)
            }
        }

        if (nextPositions.isEmpty()) {
            out2 = round
            break
        }

        for (pos in nextPositions.keys) {
            val elfIndices = nextPositions[pos]!!
            if (elfIndices.size == 1) {
                elves[elfIndices[0]] = pos
            }
        }

        if (round == 10) {
            val xs = elves.map { it.first }
            val ys = elves.map { it.second }

            val xl = xs.maxOf { it } - xs.minOf { it } + 1
            val yl = ys.maxOf { it } - ys.minOf { it } + 1

            out1 = xl * yl - elves.size
        }
    }

    // visualize end
    val xs = elves.map { it.first }
    val ys = elves.map { it.second }
    for (y in (ys.minOf { it }..ys.maxOf { it })) {
        for (x in (xs.minOf { it }..xs.maxOf { it })) {
            if (Pair(x, y) !in elves) print('.')
            else print('#')
        }
        println()
    }
    println()

    println(out1)
    println(out2)

    assert(out1 == 4052) { "First answer $out1 is wrong." }
    assert(out2 == 978) { "Second answer $out2 is wrong." }
}