data class Blueprint(val numbers: List<Int>) {
    val oreCost: Int = numbers[0]
    val clayCost: Int = numbers[1]
    val obsidianCost: Pair<Int, Int> = Pair(numbers[2], numbers[3])
    val geodeCost: Pair<Int, Int> = Pair(numbers[4], numbers[5])
    val maxOresCost = maxOf(oreCost, clayCost, obsidianCost.first, geodeCost.first)

    var curBest = 0
    var maxTime = 24
}

fun solve(blueprint: Blueprint, time: Int, robots: MutableList<Int>, ores: MutableList<Int>): Int {
    val maxTime = blueprint.maxTime
    if(time > maxTime) {
        blueprint.curBest = maxOf(blueprint.curBest, ores[3])
        return ores[3]
    }

    val canProduceOre = ores[0] >= blueprint.oreCost
    val canProduceClay = ores[0] >= blueprint.clayCost
    val canProduceObsidian = ores[0] >= blueprint.obsidianCost.first && ores[1] >= blueprint.obsidianCost.second
    val canProduceGeode = ores[0] >= blueprint.geodeCost.first && ores[2] >= blueprint.geodeCost.second

    val practicalBest = ores[3] + (maxTime+1-time) * robots[3]
    blueprint.curBest = maxOf(blueprint.curBest, practicalBest)

    val add = if(canProduceGeode) (maxTime+1-time)*(maxTime+2-time)/2 else (maxTime-time)*(maxTime+1-time)/2
    val theoreticalBest = practicalBest + add
    if(theoreticalBest <= blueprint.curBest) return practicalBest

    for(i in robots.indices) ores[i] += robots[i]

    var maxGeodes = 0

    if(canProduceGeode) {
        val robots2 = robots.toMutableList()
        val ores2 = ores.toMutableList()
        robots2[3] += 1
        ores2[0] -= blueprint.geodeCost.first
        ores2[2] -= blueprint.geodeCost.second
        maxGeodes = maxOf(maxGeodes, solve(blueprint, time+1, robots2, ores2))
    }
    if(canProduceObsidian) {
        val robots2 = robots.toMutableList()
        val ores2 = ores.toMutableList()
        robots2[2] += 1
        ores2[0] -= blueprint.obsidianCost.first
        ores2[1] -= blueprint.obsidianCost.second
        maxGeodes = maxOf(maxGeodes, solve(blueprint, time+1, robots2, ores2))
    }
    if(canProduceClay) {
        val robots2 = robots.toMutableList()
        val ores2 = ores.toMutableList()
        robots2[1] += 1
        ores2[0] -= blueprint.clayCost
        maxGeodes = maxOf(maxGeodes, solve(blueprint, time+1, robots2, ores2))
    }
    if(canProduceOre && robots[0] < blueprint.maxOresCost) {
        val robots2 = robots.toMutableList()
        val ores2 = ores.toMutableList()
        robots2[0] += 1
        ores2[0] -= blueprint.oreCost
        maxGeodes = maxOf(maxGeodes, solve(blueprint, time+1, robots2, ores2))
    }

    val tryWait = !canProduceGeode &&
            (!canProduceObsidian || robots[2] > 0)

    if(tryWait) {
        val robots2 = robots.toMutableList()
        val ores2 = ores.toMutableList()
        maxGeodes = maxOf(maxGeodes, solve(blueprint, time + 1, robots2, ores2))
    }

    return maxGeodes
}

fun main() {
    val blueprints = generateSequence { readlnOrNull() }
        .map { it.split(" ").map { it.toIntOrNull() }.filterNotNull() }
        .map{Blueprint(it)}
        .toList()


    var out1 = 0
    for((i, blueprint) in blueprints.withIndex()) {
        val cur = solve(blueprint, 1, mutableListOf(1,0,0,0), mutableListOf(0,0,0,0))
        out1 += (i+1) * cur
        println("${i+1}/30")
    }

    var out2 = 1
    for((i, blueprint) in blueprints.take(3).withIndex()) {
        blueprint.maxTime = 32
        blueprint.curBest = 0
        val cur = solve(blueprint, 1, mutableListOf(1,0,0,0), mutableListOf(0,0,0,0))
        out2 *= cur
        println("${i+1}/3")
    }

    println(out1)
    println(out2)

    assert(out1 == 817) { "First answer $out1 is wrong." }
    assert(out2 == 4216) { "Second answer $out2 is wrong." }
}
