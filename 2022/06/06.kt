fun solve(input: String, n: Int): Int {
    return n + input
        .windowed(n)
        .indexOfFirst { it.toSet().size == n }
}

fun main() {
    val list = readln()

    val out1 = solve(list, 4)
    val out2 = solve(list, 14)

    println(out1)
    println(out2)

    assert(out1 == 1275) { "First answer is wrong." }
    assert(out2 == 3605) { "Second answer is wrong." }
}