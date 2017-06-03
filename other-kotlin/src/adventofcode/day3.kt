package adventofcode

import java.io.File

/**
 * Created by jinux on 17-6-1.
 */

var count = 0

fun day3() {


    val f = File("data/day3")

    val data = arrayOf(ArrayList<Int>(),
            ArrayList<Int>(),
            ArrayList<Int>());

    f.forEachLine { line ->
        val edges = line.trim().split(" ").filter { !it.isBlank() }.map { it.trim().toInt() }
        val (a, b, c) = edges
        data[0].add(a)
        data[1].add(b)
        data[2].add(c)

        if (isAnagle(a, b, c)) {
            count++
        }
    }
    println(count)

    count = 0

    countAngle(countAngle(countAngle(data[0]), data[1]), data[2])
    println(count)
}

fun isAnagle(a: Int, b: Int, c: Int): Boolean {
    return a + b > c && a + c > b && b + c > a
}

fun  countAngle(data1: List<Int>): List<Int> {
    if (data1.size < 3) {
        return data1
     }

    val (a, b, c) = data1
    if (isAnagle(a, b, c)) {
        count++
    }

    val rest = data1.subList(3, data1.size)
    return countAngle(rest)
}

fun  countAngle(data1: List<Int>, data2: List<Int>): List<Int> {
    return countAngle(data1 + data2)
}
