fun main() {
    val monkeys = generateSequence { readlnOrNull() }.map { it.split(": ") }.associate { it[0] to it[1].split(" ") }

    fun solve(monkey: String, isSecond: Boolean = false, humanVal: Long = 0L): Long {
        val num = monkeys[monkey]!!

        if (isSecond) {
            if (monkey == "humn") return humanVal
            if (monkey == "root") return solve(num[0], true, humanVal) - solve(num[2], true, humanVal)
        }

        if (num.size == 1) return num[0].toLong()

        val operations: Map<String, (Long, Long) -> Long> = mapOf(
            "*" to Long::times,
            "+" to Long::plus,
            "-" to Long::minus,
            "/" to Long::div
        )

        return operations[num[1]]!!.invoke(solve(num[0], isSecond, humanVal), solve(num[2], isSecond, humanVal))
    }

    fun binarySearch(goRight: Boolean): Long {
        var hi = 1000_000_000_000_000
        var lo = 0L
        while (hi > lo + 1) {
            val mi = (hi + lo) / 2
            val x = solve("root", true, mi)
            if (x == 0L) return mi
            else if ((goRight && x > 0) || (!goRight && x < 0)) lo = mi
            else hi = mi
        }
        return -1L
    }

    val out1 = solve("root")
    // binary search in both directions
    var out2 = binarySearch(false)
    if (out2 == -1L) out2 = binarySearch(true)

    println(out1)
    println(out2)
    println(solve("root", true, 3592056845086L))
    assert(out1 == 194058098264286L) { "First answer $out1 is wrong." }
    assert(out2 == 3592056845087L) { "Second answer $out2 is wrong." }
}
