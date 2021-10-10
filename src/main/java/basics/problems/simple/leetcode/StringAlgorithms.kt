package basics.problems.simple.leetcode

import java.util.*
import kotlin.math.max

// https://leetcode.com/problems/backspace-string-compare/
fun typedStringComparison(s: String, t: String): Boolean {
    fun processBackspaces(str: String): String {
        var poundCounter = 0
        var list = mutableListOf<Char>()
        for (i in str.length - 1 downTo 0) {
            if (str[i] == '#') {
                poundCounter++
            } else {
                if (poundCounter > 0) {
                    poundCounter--
                } else {
                    list.add(str[i])
                }
            }
        }
        return list.joinToString("")
    }

    fun processBackspacesStack(str: String): String {
        var stack = Stack<Char>()
        for (i in str.indices) {
            if (str[i] == '#') {
                if (stack.size > 0) stack.pop()
            } else {
                stack.push(str[i])
            }
        }
        return stack.joinToString("")
    }

    val sString = processBackspacesStack(s)
    val tString = processBackspacesStack(t)
    if (sString.length == tString.length) {
        for (i in sString.indices) {
            if (sString[i] != tString[i]) return false
        }
        return true
    } else {
        return false
    }
//    return processBackspaces(s) == processBackspaces(t)
}

// https://leetcode.com/problems/backspace-string-compare/
fun typedStringTwoPointer(s: String, t: String): Boolean {
    // "aa###b", "b"
    var sPointer = s.length - 1
    var tPointer = t.length - 1
    var tPoundCounter = 0
    var sPoundCounter = 0
    var finalResult = true
    while (sPointer >= 0 || tPointer >= 0) {
        var sx: Char
        var tx: Char
        var isCompareS = false
        var isCompareT = false

        if (sPointer >= 0) {
            sx = s[sPointer]
            if (sx == '#') {
                sPoundCounter++
                sPointer--
            } else {
                if (sPoundCounter > 0) {
                    sPoundCounter--
                    sPointer--
                } else {
                    isCompareS = true
                }
            }
        } else {
            isCompareS = true
            sx = ' '
        }

        if (tPointer >= 0) {
            tx = t[tPointer]
            if (tx == '#') {
                tPoundCounter++
                tPointer--
            } else {
                if (tPoundCounter > 0) {
                    tPoundCounter--
                    tPointer--
                } else {
                    isCompareT = true
                }
            }
        } else {
            isCompareT = true
            tx = ' '
        }

        if (isCompareS && isCompareT) {
            if (sx != tx) {
                finalResult = false
                break
            } else {
                sPointer--
                tPointer--
                continue
            }
        }

    }
    return finalResult
}


// https://leetcode.com/problems/longest-substring-without-repeating-characters/
fun longestSubStringwithoutRepeatingCharactersBruteForce(input: String): Int {
    if (input.length <= 1) {
        return input.length
    }
    // aabcdabcde
    var maxCount = 0
    var counter = 0
    for (i in input.indices) {
        if (maxCount >= input.length - i + 1) break
        val set = mutableSetOf<Char>(input[i])
        maxCount = max(counter, maxCount)
        counter = 1
        for (j in i + 1 until input.length) {
            if (set.contains(input[j])) {
                break
            } else {
                set.add(input[j])
                counter++
            }
        }
    }
    return maxCount
}

fun main() {
    println(longestSubStringWithoutRepeatingCharactersSlidingWindow("bbbbb"))
}

/**
 * p w
|
 * longest = 4
 * current = 4
 * {
 *    p
 *    w
 * }
 *
 */

fun longestSubStringWithoutRepeatingCharactersSlidingWindow(input: String): Int {
    // a b c a b c b b
    if (input.length <= 1) return input.length

    var leftPointer = 0
    var longest = 0
    val map = mutableMapOf<Char, Int>()
    var currentLongest = 0

    for (rightPointer in input.indices) {
        val lastChar = input[rightPointer]
        val lastIndex = map.getOrElse(lastChar) { -1 }
        if (lastIndex >= leftPointer) {
            leftPointer = lastIndex + 1
        }
        map[lastChar] = rightPointer
        longest = max(longest, rightPointer - leftPointer + 1)
    }

    return longest
}