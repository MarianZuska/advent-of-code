fun getFirstLetterIndex(letter: Char): Int {
    val letters = (('a'..'z') + ('A'..'Z')).toList()
    return letters.indexOf(letter) + 1
}

fun main() {
    val lines = generateSequence(::readlnOrNull).toList()

    val out1 = lines
        .asSequence()
        .map { it.chunked(it.length / 2) }
        .map { compartment -> compartment.map { it.toSet() } }
        .map { it[0] intersect it[1] }
        .map { it.first()}
        .sumOf(::getFirstLetterIndex)

    val out2 = lines
        .asSequence()
        .chunked(3)
        .map { l -> l.map { it.toSet() } }
        .map { it.reduce { s1, s2 -> s1 intersect s2 } }
        .map { it.first()}
        .sumOf(::getFirstLetterIndex)

    println(out1)
    println(out2)

    assert(out1 == 7553) { "First answer is wrong." }
    assert(out2 == 2758) { "Second answer is wrong." }
}