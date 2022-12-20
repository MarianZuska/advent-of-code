fun decrypt(numbers: MutableList<Pair<Int, Long>>, rounds: Int): Long {
    repeat(rounds) {
        for (i in numbers.indices) {
            val index = numbers.indexOfFirst { it.first == i }
            val elem = numbers.removeAt(index)

            var pos = (elem.second + index) % (numbers.size)
            if (pos < 0) pos += numbers.size

            val goalPosition = (pos)

            numbers.add(goalPosition.toInt(), elem)
        }
    }

    val indexOfZero = numbers.indexOfFirst { it.second == 0L }
    return listOf(1000, 2000, 3000).sumOf { numbers[(indexOfZero + it) % numbers.size].second }
}

fun main() {
    val numbers = generateSequence { readlnOrNull()?.toLongOrNull() }
        .mapIndexed { i, num -> Pair(i, num) }
        .toMutableList()

    val numbers2 = numbers
        .map { (i, num) -> Pair(i, num * 811589153L) }
        .toMutableList()

    val out1 = decrypt(numbers, 1)
    val out2 = decrypt(numbers2, 10)

    println(out1)
    println(out2)

    assert(out1 == 27726L) { "First answer $out1 is wrong." }
    assert(out2 == 4275451658004) { "Second answer $out2 is wrong." }

}
