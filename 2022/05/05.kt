fun main() {
    val (tower, moves) = generateSequence(::readlnOrNull).joinToString("\n").split("\n\n")

    val stacks = MutableList<MutableList<Char>>(9) { mutableListOf() }
    tower.split("\n").reversed().drop(1)
        .forEach { for(i in 1..it.length step 4) if(it[i] != ' ') stacks[i/4].add(it[i]) }

    val nums = moves
        .split("\n")
        .map {it.filter {!it.isLetter()}}
        .map { it.split("  ")}
        .map { move -> move.map {it.trim().toInt()}}

    nums.forEach {
        val (n,start,end) = it

        val startStack = stacks[start-1]
        val endStack = stacks[end-1]
        val len = startStack.size
        //use this line instead of next for star 1
        //endStack.addAll(startStack.subList(len-n,len).reversed())
        endStack.addAll(startStack.subList(len-n,len))
        stacks[start-1] = startStack.dropLast(n).toMutableList()
    }

    println(stacks.filter { it.isNotEmpty() }.map{it.last()}.joinToString(""))
}