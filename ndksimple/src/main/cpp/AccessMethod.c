//
// Created by Joky on 2019/2/19 0019.
//
#include "com_jokyxray_ndksimple_AccessMethod.h"

JNIEXPORT void JNICALL Java_com_jokyxray_ndksimple_AccessMethod_callStaticMethod
        (JNIEnv *env, jclass jclass1) {
    jclass clazz = (*env)->FindClass(env, "com/jokyxray/ndksimple/MethodClass");
    if (clazz == NULL) {
        return;
    }
    jmethodID mid = (*env)->GetStaticMethodID(env, clazz, "staticMethod", "(Ljava/lang/Class;Ljava/lang/String;I)V");
    if (mid == NULL) {
        return;
    }
    jstring str_arg = (*env)->NewStringUTF(env, "静态方法");
    (*env)->CallStaticVoidMethod(env, clazz, mid, jclass1, str_arg, 100);
    (*env)->DeleteLocalRef(env, clazz);
    (*env)->DeleteLocalRef(env, str_arg);
}


JNIEXPORT void JNICALL Java_com_jokyxray_ndksimple_AccessMethod_callInstanceMethod
        (JNIEnv *env, jclass jclass1) {
    jclass clazz = NULL;
    jmethodID mid_construct = NULL;
    jmethodID mid_method = NULL;
    jobject obj = NULL;
    jstring str_arg = NULL;

    clazz = (*env)->FindClass(env, "com/jokyxray/ndksimple/MethodClass");
    if (clazz == NULL) return;
    mid_construct = (*env)->GetMethodID(env, clazz, "<init>", "()V");
    if (mid_construct == NULL) return;
    mid_method = (*env)->GetMethodID(env, clazz, "instanceMethod", "(Ljava/lang/Class;Ljava/lang/String;I)V");
    if (mid_method == NULL)return;
    obj = (*env)->NewObject(env, clazz, mid_construct);
    if (obj == NULL)return;
    str_arg = (*env)->NewStringUTF(env, "实例方法");
    (*env)->CallVoidMethod(env, obj, mid_method, jclass1, str_arg, 90);
    (*env)->DeleteLocalRef(env, clazz);
    (*env)->DeleteLocalRef(env, obj);
    (*env)->DeleteLocalRef(env, str_arg);
}
