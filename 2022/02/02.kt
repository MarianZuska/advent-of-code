fun main() {
    // Conversion to List is needed because the sequence could not be consumed twice.
    val lines = generateSequence(::readlnOrNull)
        .map { it.split(" ") }
        .map { (a, b) -> intArrayOf(" ABC".indexOf(a), " XYZ".indexOf(b)) }
        .toList()

    // "+ 4" is needed because -1 % 3 = -1 as "%" implements the remainder and not the modulo operation.
    val score1 = lines.sumOf { (a, b) -> ((b - a + 4) % 3) * 3 + b }
    val score2 = lines.sumOf { (a, b) -> (b - 1) * 3 + (a + b) % 3 + 1 }

    println(score1)
    println(score2)

    assert(score1 == 15422) { "First answer is wrong." }
    assert(score2 == 15442) { "Second answer is wrong." }
}