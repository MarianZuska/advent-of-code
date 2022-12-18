import kotlinx.coroutines.*
import kotlin.math.abs

const val size = 4000000
const val mid = 2000000

fun getEmptyPositionOrNull(ranges: List<Pair<Int, Int>>, y: Int): Pair<Int, Int>? {
    var x = 0
    for ((l, r) in ranges) {
        if (l > x) return Pair(x, y)
        x = maxOf(r + 1, x)
    }
    return if (x <= size) Pair(x, y)
    else null
}

suspend fun main() {
    val rows = MutableList(size + 1) { mutableListOf<Pair<Int, Int>>() }
    val beaconsAtMid = mutableSetOf<Int>()

    generateSequence { readlnOrNull() }
        .map { line -> line.replace(":", ",").split('=').drop(1).map { it.split(',')[0].toInt() } }
        .forEach {
            val (sx, sy, bx, by) = it
            val dist = abs(sx - bx) + abs(sy - by)

            //this parallelization only saves about 3 seconds and was done for learning purposes only
            (1..4000).map { i ->
                    val range = ((1000 * (i - 1))..(1000 * (i - 1) + 999))
                    CoroutineScope(Dispatchers.Default).async {
                        for (y in range) {
                            val width = dist - abs(y - sy)
                            if (width >= 0) rows[y].add(Pair(sx - width, sx + width))
                        }
                    }
                }
                .awaitAll()

            if(by == mid) beaconsAtMid.add(bx)
        }

    val sensorsAtMid = rows[mid].map{(it.first..it.second)}.flatten().toSet()
    val out1 = (sensorsAtMid subtract beaconsAtMid).count()

    val out2 = rows
        .map { it.sortedBy { it.first } }
        .withIndex()
        .firstNotNullOf { getEmptyPositionOrNull(it.value, it.index) }
        .run { size * 1L * this.first + this.second }

    println(out1)
    println(out2)

    assert(out1 == 4724228) { "First answer (${out1}) is wrong." }
    assert(out2 == 13622251246513L) { "Second answer (${out2}) is wrong." }
}
