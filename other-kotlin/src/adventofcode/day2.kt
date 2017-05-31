package adventofcode.day1

import java.io.File

data class Position(val x: Int, val y: Int) {
    operator fun plus(p: Position): Position {
        return Position(this.x + p.x, this.y + p.y)
    }
}


enum class Direction(val p: Position) {
    TOP(Position(0, 1)),
    LEFT(Position(-1, 0)),
    RIGHT(Position(1, 0)),
    BOTTOM(Position(0, -1))
}

class Range(val left: Position,
            val top: Position,
            val right: Position,
            val bottom: Position) {

    operator fun contains(pos: Position): Boolean {
        return (pos.x >= left.x && pos.x <= right.x && pos.y >= bottom.y && pos.y <= top.y)
    }


    val key = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
            arrayOf(7, 8, 9)
    )

    fun map(pos: Position): Int {
        return key[1 - pos.y][pos.x + 1]
    }
}

val rect =  Range1(Position(-1, 0),
        Position(0, 1),
        Position(1, 0),
        Position(0, -1))


class Range1(val left: Position,
            val top: Position,
            val right: Position,
            val bottom: Position) {

    operator fun contains(pos: Position): Boolean {
        if (pos.x == -2 && pos.y == 0) return true
        if (pos.x == 2 && pos.y == 0) return true
        if (pos.x == 0 && pos.y == -2) return true
        if (pos.x == 0 && pos.y == 2) return true
        return (pos.x >= left.x && pos.x <= right.x && pos.y >= bottom.y && pos.y <= top.y)
    }


    val key = arrayOf(
            arrayOf(0, 0, 1, 0, 0),
            arrayOf(0, 2, 3, 4, 0),
            arrayOf(5, 6, 7, 8, 9),
            arrayOf(0,'A', 'B', 'C', 0),
            arrayOf(0, 0, 'D', 0, 0)
    )

    fun map(pos: Position): Any {
        return key[2 - pos.y][pos.x + 2]
    }
}

fun d2part1() {
    var pos = Position(-2, 0)

    // read file
    val f = File("data/day2")
    f.forEachLine { line ->

//        println(line)

        line.forEach { instruction ->

            val direction: Position? = when (instruction) {
                'L' -> Direction.LEFT.p
                'U' -> Direction.TOP.p
                'R' -> Direction.RIGHT.p
                'D' -> Direction.BOTTOM.p
                else -> null
            }

            if (direction is Position) {
                val newPosition = pos + direction
                if (newPosition in rect) {
                    pos = newPosition
                }
            }
        }
        println("p = ${rect.map(pos)}")
    }
}

fun d2part2() {

}
