package `2022`.`09`

fun main() {
    val visited = mutableSetOf<Int>()

    val points = Array(10) { IntArray(2) }

    val dir = mapOf(
            "R" to listOf(1, 0),
            "L" to listOf(-1, 0),
            "U" to listOf(0, -1),
            "D" to listOf(0, 1),
    )
    val moves = generateSequence(::readlnOrNull).map { it.split(" ") }.map { it -> List(it[1].toInt()) { _ -> dir[it[0]] } }.flatten().filterNotNull()

    for ((x, y) in moves) {
        points[0][0] += x
        points[0][1] += y
        for (i in (0..9)) {
            points
        }
    }


}

