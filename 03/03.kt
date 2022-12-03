fun main() {
    val list = generateSequence(::readlnOrNull).toList()

    val out = list
        .map { it.chunked(it.length / 2) }
        .map { it.map { it.toSet() } }
        .map { x -> x[0] intersect x[1] }
        .sumOf { (('a'..'z') + ('A'..'Z')).toList().indexOf(it.first())+1 }
    
    val out2 = list
        .chunked(3)
        .map { l -> l.map { it.toSet() } }
        .map { it.reduce {s1, s2 -> s1 intersect s2 }}
        .sumOf { (('a'..'z') + ('A'..'Z')).toList().indexOf(it.first())+1 }

    println(out)
    println(out2)
}