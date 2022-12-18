import java.util.LinkedList
import java.util.Queue

private data class Cube(val x: Int, val y: Int, val z: Int) {
    fun getNeighbours() = listOf(
        this + Cube(0, 0, 1), this + Cube(0, 0, -1),
        this + Cube(0, 1, 0), this + Cube(0, -1, 0),
        this + Cube(1, 0, 0), this + Cube(-1, 0, 0)
    )

    operator fun plus(o: Cube) = Cube(x + o.x, y + o.y, z + o.z)
}

// operator overloading fun
private operator fun List<List<MutableList<Boolean>>>.get(cube: Cube): Boolean {
    return this[cube.z][cube.y][cube.x]
}

private operator fun List<List<MutableList<Boolean>>>.set(cube: Cube, boolean: Boolean) {
    this[cube.z][cube.y][cube.x] = boolean
}

private operator fun List<List<MutableList<Boolean>>>.contains(cube: Cube): Boolean {
    return (cube.x in this[0][0].indices && cube.y in this[0].indices && cube.z in this.indices)
}

private fun getSurfaceArea(
    droplet: List<List<MutableList<Boolean>>>
): Int {
    var area = 0
    for (z in 1 until 29) for (y in 1 until 29) for (x in 1 until 29) {
        val cube = Cube(x, y, z)
        if (droplet[cube])
            for (neigh in cube.getNeighbours())
                if (!droplet[neigh])
                    area++
    }
    return area
}

fun main() {
    val droplet = List(30) { List(30) { MutableList(30) { false } } }

    generateSequence { readlnOrNull() }
        .map { it.split(",") }
        .map { it.map { num -> num.toInt() } }
        .forEach { droplet[it[2] + 1][it[1] + 1][it[0] + 1] = true }

    val enclosedDroplet = List(30) { List(30) { MutableList(30) { true } } }
    val q: Queue<Cube> = LinkedList()
    q.add(Cube(0, 0, 0))

    while (!q.isEmpty()) {
        val cube = q.poll()

        if (cube !in enclosedDroplet) continue
        if (!enclosedDroplet[cube] || droplet[cube]) continue
        enclosedDroplet[cube] = false

        cube.getNeighbours().forEach { q.add(it) }
    }

    val out1 = getSurfaceArea(droplet)
    val out2 = getSurfaceArea(enclosedDroplet)

    println(out1)
    println(out2)

    assert(out1 == 3346) { "First answer $out1 is wrong." }
    assert(out2 == 1980) { "Second answer $out2 is wrong." }
}
