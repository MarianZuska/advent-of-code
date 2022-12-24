import java.util.LinkedList
import java.util.Queue

private data class Blizzard(var pos: Pair<Int, Int>, val c: Char) {
    val dir = directions[">v<^".indexOf(c)]
}

private fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
fun lcm(a: Int, b: Int): Int = a * (b / gcd(a, b))

private operator fun Pair<Int, Int>.plus(o: Pair<Int, Int>) = Pair(this.first + o.first, this.second + o.second)
private operator fun Pair<Int, Int>.times(o: Int) = Pair(this.first * o, this.second * o)

private val directions = listOf(Pair(1, 0), Pair(0, 1), Pair(-1, 0), Pair(0, -1), Pair(0, 0))

fun main() {
    val terrain = generateSequence { readlnOrNull() }.toList()
    val blizzards = terrain.mapIndexed { y, L -> L.mapIndexed { x, t -> Pair(Pair(x - 1, y - 1), t) } }.flatten()
        .filter { it.second in ">v<^" }.map { Blizzard(it.first, it.second) }.toMutableList()

    val height = terrain.size - 2
    val width = terrain[0].length - 2

    val simSize = lcm(width, height)
    val terrainsWithTornado = List(simSize) { List(height) { MutableList(width) { false } } }
    for (i in 0 until simSize) {
        blizzards.forEach {
            val curPos = (it.pos + it.dir * i)
            terrainsWithTornado[i][(curPos.second + height * 100) % height][(curPos.first + width * 100) % width] = true
        }
    }

    val start = Pair(0, -1)
    val end = Pair(width - 1, height)

    fun Pair<Int, Int>.isInside(): Boolean =
        this == start || this == end || (this.first in 0 until width && this.second in 0 until height)

    fun bfs(startPos: Pair<Int, Int>, endPos: Pair<Int, Int>, startTime: Int): Int {
        val visitedAtTime = List(simSize) { List(height + 2) { MutableList(width + 2) { false } } }
        val q: Queue<Pair<Int, Pair<Int, Int>>> = LinkedList()
        q.add(Pair(startTime, startPos))

        while (q.isNotEmpty()) {
            val (time, pos) = q.poll()

            if (pos == endPos) return time
            if (visitedAtTime[time % simSize][pos.second + 1][pos.first + 1]) continue
            visitedAtTime[time % simSize][pos.second + 1][pos.first + 1] = true

            for (d in directions) {
                val next = pos + d
                if (next.isInside() && (next == startPos || next == endPos || !terrainsWithTornado[(time + 1) % simSize][next.second][next.first])) {
                    q.add(Pair(time + 1, next))
                }
            }
        }
        return 1000000000
    }

    val out1 = bfs(start, end, 0)
    val out2 = bfs(start, end, bfs(end, start, bfs(start, end, 0)))

    println(out1)
    println(out2)

    assert(out1 == 242) { "First answer $out1 is wrong." }
    assert(out2 == 720) { "Second answer $out2 is wrong." }
}
