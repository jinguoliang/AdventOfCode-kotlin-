package adventofcode

import java.io.File

/**
 * Created by jinux on 17-6-7.
 */

val regular = "\\((\\d+)x(\\d+)\\)".toRegex()

fun day9() {
    var s = File("data/day9").readLines()[0]

    println(parse(s))
}

fun parse(s: String):Long {
    var s = s
    var count = 0L;
    while (!s.isEmpty()) {
        val marker = parseMarker(s)

        if (marker == null) {
            count++
            s = s.substring(1)
        } else {
            s = s.substring(marker.getLength())
            count += parse(s.substring(0, marker.markLength)) * marker.markRepeat
            s = s.substring(marker.markLength)
        }
    }
    return count
}

private operator fun  String.times(markRepeat: Int): String {
    var result = StringBuilder()
    for (i in 1..markRepeat) {
        result.append(this)
    }
    return result.toString()
}

fun  parseMarker(s: String): Marker? {
    val find = regular.find(s)
    if (find != null){
        val group = find.groupValues;
        return Marker(group[0], group[1].toInt(), group[2].toInt())

    } else  {
        return null
    }
}

data class Marker(val markerString: String, val markLength: Int, val markRepeat: Int) {
    fun getLength(): Int {
        return markerString.length
    }
}