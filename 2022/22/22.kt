// note that this code is made to only work with my input
// also TODO: Refactor

data class CubeFace(
    val pos: Pair<Int, Int>,
    val up: Pair<Pair<Int, Int>, Int>,
    val right: Pair<Pair<Int, Int>, Int>,
    val down: Pair<Pair<Int, Int>, Int>,
    val left: Pair<Pair<Int, Int>, Int>,
)

private operator fun Pair<Int, Int>.plus(o: Pair<Int, Int>) = Pair(this.first + o.first, this.second + o.second)
private operator fun Pair<Int, Int>.minus(o: Pair<Int, Int>) = Pair(this.first - o.first, this.second - o.second)

val dirs = listOf(Pair(1, 0), Pair(0, 1), Pair(-1, 0), Pair(0, -1))

const val right = 0
const val down = 1
const val left = 2
const val up = 3

// op shows relative position to face above current face and whether the indices need to be flipped when crossing the edge
val myCube = mapOf(
    Pair(1, 0) to CubeFace(
        pos = Pair(1, 0),
        up = Pair(Pair(-1, 3), 0),
        right = Pair(dirs[right], 0),
        down = Pair(dirs[down], 0),
        left = Pair(Pair(-1, 2), 1)
    ),
    Pair(2, 0) to CubeFace(
        pos = Pair(2, 0),
        up = Pair(Pair(-2, 3), 0),
        right = Pair(Pair(-1, 2), 1),
        down = Pair(Pair(-1, 1), 0),
        left = Pair(dirs[left], 0)
    ),
    Pair(1, 1) to CubeFace(
        pos = Pair(1, 1),
        up = Pair(dirs[up], 0),
        right = Pair(Pair(1, -1), 0),
        down = Pair(dirs[down], 0),
        left = Pair(Pair(-1, 1), 0)
    ),
    Pair(1, 2) to CubeFace(
        pos = Pair(1, 2),
        up = Pair(dirs[up], 0),
        right = Pair(Pair(1, -2), 1),
        down = Pair(Pair(-1, 1), 0),
        left = Pair(dirs[left], 0)
    ),
    Pair(0, 2) to CubeFace(
        pos = Pair(0, 2),
        up = Pair(Pair(1, -1), 0),
        right = Pair(dirs[right], 0),
        down = Pair(dirs[down], 0),
        left = Pair(Pair(1, -2), 1)
    ),
    Pair(0, 3) to CubeFace(
        pos = Pair(0, 3),
        up = Pair(dirs[up], 0),
        right = Pair(Pair(1, -1), 0),
        down = Pair(Pair(2, -3), 0),
        left = Pair(Pair(1, -3), 0)
    )
)


fun main() {
    val (dieStr, movesStr) = generateSequence { readlnOrNull() }.joinToString("\n").split("\n\n")

    val die = dieStr.split("\n")
    val moves = Regex("""\d+|\D+""").findAll(movesStr).map { it.groupValues.first() }.toList()
    val dieWithPath = die.map { it.toMutableList() }

    var dir = 0
    var pos = Pair(die[0].indexOfFirst { it == '.' }, 0)

    fun Pair<Int, Int>.toCubeMap() = Pair(this.first / 50, this.second / 50)
    fun Pair<Int, Int>.isWalkable(): Boolean = die[this.second][this.first] == '.'
    fun Pair<Int, Int>.isOutSideMap(): Boolean =
        this.second !in die.indices || this.first !in die[this.second].indices || die[this.second][this.first] == ' '

    fun getPositionOnEdge(newPos: Pair<Int, Int>, cubeFace: CubeFace): Int {
        var index = when (dir) {
            right -> newPos.second % 50
            down -> newPos.first % 50
            left -> newPos.second % 50
            else -> newPos.first % 50
        }
        when (dir) {
            right -> if (cubeFace.right.second == 1) index = 49 - index
            down -> if (cubeFace.down.second == 1) index = 49 - index
            left -> if (cubeFace.left.second == 1) index = 49 - index
            else -> if (cubeFace.up.second == 1) index = 49 - index
        }

        return index
    }

    fun goForwards(steps: Int) {
        for (i in 0 until steps) {
            dieWithPath[pos.second][pos.first] = '/'

            val newPos = pos + dirs[dir]
            val facePos = pos.toCubeMap()
            val face = myCube[facePos]!!

            if (!newPos.isOutSideMap()) {
                if (newPos.isWalkable()) pos = newPos
                else break
            } else {
                val nextFacePos: Pair<Int, Int> = when (dir) {
                    right -> face.pos + face.right.first
                    down -> face.pos + face.down.first
                    left -> face.pos + face.left.first
                    else -> face.pos + face.up.first
                }

                val nextFace = myCube[nextFacePos]!!
                val positionOnEdge = getPositionOnEdge(newPos, face)

                when (facePos) {
                    nextFacePos + nextFace.up.first -> {
                        val nextPos = Pair(nextFacePos.first * 50 + positionOnEdge, nextFacePos.second * 50)
                        if (nextPos.isWalkable()) {
                            pos = nextPos
                            dir = down
                        } else break
                    }

                    nextFacePos + nextFace.right.first -> {
                        val nextPos = Pair(nextFacePos.first * 50 + 49, nextFacePos.second * 50 + positionOnEdge)
                        if (nextPos.isWalkable()) {
                            pos = nextPos
                            dir = left
                        } else break
                    }

                    nextFacePos + nextFace.down.first -> {
                        val nextPos = Pair(nextFacePos.first * 50 + positionOnEdge, nextFacePos.second * 50 + 49)
                        if (nextPos.isWalkable()) {
                            pos = nextPos
                            dir = up
                        } else break
                    }

                    nextFacePos + nextFace.left.first -> {
                        val nextPos = Pair(nextFacePos.first * 50, nextFacePos.second * 50 + positionOnEdge)
                        if (nextPos.isWalkable()) {
                            pos = nextPos
                            dir = right
                        } else break
                    }

                    else -> assert(false)
                }
            }
        }
    }

    for (move in moves) {
        when (move) {
            "R" -> dir = (dir + 1 + 4) % 4
            "L" -> dir = (dir - 1 + 4) % 4
            else -> goForwards(move.toInt())
        }
    }

    val row = pos.second + 1
    val col = pos.first + 1
    val facing = dir
    val out2 = 1000 * row + 4 * col + facing
    println(out2)

    assert(out2 == 197122) { "Second answer $out2 is wrong." }
}
