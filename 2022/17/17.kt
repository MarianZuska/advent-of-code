fun main() {
    val jetPattern = readln().map { if (it == '<') -1 else 1 }

    val rocks = listOf(
        listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0)),
        listOf(Pair(1, 0), Pair(0, 1), Pair(1, 1), Pair(2, 1), Pair(1, 2)),
        listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(2, 1), Pair(2, 2)),
        listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3)),
        listOf(Pair(0, 0), Pair(1, 0), Pair(0, 1), Pair(1, 1)),
    )

    var height = 0
    var wind = 0

    val cols = MutableList(1000000) { MutableList(7) { false } }
    val colsHighest = MutableList(7) { 0 }
    (0..6).forEach { cols[0][it] = true }

    // stores information about the tower's state at specific points to find patterns
    val towerStates = MutableList(jetPattern.size) { mutableListOf<Triple<Int,Int,Int>>() }

    var turnsSkipped = false
    var skippedTurns = 0L
    var skippedHeight = 0L

    var heightAfter2022 = 0

    for (i in (0 until (1000000))) {
        if(i == 2022) heightAfter2022 = height
        if(i + skippedTurns == 1000000000000L) break

        val rock = rocks[i % rocks.size]
        var (x, y) = Pair(2, height + 4)

        while (true) {
            // save state and compare with previous states to find patterns
            if(!turnsSkipped) {
                val windIndex = wind % jetPattern.size
                val rockIndex = i % rock.size
                val repeatedOnce = i >= jetPattern.size

                // only check if wind has repeated once and the first rock was used when the first wind was applied
                if (repeatedOnce && windIndex % 5 == rockIndex) {
                    val maxCol = colsHighest.maxOrNull()!!
                    val minCol = colsHighest.minOrNull()!!
                    val state = maxCol - minCol

                    val numStates = towerStates[windIndex].size

                    // check if state repeated 5 times in the last 25 states
                    if (i >= 2022 && numStates > 25) {
                        val repeated = 5 - towerStates[windIndex].takeLast(5).indexOfFirst { it.first == state }
                        if(repeated < 6) {
                            turnsSkipped = (2..5).all {towerStates[windIndex][numStates-repeated*it].first == state}
                        }
                        if (turnsSkipped) {
                            // pattern found! Skip as many turns as possible
                            val (_, iPrev, maxColPrev) = towerStates[windIndex][numStates-repeated]

                            val heightDiff = maxCol - maxColPrev
                            val periodLength = i - iPrev
                            val skipAmount = (1000000000000L - i) / periodLength

                            skippedHeight = heightDiff * skipAmount
                            skippedTurns = skipAmount * periodLength
                        }
                    }
                    towerStates[windIndex].add(Triple(state, i, maxCol))
                }
            }

            // simulate turn

            // apply wind
            val newX = x + jetPattern[wind++ % jetPattern.size]
            val blocked = rock.any { (dx, dy) -> (newX + dx) !in (0..6) || cols[y + dy][newX + dx] }
            if (!blocked) x = newX

            // check if rock will stop
            val stop = rock.any { (dx, dy) -> cols[y + dy - 1][x + dx] }
            if (stop) {
                // change tower
                rocks[i % rocks.size]
                    .forEach {
                        (dx, dy) -> cols[y + dy][x + dx] = true
                        height = maxOf(height, y+dy)
                        colsHighest[x+dx] =  maxOf(colsHighest[x+dx], y+dy)
                    }
                break
            }
            y--
        }
    }

    val out1 = heightAfter2022
    val out2 = height + skippedHeight

    println(out1)
    println(out2)

    assert(out1 == 3065) { "First answer $out1 is wrong."}
    assert(out2 == 1562536022966) { "Second answer $out2 is wrong."}
}