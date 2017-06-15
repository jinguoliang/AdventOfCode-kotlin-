package adventofcode

import java.io.File

/**
 * Created by jinux on 17-6-15.
 */

fun day6() {
    var isInit: Boolean = false;
    val MAX_COLUMN_INDEX = 7;

    val columns = ArrayList<ArrayList<Char>>()
    for (i in 0..MAX_COLUMN_INDEX) {
        columns.add(ArrayList<Char>())
    }

    // read file
    val f = File("data/day6")
    f.forEachLine { line ->
        for (i in 0..MAX_COLUMN_INDEX) {
            columns[i].add(line[i])
        }
    }
    println(columns)

    var result = columns.filter { !it.isEmpty() }.map {
        it.groupBy { it }.maxBy {
            it.value.size
        }!!.value[0]
    }
    println(result.joinToString(""))

    result = columns.filter { !it.isEmpty() }.map {
        it.groupBy { it }.minBy {
            it.value.size
        }!!.value[0]
    }
    println(result.joinToString(""))
}