class Monkey(description: List<String>) {
    val items: MutableList<Long>
    var counter: Long = 0
    var inspect: (Long) -> Long
    var indexOfNext: (Long) -> Int

    companion object {
        var modulator: Long = 1

        private fun gcd(a: Long, b: Long): Long {
            return if (b == 0L) a else gcd(b, a % b)
        }

        fun lcm(a: Long, b: Long): Long {
            return a * (b / gcd(a, b))
        }
    }

    init {
        val (itemsStr, operation, test, ifTrue, ifFalse) = description
        val divisor = test.split(" ").last().toLong()
        val monkeyIfTrue = ifTrue.split(" ").last().toInt()
        val monkeyIfFalse = ifFalse.split(" ").last().toInt()

        modulator = lcm(modulator, divisor)

        items = itemsStr.split(", ").map { it.toLong() }.toMutableList()
        inspect = parsedOperation(operation)
        indexOfNext = { num -> if (num % divisor == 0L) monkeyIfTrue else monkeyIfFalse }
    }

    private fun parsedOperation(operation: String): (Long) -> Long {
        val (num1, operator, num2) = operation.split(" ").takeLast(3)

        val functions: Map<String, (Long, Long) -> Long> = mapOf(
            "*" to Long::times,
            "+" to Long::plus
        )

        return fun(num: Long): Long {
            counter++
            val n1: Long = if (num1 == "old") num else num1.toLong()
            val n2: Long = if (num2 == "old") num else num2.toLong()
            return functions[operator]!!.invoke(n1, n2) % modulator
        }
    }

    fun catch(item: Long) {
        items.add(item)
    }
}


fun main() {
    val monkeys = generateSequence { readlnOrNull() }
        .filter { it.isNotEmpty() && !it.startsWith("Monkey") }
        .map { it.split(": ")[1] }
        .chunked(5)
        .map { Monkey(it) }
        .toList()

    repeat(10000) {
        for (monkey in monkeys) {
            for (item in monkey.items) {
                val newItem = monkey.inspect(item)
                val nextMonkey = monkeys[monkey.indexOfNext(newItem)]
                nextMonkey.catch(newItem)
            }
            monkey.items.clear()
        }
    }

    val monkeyBusiness = monkeys
        .map { it.counter }
        .sortedDescending()
        .take(2)
        .reduce(Long::times)
    println(monkeyBusiness)

    assert(monkeyBusiness == 25590400731) { "Second answer is wrong." }
}
