fun solve(input:String, n: Int) = input.windowed(n).indexOfFirst { it.toSet().size == n } + n

fun main() {
    val list = readln()

    println(solve(list, 4))
    println(solve(list, 14))
}