package basics.problems.simple

import org.junit.jupiter.api.Assertions.*
import java.util.*
import org.paukov.combinatorics3.Generator;
import kotlin.collections.HashMap
import kotlin.math.abs
import kotlin.system.measureTimeMillis

/**
 * Given an array of positive integers, find the index of a pair of numbers that add up
 * to a given integer.  E.g. Given [5, 2, 6, 3, 1] and 9, the output will be the array [2, 3] because 6+3 = 9
 */

/**
 * Constraints:
 * 1.  Input array can be empty.  Input number will always be positive integer.
 * 2.  There will always be a solution.  And there will only be one solution.
 * 3.  Numbers will not repeat
 * 4.  Return null if solution is not found or any constraints are not satisfied.
 */

/**
 * Test Cases:
 * 1.  Given either an empty array or array with only 1 element, return null.
 * 2.  Return null if solution not found
 */

fun pairAddingUpToBruteForce(array: Array<Int>, sum: Int): Optional<Array<Int>> {
    val empty = Optional.empty<Array<Int>>()
    if (array.isNullOrEmpty() || array.size == 1) {
        return empty;
    }

    for (i in 1 until array.size) {
        for (j in i until array.size) {
            if (array[i] + array[j] == sum) {
                return Optional.of(arrayOf(i, j))
            }
        }
    }
    return empty
}

fun pairAddingupOptimized(array: Array<Int>, sum: Int): Array<Int> { //5, 2, 6, 3, 1
    val map = mutableMapOf<Int, Int>();
    for(i in array.indices){
        val ntf = sum - array[i]
        if(map.containsKey(array[i])) {
            return arrayOf(map[array[i]]!!, i )
        }
        map[ntf] = i
    }

    return emptyArray();
}


fun nPairAddingUpTo(a: Array<Int>, nPair: Int, sum: Int): List<Int> {
    /**
     * [5, 6, 1, 2, 8, 3], 4, 14 ==> 1,2,8,3
     */

    if (a.isEmpty() || nPair <= 0 || nPair > a.size) {
        return emptyList()
    }
    val list = a.distinct()
    return combinations(list, nPair).first { it.sum() == sum }
}

fun combinations(numbers: List<Int>, k: Int): List<MutableList<Int>> {
    return Generator.combination(numbers).simple(k).toList()
}

fun fibonacci(n: Int): Int {
    if(n <= 2) return 1
    else return fibonacci(n - 1) + fibonacci(n - 2)
}

fun fibonacciDynamicProgramming(n: Int, hm: MutableMap<Int, Int> = mutableMapOf()): Int {
    return if(n <= 2) 1
    else if (hm.containsKey(n)) return hm[n]!!
    else {

        hm[n] = fibonacciDynamicProgramming(n - 1, hm) + fibonacciDynamicProgramming(n - 2, hm)
        return hm[n]!!
    }
}

/********************  TESTS ******************************/
fun main() {
//    pairAddingUpTo(arrayOf(5, 2, 6, 3, 1), 9)
//    Tests.runAll()
//    println(numberOfCombinations(10, 7))
//    println("Tests Passed!")
//    println(combinations(arrayOf(5, 2, 6, 3, 1), 2))
//    println(nPairAddingUpTo(arrayOf(5, 6, 1, 2, 8, 3, 2, 34, 66, 23, 55, 64, 31, 77), 3, 77))
    println(Arrays.toString(pairAddingupOptimized(arrayOf(5, 2, 6, 3, 1), 9)))
//    val n = 14
//    println(measureTimeMillis { println(fibonacci(n)) })
//    println(measureTimeMillis { println(fibonacciDynamicProgramming(n)) })
}

class Tests {
    companion object {
        fun runAll() {
            val tests = Tests()
            tests.`should return null if input array is empty`()
            tests.`should return null if input array is has one element`()
            tests.`should return the index of two numbers that add up to the sum`()
        }
    }

    fun `should return null if input array is has one element`() {
        val result = pairAddingUpToBruteForce(arrayOf(1), 9)
        assertEquals(Optional.empty<Array<Int>>(), result)
    }

    fun `should return null if input array is empty`() {
        val result = pairAddingUpToBruteForce(emptyArray(), 9)
        assertEquals(Optional.empty<Array<Int>>(), result)
    }

    fun `should return the index of two numbers that add up to the sum`() {
        val result = pairAddingUpToBruteForce(arrayOf(5, 2, 6, 3, 1), 9)
        assertArrayEquals(arrayOf(2, 3), result.get())
    }
}