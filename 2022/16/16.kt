import java.util.*


data class Valve(val name: String, val rate: Int, val adj: List<String>)

data class State(
    val time: Int = 1,
    val eleTime: Int = 1,
    val position: String = "AA",
    val elePosition: String = "AA",
    val openValves: Long = 0L,
    val flow: Int = 0,
) : Comparable<State> {
    override fun compareTo(other: State) = compareValuesBy(this, other) { minOf(it.time, it.eleTime) }
}

fun main() {
    val lines = generateSequence { readlnOrNull() }

    val valves: MutableMap<String, Valve> = mutableMapOf()
    val valveIndices: MutableMap<String, Int> = mutableMapOf()
    val valveNames: MutableList<String> = mutableListOf()
    for ((i, line) in lines.withIndex()) {
        val (node, neighborString) = line.split(";")
        val neighbors = neighborString.split(", ").map { it.substring(it.length - 2) }
        val name = node.split(" ")[1]
        val rate = node.split("=")[1].toInt()

        valveIndices[name] = i
        valves[name] = Valve(name, rate, neighbors)
        valveNames.add(name)
    }

    fun getNonZeroNeighs(node: String): List<Pair<Int, String>> {
        val neighs = mutableListOf<Pair<Int, String>>()
        val q: Queue<Pair<Int, String>> = LinkedList()
        q.add(Pair(0, node))
        var visited = 0L
        while (q.isNotEmpty()) {
            val (dist, cur) = q.poll()
            val ind = (1L shl valveIndices[cur]!!)
            if(visited and ind != 0L) continue
            visited += ind

            val valve = valves[cur]!!
            if(cur != node && valve.rate > 0) neighs.add(Pair(dist, cur))
            for(neigh in valves[cur]!!.adj) {
                q.add(Pair(dist + 1, neigh))
            }
        }
        return neighs
    }

    val nonZeroNeighs = mutableMapOf<String, List<Pair<Int, String>>>()
    for(valve in valveNames) nonZeroNeighs[valve] = getNonZeroNeighs(valve)

    // note that using a simple queue is faster but does not allow for simple estimation of remaining time. ^^
    val q = PriorityQueue<State>()
    q.add(State())

    var maxScore = 0
    val maxTime = 26

    var count = 0
    while (q.isNotEmpty()) {
        val (myTime, eleTime, myPos, elePos, open, flow) = q.poll()

        maxScore = maxOf(maxScore, flow)
        val me = myTime <= eleTime
        val curPos = if (me) myPos else elePos
        val curTime = if (me) myTime else eleTime

        nonZeroNeighs[curPos]!!.forEach { (dist, newPos) ->
            val bitMask = (1L shl valveIndices[newPos]!!)
            val newTime = curTime + dist + 1
            if(open and bitMask == 0L && newTime <= maxTime) {
                val rate = valves[newPos]!!.rate
                val addFlow = rate * (maxTime-newTime+1)

                if(me) q.add(State(newTime, eleTime, newPos, elePos, open + bitMask, flow + addFlow))
                else q.add(State(myTime, newTime, myPos, newPos, open + bitMask, flow + addFlow))
            }
        }

        count++
        if(count % 1000000 == 0) println("${minOf(myTime,eleTime)}/$maxTime")
    }

    val out2 = maxScore
    println(maxScore)
    assert(out2 == 2591) { "Second answer (${out2}) is wrong." }
}
