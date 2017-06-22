package adventofcode

import sun.misc.Queue
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by jinux on 17-6-5.
 */

/**
 * 入口
 */
fun day8() {
    val screen = Screen(50, 6)

    screen.display()
    File("data/day8").forEachLine { cmd ->
        val cmdd = Command(cmd)
        if (cmdd.action == Command.Cmd.RECT) {
            screen.rect(cmdd.argument!!.first,cmdd.argument!!.second)
        } else if (cmdd.action == Command.Cmd.ROTATE_ROW) {
            screen.rotateRow(cmdd.argument!!.first,cmdd.argument!!.second)
        } else if (cmdd.action == Command.Cmd.ROTATE_COLUMN) {
            screen.rotateColumn(cmdd.argument!!.first,cmdd.argument!!.second)
        }
        screen.display()
    }
    println("sum = " + screen.count())
}

data class Command(var cmd: String) {
    var action: Cmd?
    var argument: Pair<Int, Int>?


    init {
        var rectRegex = "rect (\\d+)x(\\d+)".toRegex()
        var rotateRegex = "rotate (\\w+) [x|y]=(\\d+) by (\\d+)".toRegex()

        if (rectRegex.matches(cmd)) {
            val (_, arg1, arg2) = rectRegex.matchEntire(cmd)!!.groupValues
            action = Cmd.RECT
            argument = Pair(arg1.toInt(), arg2.toInt())
        } else if (rotateRegex.matches(cmd)){
            val (_,rOc, arg1, arg2) = rotateRegex.matchEntire(cmd)!!.groupValues
            if (rOc == "row") {
                action = Cmd.ROTATE_ROW
            } else {
                action = Cmd.ROTATE_COLUMN
            }
            argument = Pair(arg1.toInt(), arg2.toInt())
        } else {
            action = null
            argument = null
        }
    }

    enum class Cmd {
        RECT, ROTATE_ROW, ROTATE_COLUMN
    }
}

/**
 * 模拟屏幕
 */
class Screen(var width: Int, var height: Int) {

    val data = ArrayList<ArrayList<Char>>()

    init {
        for (i in 0..height-1) {
            val row = ArrayList<Char>()
            for (j in 0..width-1) {
                row.add('.')
            }
            data.add(row)
        }
    }
    fun rect(width:Int, height:Int) {
        for (i in 0..height-1) {
            for (j in 0..width-1) {
                data[i][j] = '#'
            }
        }
    }

    fun display() {
        println("==start==")
        for (i in 0..height-1) {
            for (j in 0..width-1) {
                print(data[i][j])
            }
            println()
        }
        println("==end==")
    }

    fun rotateRow(row: Int, offset: Int) {
        val offset = offset % width
        data[row] = (data[row].subList(width -offset, width) + data[row].subList(0, width - offset)) as ArrayList<Char>
    }

    fun rotateColumn(column: Int, offset: Int) {
        val offset = offset % height

        val pool = LinkedList<Char>()
        for (y in height-offset..height-1) {
            pool.add(data[y][column])
        }
        for (y in height-offset-1 downTo 0) {
            data[y+offset][column] = data[y][column]
        }
        for (y in 0..pool.size-1) {
            data[y][column] = pool[y]
        }
    }

    fun count(): Int {
        var sum = 0
        for (i in 0..height-1) {
            for (j in 0..width-1) {
                if (data[i][j] == '#') {
                    sum++
                }
            }
        }
        return sum
    }
}