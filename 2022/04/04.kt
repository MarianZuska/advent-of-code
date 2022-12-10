fun main() {
    val list = generateSequence(::readlnOrNull)
        .map { it.replace(",", "-").split("-") }
        .map { nums -> nums.map { it.toInt() } }
        .toList()

    var score1 = 0
    var score2 = 0
    for ((l, r, l2, r2) in list) {
        if ((l <= l2 && r >= r2) || (l >= l2 && r <= r2)) score1 += 1
        if ((l..r intersect l2..r2).isNotEmpty()) score2 += 1
    }

    println(score1)
    println(score2)

    assert(score1 == 490) { "First answer is wrong." }
    assert(score2 == 921) { "Second answer is wrong." }
}