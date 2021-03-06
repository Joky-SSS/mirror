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

    fun maxSubArray(array: IntArray): Int {
        val length = array.size
        if (length == 0) return 0
        val dp = IntArray(length) { 0 }
        dp[0] = array[0]
        var max = dp[0]
        for (i in 1 until length) {
            dp[i] = max(dp[i - 1] + array[i], array[i])
            println("dp[$i] is ${dp[i]}")
            if (dp[i] > max) max = dp[i]
        }
        return max
    }

    /**
     * 已知不同面值的钞票，求如 何用最少数量的钞票组成某个金额，
     * 求可 以使用的最少钞票数量。
     * 如果任意数量的已知面值钞票都无法组成该金额， 则返回-1。
     *
     * 示例：
     * Input: coins = [1, 2, 5], amount = 11
     * Output: 3
     * Explanation: 11 = 5 + 5 + 1
     * Input: coins = [2], amount = 3
     * Output: -1
     */

    fun coinChange(coins: IntArray, amount: Int): Int {
        val length = coins.size
        if (length == 0 || amount < 0) return -1
        val dp = IntArray(amount + 1) { -1 }
        for (coin in coins) {
            if (coin == amount) return 1
            if (coin < amount) dp[coin] = 1
        }

        for (i in 1..amount)
            for (coin in coins)
                if (i - coin >= 0 && dp[i - coin] != -1)
                    if (dp[i] == -1 || dp[i] > (dp[i - coin] + 1))
                        dp[i] = dp[i - coin] + 1
        return dp[amount]
    }

}