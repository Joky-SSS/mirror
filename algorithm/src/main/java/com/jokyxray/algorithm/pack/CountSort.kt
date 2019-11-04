package com.jokyxray.algorithm.pack

object CountSort {
    fun countSort(arr: IntArray) {
        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        for (i in arr) {
            min = kotlin.math.min(i, min)
            max = kotlin.math.max(i, max)
        }
        val temp = IntArray(arr.size) { 0 }
        val count = IntArray(max - min + 1) { 0 }

        for (num in arr) {
            count[num - min]++
        }

//        for (i in (min + 1)..max) {
//            count[i - min] += count[i - min - 1]
//            // 此时count[i]表示数值<=i的元素的个数,计算i排序后的index
//            // 这样做的目的是为了方便最后赋值，方便后续直接对应index赋值
//            // 「从下个方法的 ‘count[num - min]--’ 可以看出」
//        }
//
//        for (num in arr) {
//            var index = count[num - min] - 1 //加总数组中对应元素的下标
//            temp[index] = num // 将该值存入存储数组对应下标中
//            count[num - min]-- // 加总数组中，该值的总和减少1。
//        }
        var index = 0
        for (i in count.indices) {
            while (count[i] > 0) {
                temp[index++] = i + min
                count[i]--
            }
        }

        // 将存储数组的值替换给原数组
        for (i in arr.indices) {
            arr[i] = temp[i]
        }
    }
}