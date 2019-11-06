package com.jokyxray.test.retrofit


import retrofit2.Call
import retrofit2.http.HEAD

interface TestService {

    @HEAD("/")
    fun testVoidHead(): Call<Void>
}