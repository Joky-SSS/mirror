//
// Created by Joky on 2019/2/18 0018.
//

#include "com_jokyxray_ndksimple_HelloWorld.h"

JNIEXPORT jstring JNICALL Java_com_jokyxray_ndksimple_HelloWorld_sayHello
        (JNIEnv *env, jobject jobject1, jstring name) {
    return env->NewStringUTF("hello ndk");
}