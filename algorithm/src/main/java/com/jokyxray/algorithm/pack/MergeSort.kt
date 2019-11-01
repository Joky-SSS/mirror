package com.jokyxray.algorithm.pack

object MergeSort {

    fun mergeSort(arr: IntArray) {
        val temp = IntArray(arr.size)

        internalMergeSort(arr, temp, 0, arr.size - 1)
    }

    private fun internalMergeSort(arr: IntArray, temp: IntArray, left: Int, right: Int) {
        // 当left == right时，不需要再划分
        if (left < right) {
            var mid = (left + right) / 2
            // 左右往下拆分
            internalMergeSort(arr, temp, left, mid)
            internalMergeSort(arr, temp, mid + 1, right)
            // 拆分结束后返回结果进行合并
            mergeSortedArray(arr, temp, left, mid, right)
        }
    }

    // 合并两个有序子序列
    private fun mergeSortedArray(arr: IntArray, temp: IntArray, left: Int, mid: Int, right: Int) {
        println("left:$left, mid:$mid, right:$right")
        println("source: " + arr.joinToString())
        println("temp:  " + temp.joinToString())
        var i = left
        var j = mid + 1
        var k = 0
        while (i <= mid && j <= right) {
            temp[k++] = if (arr[i] < arr[j]) arr[i++] else arr[j++]
        }
        // 合并完，将非空的那列拼入
        while (i <= mid) {
            temp[k++] = arr[i++]
        }
        while (j <= right) {
            temp[k++] = arr[j++]
        }
        // 把temp数据复制回原数组
        for (i in 0 until k) {
            arr[left + i] = temp[i]
        }
        println("source: " + arr.joinToString())
        println("temp:  " + temp.joinToString())
    }
}