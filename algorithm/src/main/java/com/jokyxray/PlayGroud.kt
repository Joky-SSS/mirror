package com.jokyxray

import com.jokyxray.algorithm.pack.DynamicProgram

fun main() {
    /**
     * Sort
     */
//    val sortArr = intArrayOf(6, 12, 3, 25,11 ,5, 36, 8, 2)
//    println(sortArr.joinToString())
//    when ("BucketSort") {
//        "MergeSort" -> MergeSort.mergeSort(sortArr)
//        "CountSort" -> CountSort.countSort(sortArr)
//        "BucketSort" -> BucketSort.bucketSort(sortArr)
//        else -> println("no sort")
//    }
//    println(sortArr.joinToString())

    /**
     * tree
     */
    //        4
    //    2       7
    // 1    3   6   9
    //                11
    //       invert
    //          4
    //      7       2
    //   9    6   3   1
    // 11
//    val root = TreeNode(4)
//    root.left = TreeNode(2)
//    root.right = TreeNode(7)
//    root.left?.left = TreeNode(1)
//    root.left?.right = TreeNode(3)
//    root.right?.left = TreeNode(6)
//    root.right?.right = TreeNode(9)
//    root.right?.right?.right = TreeNode(11)
//    Tree.preTraverse(root)
//    Tree.invert(root)
//    println("----after invert----")
//    Tree.preTraverse(root)

    //max
//    println("max:${Tree.getMax(root)}")
//    println("maxDepth:${Tree.maxDepth(root)}")
//    println("minDepth:${Tree.minDepth(root)}")
//    println("isBlanced:${Tree.isBlanced(root)}")

    /**
     * Retrofit test
     */
//    val retrofit = retrofit2.Retrofit.Builder()
//            .baseUrl("https://www.baidu.com")
//            .build()
//    val apiService = retrofit.create(TestService::class.java)
//    val call = apiService.testVoidHead()
//    call.execute()

    /**
     * GenericType
     */
//    val parent = SubChild<String, Int>()
//    parent.getGenericType()

    /**
     *  Dynamic Program
     */
    val array = intArrayOf(5, 2, 6, 3, 1, 7)
    val result = DynamicProgram.robber(array)
    println("robber result is: $result")

    val array2 = intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4)
    val result2 = DynamicProgram.maxSubArray(array2)
    println("maxSubArray result is: $result2")
}