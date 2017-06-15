package adventofcode

import java.io.File
import java.util.*

/**
 * Created by jinux on 17-6-3.
 */

class Room {
    val id: Int
    val checksum: String
    val name: List<Char>
    val oriName: String

    constructor(data: String) {
        println(data)
        val reg = "(.+)-(\\d+)\\[(\\w{5})\\]".toRegex();
        val (_, nameStr, idStr, checksumStr) =  reg.matchEntire(data)!!.groupValues
        oriName = nameStr
        name = nameStr.toList().filter { it != '-' }
        id = idStr.toInt()
        checksum = checksumStr
    }

    fun isReal(): Boolean {
        val groups = name.groupBy { it }
                .map { e -> Pair(e.key, e.value.size) }
                .sortedWith(kotlin.Comparator {
                    a, b -> if (a.second < b.second) 1 else if (a.second > b.second) -1 else if (a.first.toByte() < b.first.toInt()) -1 else 1
                })
        val result:List<Char> = groups.map { it.first }
        return result.joinToString(separator = "") { it.toString() }.startsWith(checksum);
    }

    val realName: String?
        get() {
            val countAlphat = 'z'.toByte() - 'a'.toByte() + 1
            val rotate = id % countAlphat
            println(rotate)
            println("ori " + oriName)
            val result = oriName.map { if (it.equals('-')) " " else ('a'.toByte() + (it.toInt() - 'a'.toByte() + rotate) % countAlphat).toChar() }.joinToString(separator = "")
            println("result " + result)
            return result
        }

}

fun day4() {
    val f = File("data/day4")

    var checkSum: Int = 0
    var result:Int = 0

    f.forEachLine { line ->
        val room = Room(line)
        if (room.isReal()) {
            println("isReal")
            checkSum += room.id
            if (room.realName!!.contains("north")) {
                result = room.id
            }
        }
    }

    println(result)
}