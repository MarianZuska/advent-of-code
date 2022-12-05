fun main() {
    val list = generateSequence(::readlnOrNull)
        .map { it.replace(",", "-").split("-")}
        .map { nums -> nums.map{ it.toInt()} }
        .toList()

    var score = 0
    var score2 = 0
    for((l, r, l2, r2) in list) {
        if ((l <= l2 && r >= r2) || (l >= l2 && r <= r2)) score += 1

        if (!(l..r intersect l2..r2).isEmpty()) score2 += 1
    }
    println(score)
    println(score2)
}