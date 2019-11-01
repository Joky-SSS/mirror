package com.jokyxray.algorithm

import com.jokyxray.algorithm.pack.MergeSort

fun main() {
    var sortArr = intArrayOf(6, 3, 5, 8, 2)
    MergeSort.mergeSort(sortArr)
    println(sortArr.joinToString())
}