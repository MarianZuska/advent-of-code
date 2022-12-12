import java.util.LinkedList
import java.util.Queue

fun bfs(start: Pair<Int, Int>, end: Pair<Int, Int>, heightMap: List<List<Int>>): Int {
    val q: Queue<Pair<Pair<Int, Int>, Int>> = LinkedList()
    q.add(Pair(start, 0))

    val visited = MutableList(heightMap.size) { MutableList(heightMap[0].size) { false } }
    while (q.isNotEmpty()) {
        val cur = q.poll()
        val (x, y) = cur.first
        val steps = cur.second

        if (Pair(x, y) == end) return steps

        if (visited[y][x]) continue
        visited[y][x] = true

        val directions = listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
        for ((dx, dy) in directions) {
            val x2 = x + dx
            val y2 = y + dy
            if (x2 in heightMap[0].indices && y2 in heightMap.indices) {
                if (heightMap[y2][x2] <= heightMap[y][x] + 1) {
                    q.add(Pair(Pair(x2, y2), steps + 1))
                }
            }
        }
    }
    return 1000000
}

fun getIndicesOfFirst(char: Char, input: List<String>): Pair<Int, Int> {
    return input
        .map { it.indexOf(char) }
        .withIndex()
        .first { it.value != -1 }
        .run { Pair(this.value, this.index) }
}


fun main() {
    val input = generateSequence { readlnOrNull() }.toList()

    val startPair = getIndicesOfFirst('S', input)
    val endPair = getIndicesOfFirst('E', input)

    val terrain = input.map { it.replace('S', 'a').replace('E', 'z') }

    val possibleStarts = terrain
        .withIndex()
        .map { (y, row) ->
            row.indices
                .filter { i -> row[i] == 'a' }
                .map { Pair(it, y) }
        }
        .flatten()

    val heightMap = terrain.map { line -> line.map { square -> square - 'a' } }.toList()

    var out2 = 1000000
    var out1 = 0
    for (curStart in possibleStarts) {
        val steps = bfs(curStart, endPair, heightMap)

        out2 = minOf(out2, steps)
        if (curStart == startPair) out1 = steps
    }

    println(out1)
    println(out2)

    assert(out1 == 370) { "First answer is wrong." }
    assert(out2 == 363) { "Second answer is wrong." }
}
