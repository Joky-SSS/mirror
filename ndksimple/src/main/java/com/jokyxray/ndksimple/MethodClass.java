package com.jokyxray.ndksimple;

import android.util.Log;

public class MethodClass {
	public static void staticMethod(Class clazz, String str, int i) {
		Log.e("Xup", "MethodClass::staticMethod called! clazz:" + clazz.getName() + " str:" + str + " ,i:" + i);
	}

	public void instanceMethod(Class clazz, String str, int i) {
		Log.e("Xup", "MethodClass::instanceMethod called! clazz:" + clazz.getName() + " str:" + str + " ,i:" + i);
	}
}
