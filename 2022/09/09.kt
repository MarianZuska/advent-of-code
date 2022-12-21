import kotlin.math.abs

operator fun Pair<Int, Int>.plus(o: Pair<Int, Int>) = Pair(this.first + o.first, this.second + o.second)

fun main() {
    val dirs = mutableMapOf("U" to Pair(0, 1), "L" to Pair(-1, 0), "D" to Pair(0, -1), "R" to Pair(1, 0))

    val moves = generateSequence { readlnOrNull() }
        .map { it.split(" ") }
        .map { (dir, times) -> List(times.toInt()) { dirs[dir]!! } }
        .flatten()

    val visited1 = mutableSetOf(Pair(0, 0))
    val visited9 = mutableSetOf(Pair(0, 0))
    val rope = MutableList(10) { Pair(0, 0) }

    for (dir in moves) {
        rope[0] += dir
        for (i in (1 until 10)) {
            val dx = rope[i].first - rope[i - 1].first
            val dy = rope[i].second - rope[i - 1].second
            if (abs(dx) >= 2 || abs(dy) >= 2) {
                rope[i] = rope[i - 1]
                if (abs(dx) >= 2) rope[i] += Pair(dx / abs(dx), 0)
                if (abs(dy) >= 2) rope[i] += Pair(0, dy / abs(dy))
            }
        }
        visited1.add(rope[1])
        visited9.add(rope[9])
    }

    val out1 = visited1.size
    val out2 = visited9.size

    println(out1)
    println(out2)

    assert(out1 == 6212) { "First answer $out1 is wrong." }
    assert(out2 == 2522) { "Second answer $out2 is wrong." }
}