import kotlin.math.*

fun main() {
    val out1 = generateSequence { readlnOrNull() }
        .map { snafu -> snafu.map { "=-012".indexOf(it) - 2 } }
        .sumOf(::sToD)
        .let(::dToS)

    println(out1)

    assert(out1 == "2=020-===0-1===2=020") { "Answer $out1 is wrong." }
}

fun pow5(x: Int): Long = 5.0.pow(x).toLong()
fun log5(x: Long): Int = log(x.toDouble(), 5.0).toInt()

fun sToD(snafu: List<Int>): Long = snafu.indices.reversed().map(::pow5).zip(snafu).sumOf { it.first * it.second }

// note that this doesn't work if a power greater than the actual decimal number  is needed (e.g. 20 -> 1-0 = 25*1 - 5)
fun dToS(decimal: Long): String = (log5(decimal) downTo 0)
    .map(::pow5)
    .fold(Pair(0L, "")) { (num, name), pow ->
        (-2..2).minByOrNull { abs(num + it * pow - decimal) }!!.let { Pair(num + pow * it, name + "=-012"[it + 2]) }
    }.second
