fun main() {

    val input = generateSequence { readlnOrNull() }.map { it.trim().split(" ") }.toMutableList()
    input.add(0, listOf("pass"))
    input.add(0, listOf("pass"))

    val registerValues = input
        .map { if (it[0] == "addx") listOf(it, listOf("pass")) else listOf(it) }
        .flatten()
        .scan(1) { x, line -> if (line[0] == "addx") x + line[1].toInt() else x }

    val valuesAfterCycle = registerValues
        .withIndex()

    val valuesDuringCycle = registerValues
        .drop(1)
        .withIndex()

    // star 1
    val sum = valuesAfterCycle
        .filter { it.index % 40 == 20 }
        .sumOf { it.value * it.index }
    println(sum)

    //star 2
    val string = StringBuilder()
    for ((cycle, value) in valuesDuringCycle) {
        val pixelPosition = cycle % 40
        if (cycle > 0 && pixelPosition == 0) string.append("\n")

        if (pixelPosition in value - 1..value + 1) {
            string.append("#")
        } else {
            string.append(".")
        }
    }
    println(string)

    assert(sum == 15260) { "First answer is wrong." }
    assert(string.startsWith(
        "###...##..#..#.####..##..#....#..#..##..\n" +
        "#..#.#..#.#..#.#....#..#.#....#..#.#..#.\n" +
        "#..#.#....####.###..#....#....#..#.#....\n" +
        "###..#.##.#..#.#....#.##.#....#..#.#.##.\n" +
        "#....#..#.#..#.#....#..#.#....#..#.#..#.\n" +
        "#.....###.#..#.#.....###.####..##...###."
    )) { "Second answer is wrong." }

}