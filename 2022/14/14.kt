val cave = MutableList(1000) { MutableList(1000) { '.' } }

fun main() {
    var floor = 2
    generateSequence { readlnOrNull() }
        .map { it.split(" -> ") }
        .map { structure -> structure.map { coord -> coord.split(",").map { it.toInt() } } }
        .forEach { structure ->
            var curPoint = structure[0]
            for (nextPoint in structure) {
                val (x0, y0) = curPoint
                val (x1, y1) = nextPoint

                floor = maxOf(floor, y1 + 2)

                assert(x1 in (1..999))
                assert(y1 in (1..999))

                val xRange = if (x1 >= x0) (x0..x1) else (x1..x0)
                val yRange = if (y1 >= y0) (y0..y1) else (y1..y0)

                for (x in xRange) for (y in yRange) cave[y][x] = '#'

                curPoint = nextPoint
            }
        }

    var out1 = 0

    var count = 0
    while (cave[0][500] == '.') {
        val onFloor = !dropSand(floor)

        if (onFloor && out1 == 0) out1 = count
        count++
    }

    val out2 = count

    for (y in (0..floor)) {
        for (x in ((500 - floor)..(500 + floor))) {
            print(cave[y][x])
        }
        println()
    }

    println(out1)
    println(out2)

    assert(out1 == 1406) { "First answer is wrong." }
    assert(out2 == 20870) { "Second answer is wrong." }
}

fun dropSand(floor: Int): Boolean {
    var (x, y) = Pair(500, 0)
    while (y < floor - 1) {
        val nextX = listOf(x, x - 1, x + 1).firstOrNull { nextX -> cave[y + 1][nextX] == '.' } ?: break
        y++
        x = nextX
    }
    cave[y][x] = 'o'
    return y < floor - 1
}
