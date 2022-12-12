import java.util.LinkedList
import java.util.Queue

// TODO: Refactor

fun main() {
    val input = generateSequence { readlnOrNull() }.toList()

    val start = input.map { it.indexOf('S') }.withIndex().first { it.value != -1 }
    val end = input.map { it.indexOf('E') }.withIndex().first { it.value != -1 }
    val endPair = Pair(end.value, end.index)
    val startPair = Pair(start.value, start.index)

    val terrain = input.map { it.replace('S', 'a').replace('E', 'z') }

    val possibleStarts = terrain
        .map { it.mapIndexed { index, elem -> index.takeIf { elem == 'a' } }.filterNotNull() }
        .withIndex()
    val heightMap = terrain.map { line ->
        line.map { square -> square - 'a' }
    }.toList()

    var minSteps = 1000000
    var stepsFromS = 0
    val q: Queue<Pair<Pair<Int, Int>, Int>> = LinkedList()
    for ((startY, startXs) in possibleStarts) {
        for (startX in startXs) {
            val curStart = Pair(startX, startY)
            q.clear()
            q.add(Pair(curStart, 0))

            val visited: MutableList<MutableList<Boolean>> =
                MutableList(terrain.size) { MutableList(terrain[0].length) { false } }

            while (q.isNotEmpty()) {
                val cur = q.poll()
                val pos = cur.first
                val steps = cur.second

                if (visited[pos.second][pos.first]) continue
                visited[pos.second][pos.first] = true
                if (pos == endPair) {
                    minSteps = minOf(minSteps, steps)
                    if (curStart == startPair) stepsFromS = steps
                    break
                }

                for (dx in (-1..1)) {
                    for (dy in (-1..1)) {
                        if ((dx == 0 || dy == 0) && !(dx == 0 && dy == 0)) {
                            val y = pos.second + dy
                            val x = pos.first + dx
                            if (x in heightMap[0].indices && y in heightMap.indices) {
                                if (heightMap[y][x] <= heightMap[pos.second][pos.first] + 1) {
                                    q.add(Pair(Pair(x, y), steps + 1))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    val out1 = stepsFromS
    val out2 = minSteps

    println(out1)
    println(out2)

    assert(out1 == 370) { "First answer is wrong." }
    assert(out2 == 363) { "Second answer is wrong." }
}