import java.util.*

data class Valve(val name: String, val rate: Int, val adj: List<String>)

data class State(
    val position: String = "AA",
    val time: Int = 1,
    val openValves: Int = 0,
    val visitedAll: Int = 0,
    val visitedSinceLastOpen: Int = 0,
    val flow: Int = 0
)

fun main() {
    val lines = generateSequence { readlnOrNull() }

    val valves: MutableMap<String, Valve> = mutableMapOf()
    val valveIndices: MutableMap<String, Int> = mutableMapOf()

    for ((i, line) in lines.withIndex()) {
        val (node, neighborString) = line.split(";")
        val neighbors = neighborString.split(", ").map { it.substring(it.length - 2) }
        val name = node.split(" ")[1]
        val rate = node.split("=")[1].toInt()

        valveIndices[name] = i
        valves[name] = Valve(name, rate, neighbors)
    }

    var maxScore = 0
    val q: Queue<State> = LinkedList()
    q.add(State())

    // TODO add monkey
    while (q.isNotEmpty()) {
        val (cur, time, open, visitedAll, visitedSinceLastOpen, flow) = q.poll()

        if (time == 30) maxScore = maxOf(maxScore, flow)
        if (time >= 30) continue

        valves[cur]!!.adj.forEach { neigh ->
            val ind = valveIndices[neigh]!!
            val bitMask = (1 shl ind)
            if (visitedSinceLastOpen and bitMask == 0) {
                val rate = valves[neigh]!!.rate
                if (rate > 0 && open and bitMask == 0 && visitedAll and bitMask == 0) {
                    val addFlow = rate * (30 - time - 1)
                    q.add(State(neigh, time + 1, open + bitMask, visitedAll + bitMask, 0, flow + addFlow))
                }
                q.add(State(neigh, time + 1, open, visitedAll + bitMask, visitedSinceLastOpen + bitMask, flow))
            }
        }
        println(time)
    }
    println(maxScore)
}