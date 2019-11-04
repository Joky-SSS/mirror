package com.jokyxray.algorithm.pack

object BucketSort {
    fun bucketSort(arr: IntArray) {
        var max = Int.MIN_VALUE
        var min = Int.MAX_VALUE
        for (num in arr) {
            max = kotlin.math.max(num, max)
            min = kotlin.math.min(num, min)
        }
        val bucketNum = (max - min) / arr.size + 1
        val bucketArr = ArrayList<ArrayList<Int>>(bucketNum)
        // 初始化各个桶
        for (i in 0 until bucketNum) {
            bucketArr.add(ArrayList())
        }

        // 将每个元素放入相应的桶
        for (num in arr) {
            val index = (num - min) / (arr.size)
            bucketArr[index].add(num)
        }
        var j = 0
        // 对每个桶进行排序
        for (bucket in bucketArr) {
            bucket.sort()
            for (num in bucket) {
                arr[j++] = num
            }
        }
    }
}