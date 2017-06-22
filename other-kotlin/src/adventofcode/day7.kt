package adventofcode

import com.sun.org.apache.xpath.internal.operations.Bool
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by jinux on 17-6-16.
 */

fun day7() {
    val f = File("data/day7")

    var TLSCount = 0;
    var SSLCount = 0;

    f.forEachLine { line ->
        println(line)

        val (inBracket, outBracket) = decompose(line)

        if(isSupportTLS(inBracket, outBracket)) {
            TLSCount++
        }

        if(isSupportSSL(inBracket, outBracket)) {
            SSLCount++
        }
    }

    println("TLS count = $TLSCount")
    println("SSL count = $SSLCount")
}

fun isSupportSSL(inBracket: ArrayList<String>, outBracket: ArrayList<String>): Boolean {
    var isSupport = false

    val ABAInBracket = inBracket.flatMap { it.getABA() }
    val ABAOutBracket = outBracket.flatMap { it.getABA() }
    for (s in ABAInBracket) {
        for (t in ABAOutBracket) {
            if (s.isReverse(t)) {
                return true
            }
        }
    }
    return false
}

private fun String.isReverse(t: String): Boolean {
    return this[0] == t[1] && this[1] == t[0]
}

private fun String.getABA(): List<String> {
    val patternLength = 3
    val pattern = LinkedList<Char>()
    fun checkABBA(): Boolean {
        if (pattern.size != patternLength) {
            return false
        }
        return pattern[0] == pattern[2] && pattern[0] != pattern[1]
    }

    val result = ArrayList<String>()
    forEach {
        pattern.offer(it)
        if (pattern.size == patternLength) {
            if (checkABBA()) {
                result.add(pattern.joinToString(separator = ""))
            }
            pattern.poll()
        }
    }

    return result;
}

fun isSupportTLS(inBracket: ArrayList<String>, outBracket: ArrayList<String>): Boolean {
    var isSupport = false

    for (s in inBracket) {
        if (isABBA(s)) {
            return false
        }
    }

    for (s in outBracket) {
        if (isABBA(s)) {
            return true
        }
    }

    return false
}

fun decompose(line: String): Pair<ArrayList<String>, ArrayList<String>> {
    val inBracket = ArrayList<String>()
    val outBracket = ArrayList<String>()

    line.split("[").forEach {
        if (it.contains("]")) {
            val tmp = it.split("]")
            inBracket.add(tmp[0])
            outBracket.add(tmp[1])
        } else {
            outBracket.add(it)
        }
    }
    return Pair(inBracket, outBracket)
}

fun isABBA(part: String): Boolean {
    val fore = LinkedList<Char>()
    fun checkABBA(): Boolean {
        if (fore.size != 4) {
            return false
        }
        return fore[0] == fore[3] && fore[1] == fore[2] && fore[0] != fore[1]
    }

    part.forEach {
        fore.offer(it)
        if (checkABBA()) {
            return true
        } else {
            if (fore.size >= 4) {
                fore.poll()
            }
        }
    }

    return false;
}
