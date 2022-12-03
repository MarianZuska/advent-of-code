fun main() {
    val list = generateSequence(::readlnOrNull)
        .map { it.split(" ") }
        .map { (a,b) -> intArrayOf(" ABC".indexOf(a), " XYZ".indexOf(b)) }
        .toList()

    // needs + 4 because -1 % 3 = -1
    val score1 = list
        .map { (a, b) -> ((b-a+4)%3) * 3 + b }
        .sum()

    val score2 = list
        .map { (a, b) -> (b-1)*3 + (a + b)%3+1 }
        .sum()

    println(score1)
    println(score2)

}