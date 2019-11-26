package com.jokyxray.algorithm.pack

import kotlin.math.max

object DynamicProgram {
    /**
     * 在一条直线上，有n个房屋，每个房屋中有数量不等的财宝，有一个盗 贼希望从房屋中盗取财宝，
     * 由于房屋中有报警器，如果同时从相邻的两个房屋中盗取财宝就会触发报警器。
     * 问在不触发报警器的前提下，最多可获取多少财宝？
     * 例如 [5，2，6，3，1，7]，则选择5，6，7
     */

    fun robber(array: IntArray): Int {
        val length = array.size
        if (array.isEmpty()) return 0
        val dp = IntArray(length) { 0 }
        dp[0] = array[0]
        if (length == 1) return array[0]
        dp[1] = max(dp[0], array[1])
        for (i in 2 until length)
            dp[i] = max((array[i] + dp[i - 2]), dp[i - 1])
        return dp[length - 1]
    }

    /**
     * 给定一个数组，求这个数组的连续子数组中，最大的那一段的和。
     * 如数组[-2,1,-3,4,-1,2,1,-5,4] 的子段为：[-2,1]、[1,-3,4,-1]、[4,-1,2,1]、…、[-2,1,-3,4,-1,2,1,-5,4]，
     * 和最大的是[4,1,2,1]，为6。
     */

    fun maSubArray(array: IntArray): Int {
        val length = array.size
        if (array.isEmpty()) return 0

    }
}