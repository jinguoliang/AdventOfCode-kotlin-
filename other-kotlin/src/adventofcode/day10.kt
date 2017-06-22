package adventofcode

import com.sun.org.apache.xpath.internal.operations.Bool
import java.io.File

/**
 * Created by jinux on 17-6-21.
 */

fun day10(): Unit {
    val initPattern = "value (\\d+) goes to bot (\\d+)".toRegex()
    val dispatchPatter = "bot (\\d+) gives low to (\\w+) (\\d+) and high to (\\w+) (\\d+)".toRegex()

    File("data/day10").forEachLine { line ->
        if (initPattern.matches(line)) {
            val (_, value, bot) = initPattern.matchEntire(line)!!.groupValues
//            println("bot $bot has value $value")
            val robot: Robot? = getFromReceivers(bot.toInt()) as Robot
            robot!!.gaveValue(value.toInt())
        }

        if (dispatchPatter.matches(line)) {
            val groups = dispatchPatter.matchEntire(line)!!.groupValues
            val (_, bot, lowReceiver, lowId) = groups
            val (hightReceiver, hightId) = groups.subList(4, groups.size);
//            println("bot $bot dispatch low to $lowReceiver $lowId and high to $hightReceiver $hightId")
            var receiver = getFromReceivers(bot.toInt()) as Robot
            receiver!!.lowReceiver = if (lowReceiver == "bot") getFromReceivers(lowId.toInt()) else getFromReceivers(lowId.toInt(),false)
            receiver!!.highReceiver = if (hightReceiver == "bot") getFromReceivers(hightId.toInt()) else getFromReceivers(hightId.toInt(),false)
        }

    }
//    robots.toList().sortedBy { it.first }.forEach {
//        println("start ${it.second}")
//    }

    while(true) {
        robots.forEach { _, v ->
            if (v is Robot) {
                if (v.full()) {
                    println(v)
                        v.lowReceiver!!.gaveValue(v.lowValue)
                        v.lowValue = -1
                        v.highReceiver!!.gaveValue(v.hightValue)
                        v.hightValue = -1
                }
            }
        }
        val count = robots.count {
            if (it.value is Robot) {
                val rob = it.value as Robot
                rob.full()
            } else {
                 false
            }
        }
        if (count == 0) {
            break
        }
    }

    robots.toList().sortedBy { it.first }.forEach {
        println("finish ${it.second}")
    }
    println(robots.toList().filter { it.first < 0 && it.first > -10003 }.fold(1, { multi, kv ->
        multi * (kv.second as Output).first()
    }))
}

val robots = mutableMapOf<Int, Receiver>()

fun getFromReceivers(id: Int, robot: Boolean = true): Receiver? {
    val id = if (robot) id else -id - 10000
    if (robots[id] == null) {
        robots[id] = if (robot) Robot(id) else Output(id)
    }
    return robots[id]
}

interface Receiver {
    var id: Int
     fun gaveValue(v: Int)
    fun full(): Boolean
    fun empty(): Boolean
}

class Output(override var id: Int) : Receiver {
    override fun empty(): Boolean {
        return values.size == 0
    }

    override fun full(): Boolean {
        return false
    }

    val values = mutableListOf<Int>()

    override fun gaveValue(v: Int) {
        values.add(v)
    }

    override fun toString(): String {
        return "I am Output $id and I have $values"
    }

    fun first(): Int {
        return values[0]
    }
}

class Robot(override var id: Int) : Receiver {
    override fun empty(): Boolean {
        return lowValue == -1 && hightValue == -1
    }

    override fun full(): Boolean {
        if (hightValue != -1 && lowValue != -1) {
            return true
        }
        return false
    }

    var hightValue = -1
    var lowValue = -1

    var lowReceiver: Receiver? = null;
    var highReceiver: Receiver? = null;

    override fun gaveValue(value: Int) {
        if ((value == 61 && hightValue == 17) || (value == 17 && hightValue == 61)
                || (value == 61 && lowValue == 17) || (value == 17 && lowValue == 61)) {
            println("robot $id")
        }
        if (value < hightValue) {
            lowValue = value
        } else {
            lowValue = hightValue
            hightValue = value

        }
    }

    override fun toString(): String {
        return "I am robot $id, I has $lowValue and $hightValue, and I will give low to ${lowReceiver?.id} and give hight to ${highReceiver?.id}"
    }

}