//
// Created by Joky on 2019/2/18 0018.
//

#include <cstdio>
#include <cstdlib>
#include <cstring>
#include "com_jokyxray_ndksimple_HelloWorld.h"

JNIEXPORT jstring JNICALL Java_com_jokyxray_ndksimple_HelloWorld_sayHello
        (JNIEnv *env, jobject jobject1, jstring name) {
    const char *str_char = nullptr;
    char buff[128] = {0};
    jboolean isCopy;
    str_char = env->GetStringUTFChars(name, &isCopy);
    printf("isCopy: %d \n", isCopy);
    if (str_char == nullptr) {
        return nullptr;
    }
    printf("str_char is %s \n", str_char);
    sprintf(buff, "hello %s", str_char);
    env->ReleaseStringUTFChars(name, str_char);
    return env->NewStringUTF(buff);
}

JNIEXPORT jint JNICALL Java_com_jokyxray_ndksimple_HelloWorld_sumArr
        (JNIEnv *env, jobject jobject1, jintArray jintArray1) {
    jint sum = 0;
    jint *c_arr;
    jint arr_len;
    arr_len = env->GetArrayLength(jintArray1);
    c_arr = (jint *) malloc(sizeof(jint) * arr_len);
    memset(c_arr, 0, arr_len);
    env->GetIntArrayRegion(jintArray1, 0, arr_len, c_arr);
    for (int i = 0; i < arr_len; ++i) {
        sum += c_arr[i];
    }
    free(c_arr);
    return sum;
}