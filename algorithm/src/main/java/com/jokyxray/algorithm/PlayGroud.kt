package com.jokyxray.algorithm

import com.jokyxray.algorithm.pack.CountSort
import com.jokyxray.algorithm.pack.MergeSort

fun main() {
    val sortArr = intArrayOf(6, 3, 5, 8, 2)
    when ("CountSort") {
        "MergeSort" -> MergeSort.mergeSort(sortArr)
        "CountSort" -> CountSort.countSort(sortArr)
        else -> println("no sort")
    }
    println(sortArr.joinToString())
}