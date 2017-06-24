package adventofcode

import java.io.File

/**
 * Created by jinux on 17-6-21.
 *
 * 第十天
 */

fun day10(): Unit {

    val (robots, outputs) = parseData()

    val robotList = robots.toList().map { it.second }

    while (robotList.count { it.isFull() } != 0) {
        robotList.forEach { v ->
            if (v.isFull()) {
                v.lowReceiver!!.gaveValue(v.lowValue)
                v.lowValue = -1
                v.highReceiver!!.gaveValue(v.highValue)
                v.highValue = -1
            }
        }
    }

    println("part2 answer: " + outputs.filter { it.key in 0..2 }.toList().
            fold(1, { multi, kv -> multi * kv.second.first() }))
}

fun  parseData(): Pair<Map<Int, Robot>, Map<Int, Output>> {

    val robots = mutableMapOf<Int, Robot>()
    val outputs = mutableMapOf<Int, Output>()

    fun getFromRobots(id: Int): Robot {
        if (robots[id] == null) {
            robots[id] = Robot(id)
        }
        return robots[id] as Robot
    }

    fun getFromOutputs(id: Int): Output {
        if (outputs[id] == null) {
            outputs[id] = Output(id)
        }
        return outputs[id] as Output
    }

    fun parseLine(line: String) {
        val initPattern = "value (\\d+) goes to bot (\\d+)".toRegex()
        val dispatchPatter = "bot (\\d+) gives low to (\\w+) (\\d+) and high to (\\w+) (\\d+)".toRegex()


        if (initPattern.matches(line)) {
            val (_, value, bot) = initPattern.matchEntire(line)!!.groupValues
            getFromRobots(bot.toInt()).gaveValue(value.toInt())
        }

        if (dispatchPatter.matches(line)) {
            val groups = dispatchPatter.matchEntire(line)!!.groupValues
            val (_, bot) = groups
            val (lowReceiver, lowId) = groups.subList(2, 4)
            val (highReceiver, highId) = groups.subList(4, 6)
            val robot = getFromRobots(bot.toInt())
            robot.lowReceiver = if (lowReceiver == "bot") getFromRobots(lowId.toInt()) else getFromOutputs(lowId.toInt())
            robot.highReceiver = if (highReceiver == "bot") getFromRobots(highId.toInt()) else getFromOutputs(highId.toInt())
        }
    }

    File("data/day10").forEachLine { line ->
        parseLine(line)
    }

    return Pair(robots, outputs)
}

interface Receiver {
    var id: Int
    fun gaveValue(value: Int)
}

class Output(override var id: Int) : Receiver {

    val values = mutableListOf<Int>()

    override fun gaveValue(value: Int) {
        values.add(value)
    }

    fun first(): Int {
        return values[0]
    }


    override fun toString(): String {
        return "I am Output $id and I have $values"
    }
}

class Robot(override var id: Int) : Receiver {
    var highValue = -1
    var lowValue = -1

    var lowReceiver: Receiver? = null
    var highReceiver: Receiver? = null

    override fun gaveValue(value: Int) {
        if (value < highValue) {
            lowValue = value
        } else {
            lowValue = highValue
            highValue = value
        }

        // part1 answer
        if (lowValue == 17 && highValue == 61) {
            println("part1 answer: robot $id")
        }
    }

    fun isFull(): Boolean {
        if (highValue != -1 && lowValue != -1) {
            return true
        }
        return false
    }

    override fun toString(): String {
        return "I am robot $id, I has $lowValue and $highValue, and I will give low to ${lowReceiver?.id} and give high to ${highReceiver?.id}"
    }

}