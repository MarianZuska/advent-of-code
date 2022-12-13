data class Packet(
    val packets: MutableList<Packet> = mutableListOf(),
    var value: Int? = null,
    var parent: Packet? = null
)

fun main() {
    val packets = generateSequence { readlnOrNull() }
        .filter { it.isNotEmpty() }
        .map { it.replace("[", "[,").replace("]", ",]").split(',').filter { elem -> elem != "" } }
        .map(::parsePacket)
        .toList()

    val out1 = packets
        .asSequence()
        .zipWithNext()
        .filterIndexed { index, _ -> index % 2 == 0 }
        .map(::compare)
        .withIndex()
        .filter { it.value > 0 }
        .sumOf { it.index + 1 }

    val x1 = parsePacket(listOf("[", "[", "2", "]", "]"))
    val x2 = parsePacket(listOf("[", "[", "6", "]", "]"))

    val ind1 = packets.map { compare(Pair(it, x1)) }.count { it > 0 } + 1
    val ind2 = packets.map { compare(Pair(it, x2)) }.count { it > 0 } + 2
    val out2 = ind1 * ind2

    println(out1)
    println(out2)

    assert(out1 == 5013) { "First answer is wrong." }
    assert(out2 == 25038) { "Second answer is wrong." }
}

fun compare(packets: Pair<Packet, Packet>): Int {
    val (p1, p2) = packets

    // one completely empty
    if (p1.packets.isEmpty() && p1.value == null && p2.packets.isEmpty() && p2.value == null) return 0
    if (p1.packets.isEmpty() && p1.value == null) return 1
    if (p2.packets.isEmpty() && p2.value == null) return -1

    // both numbers
    if (p1.value != null && p2.value != null) {
        return if (p1.value!! > p2.value!!) -1
        else if (p1.value!! < p2.value!!) 1
        else 0
    }

    // convert one to list if necessary
    val listPacket1 = p1.packets.ifEmpty { mutableListOf(p1) }
    val listPacket2 = p2.packets.ifEmpty { mutableListOf(p2) }

    // return based on diff values or size difference
    val sizeDiff = (listPacket2.size - listPacket1.size).coerceIn(-1..1)

    return listPacket1
        .zip(listPacket2)
        .map(::compare)
        .firstOrNull { it != 0 } ?: sizeDiff
}

fun parsePacket(packetStr: List<String>): Packet {
    var cur = Packet()
    for (elem in packetStr) {
        when (elem) {
            "[" -> {
                val newPacket = Packet(mutableListOf(), null, cur)
                cur.packets.add(newPacket)
                cur = newPacket
            }
            "]" -> cur = cur.parent!!
            else -> cur.packets.add(Packet(mutableListOf(), elem.toInt()))
        }
    }
    assert(cur.parent == null)
    return cur.packets[0]
}
