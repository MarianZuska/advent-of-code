import java.util.*

data class Valve(val name: String, val rate: Int, val adj: List<String>)

fun main() {
    val lines = generateSequence { readlnOrNull() }

    val valves: MutableMap<String, Valve> = mutableMapOf()

    val valveIndices: MutableMap<String, Int> = mutableMapOf()
    val valveNames: MutableList<String> = mutableListOf()

    for(line in lines) {
        val (node, neighborString) = line.split(";")
        val neighbors = neighborString.split(", ").map { it.substring(it.length-2) }
        val name = node.split(" ")[1]
        val rate = node.split("=")[1].toInt()

        valveNames.add(name)
        valveIndices[name] = valveNames.size-1
        valves[name] = Valve(name, rate, neighbors)
    }

    var maxScore = 0
    val q: Queue<Pair<Triple<String, Int, Int>, Pair<Int, Int>>> = LinkedList()
    q.add(Pair(Triple("AA", 0, 0), Pair(1,0)))
    while(q.isNotEmpty()) {
        val (valveStrs, nums) = q.poll()
        var (cur, open, visitedSinceLastOpen) = valveStrs
        val (time, score) = nums

        if(time == 30) maxScore = maxOf(maxScore, score)
        if(time >= 30) continue

        valves[cur]!!.adj.forEach{ neighStr ->
                val ind = valveIndices[neighStr]!!
                val bitMask = (1 shl ind)
                if(visitedSinceLastOpen and bitMask == 0) {
                    val neigh = valves[neighStr]!!
                    if (neigh.rate > 0 && open and bitMask == 0) {
                        val addScore = neigh.rate * (30 - time - 1)
                        q.add(Pair(Triple(neighStr, open + bitMask, 0), Pair(time + 2, score + addScore)))
                    }
                    visitedSinceLastOpen += bitMask
                    q.add(Pair(Triple(neighStr, open, visitedSinceLastOpen), Pair(time + 1, score)))
                }
        }
    }
    println(maxScore)
}