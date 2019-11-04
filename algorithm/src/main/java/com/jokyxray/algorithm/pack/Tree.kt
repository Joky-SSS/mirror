package com.jokyxray.algorithm.pack

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class TreeNode(var value: Int,
                    var name: String = "",
                    var left: TreeNode? = null,
                    var right: TreeNode? = null)


object Tree {
    fun preTraverse(root: TreeNode?) {
        root?.run {
            println(value)
            preTraverse(left)
            preTraverse(right)
        }
    }

    fun inTraverse(root: TreeNode?) {
        root?.run {
            preTraverse(left)
            println(value)
            preTraverse(right)
        }
    }

    fun postTraverse(root: TreeNode?) {
        root?.run {
            preTraverse(left)
            preTraverse(right)
            println(value)
        }
    }

    fun invert(root: TreeNode?) {
        root?.run {
            val temp = left
            left = right
            right = temp
            invert(left)
            invert(right)
        }
    }

    fun getMax(root: TreeNode?): Int {
        if (root == null) {
            return Int.MIN_VALUE
        }
        val left = getMax(root.left)
        val right = getMax(root.right)
        return max(root.value, max(left, right))
    }

    fun maxDepth(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        val left = maxDepth(root.left)
        val right = maxDepth(root.right)
        return max(left, right) + 1
    }

    fun minDepth(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        val left = minDepth(root.left)
        val right = minDepth(root.right)
        if (left == 0) return right + 1
        if (right == 0) return left + 1
        return min(left, right) + 1
    }

    fun isBlanced(root: TreeNode?): Boolean {
        return maxBlancedDepth(root) != -1
    }

    private fun maxBlancedDepth(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        val left = maxBlancedDepth(root.left)
        val right = maxBlancedDepth(root.right)
        if (left == -1 || right == -1 || (abs(left - right) > 1)) {
            return -1
        }
        return max(left, right) + 1
    }
}