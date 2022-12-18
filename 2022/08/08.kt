data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)
}

fun main() {
    val terrain = generateSequence { readlnOrNull() }.map { it.toList().map { num -> num.digitToInt() } }.toList()

    val sy = terrain.size
    val sx = terrain[0].size
    val visible = MutableList(sy) { MutableList(sx) { false } }

    fun checkFromRim(start: Point, dir: Point) {
        var height = -1
        var cur = start
        while (cur.x in 0 until sx && cur.y in 0 until sy) {
            if (terrain[cur.y][cur.x] > height) {
                visible[cur.y][cur.x] = true
                height = terrain[cur.y][cur.x]
            }
            cur += dir
        }
    }

    val dirs = listOf(Point(1, 0), Point(-1, 0), Point(0, 1), Point(0, -1))
    for (dir in dirs) {
        val (dx, dy) = dir
        val isPositive = dx + dy > 0
        val inverted = Point(dy, dx)

        var start = if (isPositive) Point(0, 0) else Point(sx - 1, sy - 1)

        while (start.x in 0 until sx && start.y in 0 until sy) {
            checkFromRim(start, dir)
            start += inverted
        }
    }

    fun checkFromTree(start: Point, dir: Point): Int {
        var out = 0
        val height = terrain[start.y][start.x]
        var cur = start + dir
        while (cur.x in 0 until sx && cur.y in 0 until sy) {
            out += 1
            if (terrain[cur.y][cur.x] >= height) break
            cur += dir
        }
        return out
    }

    var scenic = 0
    for (y in 0 until sy)
        for (x in 0 until sx)
            scenic = maxOf(scenic, dirs.map { dir -> checkFromTree(Point(x, y), dir) }.reduce { acc, i -> acc * i })

    val out1 = visible.sumOf { line -> line.count { it } }
    val out2 = scenic

    println(out1)
    println(out2)

    assert(out1 == 1705) { "First answer is wrong." }
    assert(out2 == 371200) { "Second answer is wrong." }
}